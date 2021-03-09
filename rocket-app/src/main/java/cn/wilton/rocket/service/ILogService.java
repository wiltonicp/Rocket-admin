package cn.wilton.rocket.service;


import cn.wilton.rocket.common.entity.QueryRequest;
import cn.wilton.rocket.common.entity.system.Log;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.Method;

/**
 * @author Ranger
 * @date: 2021/3/9 16:23
 * @email: wilton.icp@gmail.com
 */
public interface ILogService extends IService<Log> {

    /**
     * 查询操作日志分页
     * @param log     日志
     * @param request QueryRequest
     * @return IPage<SystemLog>
     */
    IPage<Log> findLogs(Log log, QueryRequest request);

    /**
     * 删除操作日志
     * @param logIds 日志 ID集合
     */
    void deleteLogs(String[] logIds);

    /**
     * 异步保存操作日志
     */
    @Async("AsyncThreadPool")
    void saveLog(Log log);
}
