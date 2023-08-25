package com.neusoft.elm.view;

import com.neusoft.elm.po.Admin;

import java.io.IOException;

public interface AdminView {
    public Admin login();
    //疑问1：为什么这里要返回一个类，直接返回一个Boolean不可以吗？
}
