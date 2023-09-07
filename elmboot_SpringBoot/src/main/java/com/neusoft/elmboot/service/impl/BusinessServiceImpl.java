package com.neusoft.elmboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neusoft.elmboot.mapper.BusinessMapper;
import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.service.BusinessService;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public List<Business> listBusinessByOrderTypeId(Integer orderTypeId) {
        return businessMapper.listBusinessByOrderTypeId(orderTypeId);
    }

    @Override
    public Business getBusinessById(Integer businessId) {
        return businessMapper.getBusinessById(businessId);
    }

    @Override
    public int updateViews(Integer businessId, Integer views) {
        return businessMapper.updateViews(businessId,views);
    }

    @Override
    public int updateOrderQuantity(Integer businessId, Integer orderQuantity) {
        return businessMapper.updateOrderQuantity(businessId,orderQuantity);
    }

    @Override
    public List<Business> listBusinessSortByView() {
        return businessMapper.listBusinessSortByView();
    }

    @Override
    public List<Business> listBusinessSortByOrderQuantity() {
        return businessMapper.listBusinessSortByOrderQuantity();
    }

    @Override
    public List<Business> listBusinessSortByAverage() {
        return businessMapper.listBusinessSortByAverage();
    }

    @Override
    public List<Business> listBusinessByBusinessName(String businessName) {
        return businessMapper.listBusinessByBusinessName(businessName);
    }
}