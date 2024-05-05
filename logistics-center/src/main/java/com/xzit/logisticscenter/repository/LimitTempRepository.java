package com.xzit.logisticscenter.repository;

import com.xzit.common.logistics.entity.LimitTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LimitTempRepository extends ElasticsearchRepository<LimitTemp, Long> {
    LimitTemp findByCenterId(Long centerId);
    List<LimitTemp> findAllBy();
}
