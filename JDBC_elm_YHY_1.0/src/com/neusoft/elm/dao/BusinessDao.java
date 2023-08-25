package com.neusoft.elm.dao;

import com.neusoft.elm.po.Business;

import java.util.List;

public interface BusinessDao {

    public List<Business> listBusiness(String businessName,String businessAddress);
    public int saveBusiness(String businessName);

    int removeBusiness(int businessId);

    Business getBusinessByIdByPass(int businessId, String password);

    Business getBusinessById(Integer businessId);

    int updateBusiness(Business business);

    int updateBusinessByPassword(Integer businessId, String newPass);
}
