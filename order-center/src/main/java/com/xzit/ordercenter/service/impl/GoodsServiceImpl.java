package com.xzit.ordercenter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.api.user.feign.UserFeignClient;
import com.xzit.common.order.entity.Goods;
import com.xzit.common.order.model.dto.GoodsDTO;
import com.xzit.common.order.model.vo.GoodsVO;
import com.xzit.common.sys.constant.MQConstant;
import com.xzit.common.sys.entity.Notice;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.common.user.entity.AuthUser;
import com.xzit.common.user.entity.UserInfo;
import com.xzit.common.user.model.dto.UserInfoDTO;
import com.xzit.ordercenter.mapper.GoodsMapper;
import com.xzit.ordercenter.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final StreamBridge streamBridge;
    private final GoodsMapper goodsMapper;
    private final UserFeignClient userFeignClient;

    @Override
    public void addGoods(GoodsVO goodsVO) {
        if (goodsVO.getMaxTemperature() == null || goodsVO.getMinTemperature() == null ||
                goodsVO.getMaxHumidity() == null || goodsVO.getMinHumidity() == null ||
                goodsVO.getMaxDioxide() == null || goodsVO.getMinDioxide() == null ||
                goodsVO.getMaxMethane() == null || goodsVO.getMinMethane() == null ||
                goodsVO.getMaxEthylene() == null || goodsVO.getMinEthylene() == null ||
                goodsVO.getMaxOxide() == null || goodsVO.getMinOxide() == null) {
            throw new BizException("All temperature, humidity, dioxide, methane, ethylene, and oxide values must be provided.");
        }

        if (goodsVO.getMaxTemperature() < goodsVO.getMinTemperature()) {
            throw new BizException("maxTemperature must be greater than minTemperature");
        }
        if (goodsVO.getMaxHumidity() < goodsVO.getMinHumidity()) {
            throw new BizException("maxHumidity must be greater than minHumidity");
        }
        if (goodsVO.getMaxDioxide() < goodsVO.getMinDioxide()) {
            throw new BizException("maxDioxide must be greater than minDioxide");
        }
        if (goodsVO.getMaxMethane() < goodsVO.getMinMethane()) {
            throw new BizException("maxMethane must be greater than minMethane");
        }
        if (goodsVO.getMaxEthylene() < goodsVO.getMinEthylene()) {
            throw new BizException("maxEthylene must be greater than minEthylene");
        }
        if (goodsVO.getMaxOxide() < goodsVO.getMinOxide()) {
            throw new BizException("maxOxide must be greater than minOxide");
        }

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map = jwt.getClaims();
        Long userId = (Long) map.get("userId");
        UserInfoDTO userInfoDTO = userFeignClient.getUserInfo(userId).getData();

        Goods goods = BeanCopyUtil.copyObject(goodsVO, Goods.class);
        goodsMapper.insert(goods);

        Notice notice = Notice.builder()
                .title(MQConstant.CHECK_TITLE)
                .content(MQConstant.CHECK_CONTENT)
                .userInfoId(userInfoDTO.getId())
                .isAdminMessage(true)
                .build();
        Message<Notice> checkNotice = MessageBuilder.withPayload(notice).build();
        streamBridge.send(MQConstant.NOTICE_EXCHANGE, checkNotice);
    }


    @Override
    public void modifyGoods(GoodsVO goodsVO, Long goodsId) {
        Goods goods=goodsMapper.selectById(goodsId);
        if(goods==null){
            throw new BizException("goods not exist");
        }
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        UserInfoDTO userInfoDTO=userFeignClient.getUserInfo(userId).getData();
        if(!Objects.equals(userInfoDTO.getId(), goods.getUserInfoId())){
            throw new BizException("you can't modify other's goods");
        }
        Goods modifyGoods= BeanCopyUtil.copyObject(goodsVO,Goods.class);
        modifyGoods.setId(goodsId);
        modifyGoods.setStatusId(1L);
        goodsMapper.updateById(modifyGoods);
    }

    @Override
    public IPage<GoodsDTO> getUserGoodsByQuery(QueryVO queryVO) {
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        UserInfoDTO userInfoDTO=userFeignClient.getUserInfo(userId).getData();
        Page<GoodsDTO> page=new Page<>(queryVO.getPageNum(),queryVO.getPageSize());
        return goodsMapper.getUserGoodsByQuery(page,queryVO,userInfoDTO.getId());
    }

    @Override
    public IPage<GoodsDTO> getAllGoodsByQuery(QueryVO queryVO) {
        Page<GoodsDTO> page=new Page<>(queryVO.getPageNum(),queryVO.getPageSize());
        return goodsMapper.getAllGoodsByQuery(page,queryVO);

    }

    @Override
    public IPage<GoodsDTO> getUncheckedGoodsByQuery(QueryVO queryVO) {
        Page<GoodsDTO> page=new Page<>(queryVO.getPageNum(),queryVO.getPageSize());
        return goodsMapper.getUncheckedGoodsByQuery(page,queryVO);
    }

    @Override
    public void approveGoods(Long goodsId) {
        Goods goods=goodsMapper.selectById(goodsId);
        if (goods==null){
            throw new BizException("goods not exist");
        }
        goods.setStatusId(2L);
        goodsMapper.updateById(goods);
        Notice notice=Notice.builder()
                .title(MQConstant.CHECK_RESULT_SUCCESS)
                .content(MQConstant.CHECK_RESULT_SUCCESS_CONTENT)
                .userInfoId(goods.getUserInfoId())
                .isAdminMessage(false)
                .build();
        Message<Notice> checkNotice= MessageBuilder.withPayload(notice).build();
        streamBridge.send(MQConstant.NOTICE_EXCHANGE,checkNotice);
    }

    @Override
    public void rejectGoods(Long goodsId) {
        Goods goods=goodsMapper.selectById(goodsId);
        if (goods==null){
            throw new BizException("goods not exist");
        }
        goods.setStatusId(3L);
        goodsMapper.updateById(goods);
        Notice notice=Notice.builder()
                .title(MQConstant.CHECK_RESULT_FAIL)
                .content(MQConstant.CHECK_RESULT_FAIL_CONTENT)
                .userInfoId(goods.getUserInfoId())
                .isAdminMessage(false)
                .build();
        Message<Notice> checkNotice= MessageBuilder.withPayload(notice).build();
        streamBridge.send(MQConstant.NOTICE_EXCHANGE,checkNotice);
    }

    @Override
    public void deleteGoods(Long goodsId) {
        Goods goods=goodsMapper.selectById(goodsId);
        if (goods==null){
            throw new BizException("goods not exist");
        }
        Jwt jwt= (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> map=jwt.getClaims();
        Long userId= (Long) map.get("userId");
        UserInfoDTO userInfoDTO=userFeignClient.getUserInfo(userId).getData();
        if(!Objects.equals(userInfoDTO.getId(), goods.getUserInfoId())){
            throw new BizException("you can't delete other's goods");
        }
        goodsMapper.deleteById(goodsId);
    }
}
