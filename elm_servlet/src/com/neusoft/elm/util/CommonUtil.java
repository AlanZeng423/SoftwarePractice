package com.neusoft.elm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonUtil {
    public static String getCurrentDate(){
        //字面意思 得到当前的时间，并且改成对应的形式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }
}
