package com.xzit.hardwarecenter.repository;

import co.elastic.clients.util.DateTime;
import com.xzit.common.hardware.entity.WarehouseData;
import com.xzit.common.order.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface WarehouseDataRepository extends ElasticsearchRepository<WarehouseData,Long> {
    List<WarehouseData> findAllByCenterId(Long centerId);
    List<WarehouseData> getAllByCenterIdAndCreateTimeIsAfter(Long centerId, Date createTime);

}




