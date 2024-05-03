package com.xzit.logisticscenter.repository;

import com.xzit.common.logistics.entity.LimitTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitTempRepository extends ElasticsearchRepository<LimitTemp, Long> {
    LimitTemp findByCenterId(Long centerId);
}
