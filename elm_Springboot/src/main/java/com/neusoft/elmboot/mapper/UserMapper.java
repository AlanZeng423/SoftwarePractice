package com.neusoft.elmboot.mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.neusoft.elmboot.po.User;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
 @Select("select * from user where userId=#{userId} and password=#{password}")
 public User getUserByIdByPass(User user);
 //明显比JDBC的要方便很多，不需要额外的改了

 @Select("select count(*) from user where userId=#{userId}")
 public int getUserById(String userId);
 
 @Insert("insert into user values(#{userId},#{password},#{userName},#{userSex},null,1,0)")
 public int saveUser(User user);

 @Update("update user set point = #{point} where userId = #{userId}")
 public int updatePoints(User user);
}