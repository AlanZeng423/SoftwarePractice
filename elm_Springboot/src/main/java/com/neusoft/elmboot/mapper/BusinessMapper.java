package com.neusoft.elmboot.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.neusoft.elmboot.po.Business;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BusinessMapper {
 @Select("select * from business where orderTypeId=#{orderTypeId} order by businessId")
 public List<Business> listBusinessByOrderTypeId(Integer orderTypeId);
 
 @Select("select * from business where businessId=#{businessId}")
 public Business getBusinessById(Integer businessId);

 @Update("update business set views = #{views} where businessId = #{businessId}")
 public int updateViews(Integer businessId, Integer views);

 @Update("update business set orderQuantity = #{orderQuantity} where businessId = #{businessId}")
 public int updateOrderQuantity(Integer businessId,Integer orderQuantity);
}
