package com.xzit.ordercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzit.common.order.model.dto.GoodsDTO;
import com.xzit.common.order.model.vo.GoodsVO;
import com.xzit.common.sys.model.vo.QueryVO;

public interface GoodsService {
    void addGoods(GoodsVO goodsVO);
    void modifyGoods(GoodsVO goodsVO,Long goodsId);
    IPage<GoodsDTO> getUserGoodsByQuery(QueryVO queryVO);
    IPage<GoodsDTO> getAllGoodsByQuery(QueryVO queryVO);
    IPage<GoodsDTO> getUncheckedGoodsByQuery(QueryVO queryVO);
    void approveGoods(Long goodsId);
    void rejectGoods(Long goodsId);
    void deleteGoods(Long goodsId);
}
