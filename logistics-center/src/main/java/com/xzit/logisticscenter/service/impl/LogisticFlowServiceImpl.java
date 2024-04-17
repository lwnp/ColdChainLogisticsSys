package com.xzit.logisticscenter.service.impl;

import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.logistics.model.dto.LogisticFlowDTO;
import com.xzit.common.logistics.model.dto.SimpleLogisticFlowDTO;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.logisticscenter.mapper.LogisticFlowMapper;
import com.xzit.logisticscenter.service.LogisticFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogisticFlowServiceImpl implements LogisticFlowService {
    private final LogisticFlowMapper logisticFlowMapper;
    @Override
    public List<SimpleLogisticFlowDTO> getSimpleLogisticFlowByOrderNum(String orderNum) {
        return logisticFlowMapper.getSimpleLogisticFlowByOrderNum(orderNum);
    }

    @Override
    public List<LogisticFlowDTO> getLogisticFlowByOrderNum(String orderNum) {
        return logisticFlowMapper.getLogisticFlowByOrderNum(orderNum);
    }
}
