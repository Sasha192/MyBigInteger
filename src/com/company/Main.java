package com.company;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Long;
import java.lang.Math.*;



public class Main {
    public static void main(String[] args) {
        BigNumber obj = new BigNumber();
        String str1 = "AAAAAAAAAAA";
        String str2 = "AAAAAA";
        String str3 = "24421214411";
       // String str3 = "1";
        long[] arg1 = obj.hexToArray(str1);
        long[] arg2 = obj.hexToArray(str2);
        long[] arg3 = obj.hexToArray(str3);
        System.out.println(Arrays.toString(obj.mulM(arg1,arg2)));
        System.out.println(Arrays.toString(obj.mulKarathuba(arg1,arg2)));
    }
}