package com.xzit.logisticscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.common.logistics.entity.Arrangement;
import com.xzit.common.logistics.entity.Center;
import com.xzit.common.logistics.model.dto.CenterDTO;
import com.xzit.common.logistics.model.vo.CenterVO;
import com.xzit.common.sys.exception.BizException;
import com.xzit.common.sys.function.Polymerization;
import com.xzit.common.sys.model.vo.QueryVO;
import com.xzit.common.sys.utils.BeanCopyUtil;
import com.xzit.logisticscenter.mapper.ArrangementMapper;
import com.xzit.logisticscenter.mapper.CenterMapper;
import com.xzit.logisticscenter.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {
    private final CenterMapper centerMapper;
    private final ArrangementMapper arrangementMapper;
    @Override
    public Boolean addLogisticsCenter(CenterVO centerVO) {
        if (centerVO == null) {
            return false;
        }

        if (isEmpty(centerVO.getName()) ||
                isEmpty(centerVO.getAddress()) ||
                centerVO.getAreaId() == null ||
                centerVO.getLongitude() == null ||
                centerVO.getLatitude() == null ||
                centerVO.getMaxSpace() == null) {
            return false;
        }
        Center center= BeanCopyUtil.copyObject(centerVO, Center.class);
        center.setFreeSpace(centerVO.getMaxSpace());
        return centerMapper.insert(center)==1;
    }

    @Override
    public IPage<CenterDTO> getActiveCenterByQuery(QueryVO queryVO) {
        Page<CenterDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return centerMapper.getActiveCenterByQuery(page,queryVO);
    }

    @Override
    public Boolean disableCenter(Long id) {
        Center center=centerMapper.selectById(id);
        if (center==null||center.getIsDisable()){
            return false;
        }
        center.setIsDisable(true);
        return centerMapper.updateById(center)==1;
    }

    @Override
    public IPage<CenterDTO> getDisableCenterByQuery(QueryVO queryVO) {
        Page<CenterDTO> page=new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return centerMapper.getDisableCenterByQuery(page,queryVO);
    }

    @Override
    public Boolean activeCenter(Long id) {
        Center center=centerMapper.selectById(id);
        if(center==null||!center.getIsDisable()){
            return false;
        }
        center.setIsDisable(false);
        return centerMapper.updateById(center)==1;
    }

    @Override
    public Boolean modifyCenter(CenterVO centerVO,Long centerId) {
        Center center=updateCheck().apply(centerVO,centerId);
        return centerMapper.updateById(center)==1;
    }

    private boolean isEmpty(String str) {
        return str == null || StringUtils.isEmpty(str);
    }
    private Polymerization<CenterVO,Long,Center> updateCheck(){
        return (centerVO,centerId)->{
            if(centerVO==null||centerId==null){
                throw new BizException("参数不能为空");
            }
            Center center=centerMapper.selectById(centerId);
            if(center==null){
                throw new BizException("中心不存在");
            }
            if(centerVO.getAddress()!=null){
                center.setAddress(centerVO.getAddress());
            }
            if(centerVO.getMaxSpace()!=null){
                QueryWrapper<Arrangement> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("to_id",centerId).not(q->q.eq("status_id",3));
                List<Arrangement> arrangementList=arrangementMapper.selectList(queryWrapper);
                if(arrangementList.isEmpty()){
                    center.setMaxSpace(centerVO.getMaxSpace());
                    center.setFreeSpace(centerVO.getMaxSpace());
                }
                else {
                    throw new BizException("中心有未完成的任务，不能修改容量");
                }
            }
            if(centerVO.getLongitude()!=null){
                center.setLongitude(centerVO.getLongitude());
            }
            if(centerVO.getLatitude()!=null){
                center.setLatitude(centerVO.getLatitude());
            }
            if(centerVO.getAreaId()!=null){
                center.setAreaId(centerVO.getAreaId());
            }
            if(centerVO.getName()!=null){
                center.setName(centerVO.getName());
            }
            return center;
        };
    }

}
