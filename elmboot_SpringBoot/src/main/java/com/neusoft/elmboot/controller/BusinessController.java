package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.service.BusinessService;

@RestController
@RequestMapping("/BusinessController")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping("/listBusinessByOrderTypeId")
    public List<Business> listBusinessByOrderTypeId(Business business) throws Exception {
        return businessService.listBusinessByOrderTypeId(business.getOrderTypeId());
    }

    @RequestMapping("/getBusinessById")
    public Business getBusinessById(Business business) throws Exception {
        return businessService.getBusinessById(business.getBusinessId());
    }

    //新增浏览量
    @RequestMapping("/updateViews")
    public int updateViews(Business business) throws Exception{
        return businessService.updateViews(business.getBusinessId(),business.getViews());
    }
    //新增购买量
    @RequestMapping("/updateOrderQuantity")
    public int updateOrderQuantity(Business business) throws Exception{
        return businessService.updateOrderQuantity(business.getBusinessId(),business.getOrderQuantity());
    }
    //获得商家顺序
    @RequestMapping("/listBusinessSortByView")
    public List<Business> listBusinessSortByView()throws Exception{
        return businessService.listBusinessSortByView();
    }

    @RequestMapping("/listBusinessSortByOrderQuantity")
    public List<Business> listBusinessSortByOrderQuantity()throws Exception{
        return businessService.listBusinessSortByOrderQuantity();
    }

    @RequestMapping("/listBusinessSortByAverage")
    public List<Business> listBusinessSortByAverage()throws Exception{
        return businessService.listBusinessSortByAverage();
    }
}