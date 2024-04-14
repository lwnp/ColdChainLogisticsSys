package com.xzit.common.logistics.model.dto;

import com.xzit.common.logistics.entity.Arrangement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArrangeDistanceDTO {
    List<Arrangement>  arrangementList;
    Double distance;
}
