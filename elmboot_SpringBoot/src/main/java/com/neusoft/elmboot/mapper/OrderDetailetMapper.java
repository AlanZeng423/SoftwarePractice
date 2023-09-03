package com.neusoft.elmboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.neusoft.elmboot.po.OrderDetailet;

@Mapper
public interface OrderDetailetMapper {
    public void saveOrderDetailetBatch(List<OrderDetailet> list);

    public List<OrderDetailet> listOrderDetailetByOrderId(Integer orderOd);
}