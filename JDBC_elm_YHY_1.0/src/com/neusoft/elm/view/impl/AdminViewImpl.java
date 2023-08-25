package com.neusoft.elm.view.impl;

import com.neusoft.elm.dao.AdminDao;
import com.neusoft.elm.dao.impl.AdminDaoImpl;
import com.neusoft.elm.po.Admin;
import com.neusoft.elm.view.AdminView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class AdminViewImpl implements AdminView {
    //要求获取输入（方法1，BufferedReader）
    //private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //方法2：课件给出的
    private Scanner input = new Scanner(System.in);
    //使用input.next()获取，以空白字符为间隔

    public Admin login() {
        System.out.println("请输入管理员用户名：");
        String adminName = input.next();
        System.out.println("请输入密码：");
        String password = input.next();
        //已经获取了对应的内容，接下来要从DB中提取数据进行比较（交给DAO层）
        AdminDao admindao = new AdminDaoImpl();
        admindao.getAdminByNameByPass(adminName, password);

        return null;
    }
}
