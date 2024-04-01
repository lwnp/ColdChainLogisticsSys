package com.xzit.logisticscenter.service;

import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.model.dto.AvailableLogisticDTO;

import java.util.List;
import java.util.Map;

public interface LogisticService {
    Map<String,Double> address2Location(String address);



}
