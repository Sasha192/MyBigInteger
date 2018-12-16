package com.company;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Long;
import java.lang.Math.*;



public class Main {

/*    public static long[] KillLastDigits(long[] x, int k) {
        int x_len = x.length; boolean bool = x_len > k;
        if(!bool){
            return new long[1];
        }
        long[] rez_arr = new long[x_len - k];
        for( int i = k, j = 0 ; i < x_len; i++, j++){
            rez_arr[j] = x[i];
        }
        return rez_arr;
    }

    public static long[] BarretRedution(long[] arr, long[] m, long[] n){
        int k = arr.length/2;
        long[] q = KillLastDigits(arr,k-1);
        q = LongMul(m,q,16);
        q = KillLastDigits(q,k+1);
        long[] x = LongMul(q,n,16);
        boolean bool = subtruction(arr,x,16);
        if(!bool){
            System.out.println("Kakayato dich vishla. Negative number");
            return new long[1];
        }
        while (!Compare(arr,n)){
            subtruction(arr,n,16);
            System.out.println("ARR = " + Arrays.toString(arr));
            System.out.println("N = " + Arrays.toString(n));
        }
        return arr;
    }


    public static long[] Long_ModBarrett_Power(long[] a,long[] b,long[] m,long[] n){
        int base = 16;
        String bin_string_b = new String(ArrToBinStr(b,base));
        int string_len = bin_string_b.length();
        long[] arr = new long[]{1};
        char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = bin_string_b.charAt(i);
            if(chr == '1'){
                arr = LongMul(a,arr,base);
                arr = BarretRedution(arr,m,n);
            }
            a = LongMul(a,a,base);
            a = BarretRedution(a,m,n);
            System.out.println("ARR = " + Arrays.toString(arr));
        }
        return arr;
    }*/



    public static void main(String[] args) {
        BigNumber obj = new BigNumber();
        String str1 = "FFFFFFFFFFF141341AAAAA";
        String str2 = "AAAAAAA42535FFFFFCCC";
        String str3 = "AAFFF2242535FFFFFCCC";
        long[] arg1 = obj.hexToArray(str1);
        long[] arg2 = obj.hexToArray(str2);
        long[] arg3 = obj.hexToArray(str3);
        int a_len = arg1.length, b_len = arg2.length;
        if(a_len < b_len){arg1 = obj.zeroization(arg1,b_len - a_len);}
        else{arg2 = obj.zeroization(arg2,a_len - b_len);}
        long[] prod1 = obj.mul(arg1,arg2,new long[1]);
        long[] prod2 = obj.mulKarathuba(arg1,arg2);
        System.out.println(Arrays.toString(prod1));
        System.out.println(Arrays.toString(prod2));


    }
}