package com.xzit.common.order.utils;

import com.xzit.common.order.entity.FeeStates;

import java.math.BigDecimal;
import java.util.List;

public class FeeCalculator {
    public static BigDecimal calculateShippingCost(int distance, int weight, int volume, List<FeeStates> feeStatesList) {
        BigDecimal totalDistanceCost = BigDecimal.ZERO;
        BigDecimal totalWeightCost = BigDecimal.ZERO;
        BigDecimal totalVolumeCost = BigDecimal.ZERO;
        BigDecimal fuelCost = BigDecimal.ZERO;
        BigDecimal basePrice = BigDecimal.ZERO;

        for (FeeStates feeState : feeStatesList) {
            // 计算起步价
            if (feeState.getFeeTypeId() == 1 && distance == 0 && weight == 0 && volume == 0) {
                basePrice = feeState.getPrice();
            }
            // 计算距离费用
            else if (feeState.getFeeTypeId() == 2 && distance > feeState.getLimit()) {
                totalDistanceCost = totalDistanceCost.add(BigDecimal.valueOf(distance - feeState.getLimit()).multiply(feeState.getPrice()));
            }
            // 计算重量费用
            else if (feeState.getFeeTypeId() == 3 && weight > feeState.getLimit()) {
                totalWeightCost = totalWeightCost.add(BigDecimal.valueOf(weight - feeState.getLimit()).multiply(feeState.getPrice()));
            }
            // 计算体积费用
            else if (feeState.getFeeTypeId() == 4 && volume > feeState.getLimit()) {
                totalVolumeCost = totalVolumeCost.add(BigDecimal.valueOf(volume - feeState.getLimit()).multiply(feeState.getPrice()));
            }
        }

        // 计算燃油费用
        for (FeeStates feeState : feeStatesList) {
            fuelCost = fuelCost.add(BigDecimal.valueOf(distance * feeState.getOilRate()));
        }

        // 总运费 = 起步价 + 距离费用 + 重量费用 + 体积费用 + 燃油费用
        return basePrice.add(totalDistanceCost).add(totalWeightCost).add(totalVolumeCost).add(fuelCost);
    }
}
