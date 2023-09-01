package com.neusoft.elmboot.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.service.UserService;
@RestController
@RequestMapping("/UserController")
public class UserController {
 
 @Autowired
 private UserService userService;

 @RequestMapping("/getUserByIdByPass")
 public User getUserByIdByPass(User user) throws Exception{
 return userService.getUserByIdByPass(user);
 }
 
 @RequestMapping("/getUserById")
 public int getUserById(User user) throws Exception{
 return userService.getUserById(user.getUserId());
 }
 
 @RequestMapping("/saveUser")
 public int saveUser(User user) throws Exception{
 return userService.saveUser(user);
 }
 @RequestMapping("/updatePoint")
 public int updatePoint(User user)throws Exception{
  return userService.updatePoint(user);
 }

 @RequestMapping("/getPointById")
 public double getPointById(User user)throws Exception{
  return userService.getPointById(user.getUserId());
 }
}
