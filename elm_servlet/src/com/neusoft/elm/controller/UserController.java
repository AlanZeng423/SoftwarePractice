package com.neusoft.elm.controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.neusoft.elm.po.User;
import com.neusoft.elm.service.UserService;
import com.neusoft.elm.service.impl.UserServiceImpl;
public class UserController {
    public Object getUserByIdByPass(HttpServletRequest request) throws Exception{
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        UserService service = new UserServiceImpl();
        User user = service.getUserByIdByPass(userId, password);
        return user;
    }

    public Object getUserById(HttpServletRequest request) throws Exception{
        String userId = request.getParameter("userId");
        UserService service = new UserServiceImpl();
        int result = service.getUserById(userId);
        return result;
    }

    public Object saveUser(HttpServletRequest request) throws Exception{
        User user = new User();
        user.setUserId(request.getParameter("userId"));
        user.setPassword(request.getParameter("password"));
        user.setUserName(request.getParameter("userName"));
        user.setUserSex(Integer.valueOf(request.getParameter("userSex")));
        UserService service = new UserServiceImpl();
        int result = service.saveUser(user);
        return result;
    }

    //关于对积分的更新情况，只有成功购物之后才会更新（包括了对积分的增加和减少）
    public Object updatePoint(HttpServletRequest request) throws Exception{
        String userId = request.getParameter("userId");
        double point = Double.parseDouble(request.getParameter("point"));
        UserService service = new UserServiceImpl();
        int result = service.updatePoint(userId,point);
        return result;
    }

    public Object getPointById(HttpServletRequest request) throws Exception{
        String userId = request.getParameter("userId");
        UserService service = new UserServiceImpl();
        double result = service.getPointById(userId);
        return result;
    }
}