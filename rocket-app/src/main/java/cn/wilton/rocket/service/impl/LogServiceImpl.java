package cn.wilton.rocket.service.impl;

import cn.wilton.rocket.common.constant.RocketConstant;
import cn.wilton.rocket.common.entity.QueryRequest;
import cn.wilton.rocket.common.entity.system.Log;
import cn.wilton.rocket.common.util.SortUtil;
import cn.wilton.rocket.mapper.LogMapper;
import cn.wilton.rocket.service.ILogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.*;

/**
 * @author Ranger
 * @date: 2021/3/9 16:59
 * @email: wilton.icp@gmail.com
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    private final ObjectMapper objectMapper;

    @Override
    public IPage<Log> findLogs(Log log, QueryRequest request) {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(log.getUsername())) {
            queryWrapper.lambda().eq(Log::getUsername, log.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(log.getOperation())) {
            queryWrapper.lambda().like(Log::getOperation, log.getOperation());
        }
        if (StringUtils.isNotBlank(log.getLocation())) {
            queryWrapper.lambda().like(Log::getLocation, log.getLocation());
        }
        if (StringUtils.isNotBlank(log.getCreateTimeFrom()) && StringUtils.isNotBlank(log.getCreateTimeTo())) {
            queryWrapper.lambda()
                    .ge(Log::getCreatedTime, log.getCreateTimeFrom())
                    .le(Log::getCreatedTime, log.getCreateTimeTo());
        }

        Page<Log> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", RocketConstant.ORDER_DESC, true);

        return this.page(page, queryWrapper);
    }

    @Override
    public void deleteLogs(String[] logIds) {
        List<String> list = Arrays.asList(logIds);
        baseMapper.deleteBatchIds(list);
    }

    @Override
    public void saveLog(Log log) {
        // 保存系统日志
        save(log);
    }
}
