package com.chehubang.duolejie.utils;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class NumberUtils {

    public static boolean isNum(String str){
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }
}
