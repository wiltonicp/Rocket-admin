package cn.wilton.rocket.log;

import cn.hutool.json.JSONUtil;
import cn.wilton.rocket.common.entity.system.Log;
import cn.wilton.rocket.common.util.RocketUtil;
import cn.wilton.rocket.service.ILogService;
import cn.wilton.rocket.util.AddressUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 系统日志统一处理切面（非业务日志）
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/21 11:42
 * @Email: wilton.icp@gmail.com
 */
@Slf4j
@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class RocketLogAspect {

    private final ILogService logService;
    private final ObjectMapper objectMapper;

    @Pointcut("execution(* cn.wilton.rocket.controller.*.*(..)) && @annotation(cn.wilton.rocket.common.annotation.ControllerLogAspect)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "pointcut()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Log rocketLog = new Log();

        Object result = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation logs = method.getAnnotation(ApiOperation.class);
            rocketLog.setOperation(logs.value());
        }
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();

        String ip = RocketUtil.getHttpServletRequestIpAddress();
        rocketLog.setIp(ip);
        rocketLog.setUsername(RocketUtil.getCurrentUsername());
        rocketLog.setTime((endTime - startTime));
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        rocketLog.setMethod(className + "." + methodName + "()");
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            rocketLog.setParams(params.toString());
        }
        rocketLog.setCreatedTime(LocalDateTime.now());
        rocketLog.setLocation(AddressUtil.getCityInfo(ip));
        rocketLog.setResult(result);

        Map<String,Object> logMap = new HashMap<>(5);
        logMap.put("url",urlStr);
        logMap.put("method",rocketLog.getMethod());
        logMap.put("parameter",rocketLog.getParams());
        logMap.put("spendTime",rocketLog.getTime());
        logMap.put("operation",rocketLog.getOperation());
        //入库保存
        //logService.saveLog(rocketLog);
        log.info(Markers.appendEntries(logMap), JSONUtil.parse(rocketLog).toString());
        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

    @SuppressWarnings("all")
    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Map) {
                    Set set = ((Map) args[i]).keySet();
                    List<Object> list = new ArrayList<>();
                    List<Object> paramList = new ArrayList<>();
                    for (Object key : set) {
                        list.add(((Map) args[i]).get(key));
                        paramList.add(key);
                    }
                    return handleParams(params, list.toArray(), paramList);
                } else {
                    if (args[i] instanceof Serializable) {
                        Class<?> aClass = args[i].getClass();
                        try {
                            aClass.getDeclaredMethod("toString", new Class[]{null});
                            // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                        } catch (NoSuchMethodException e) {
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                        }
                    } else if (args[i] instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) args[i];
                        params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
                    } else {
                        params.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
                    }
                }
            }
        } catch (Exception ignore) {
            params.append("参数解析失败");
        }
        return params;
    }
}
