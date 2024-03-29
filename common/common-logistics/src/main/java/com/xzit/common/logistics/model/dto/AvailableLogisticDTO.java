package com.xzit.common.logistics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableLogisticDTO {
    Long id;
    Long typeId;
    String name;
    String address;
    Double longitude;
    Double latitude;
    List<CarDTO> carList;
    List<CourierDTO> courierList;
}
