package com.company;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Long;

public class Main {


    public static boolean Compare(long [] a, long[] b){
        int a_len = a.length, b_len = b.length;
        if( a_len != b_len ){
            return a_len < b_len; // Если длина а меньше длины b, то возврщает true;
        }
        for( int i = a_len-1; i >-1; i--){
            if(a[i] != b[i]){
                return a[i] < b[i];
            }
        }
        return false;
    }


    public static long[] HextoDec(String basic_str,int k){
        // Hex to Decimal.

        int str_len = basic_str.length();
        int arr_len = str_len/k + ( str_len%k>0 ? 1:0 ) ; // допустим у нас 23 хекса, тогда массив разбививаем на [23/8] + 1 элемент
        StringBuffer str_buf = new StringBuffer(basic_str);
        // в туториалах по Java писали, что в буффер кинуть будет более производительней. Но я ещё подумаю, а пока пусть так )
        long[] arr = new long [arr_len];
        for(int i = k, j = 0; i<=str_len && j <arr_len; i+=k, j++){
            arr[j] = Long.parseLong(str_buf.substring(str_len-i,str_len-i+k),16);
            // lil Endian :)
        }
        if (str_len%k!=0) {
            arr[arr_len - 1] = Long.parseLong(str_buf.substring(0, str_len % k), 16);
            // Последний элемент нашего массива это те первые 23%8 элементов. Пссс, я в качестве примера взял 23 хекса, что б понятней.
        }
        return arr;
    }

    public static String DectoString(long[] dec_arr){
        int len_arr = dec_arr.length;
        StringBuffer str = new StringBuffer("");
        String str_0 = "00000000";
        for(int i = len_arr-1; i>-1; i--){
            if(dec_arr[i] == 0){
                str.append(str_0);
            }
            else {
                str.append(Long.toHexString(dec_arr[i]));
            }
        }
        String ret_str = str.toString();
        return ret_str;
    }

    public static long[] add(long[] a,long[] b){
        int a_len = a.length, b_len = b.length;
        int rez_len = ( a_len > b_len ? a_len + 1: b_len +1);
        boolean bool = Compare(a,b);
        if(bool){
            long[] temp_1 = b.clone();
            b=a.clone();
            a=temp_1.clone();
            b_len=a_len;
        }
        long[] rez = new long[rez_len];
        long two32 = 1,carry = 0;// Потому что, если я сразу сделаю long two32 = 1<<32; , то у нас выйдет фиговинка.
        two32=two32<<32; two32--;
        for(int i = 0; i < b_len; i++){
            long temp = ( a[i] + b[i] + carry );
            carry = temp >> 32;  ;
            rez[i] = temp&((two32));
        }
        for(int i = b_len;i<rez_len-1 && carry == 1 ;i++){// если у нас carry = 0 -> На кой черт тогда нам
            // перекидывать ?
            long temp = (a[i] + carry);
            carry = temp>>32;
            rez[i] = temp&((two32));
        }
        rez[rez_len-1] = carry;
        return rez;
    }

    public static void substraction(long[] a, long[] b){
        long two32 = 1, carry = 0;
        int b_len = b.length;
        two32=two32<<32;
        if(!Compare(a, b)) {
            for (int i = 0; i < b_len; i++) {
                long temp = (a[i] - b[i] - carry);
                carry = temp >>> 63;
                a[i] = carry * two32 + temp;
            }
        }else{
            System.out.println("Negative number");
        }
    }

    public static void FeelWithZeros(long[] arr){
        int len = arr.length;
        for( int i =0; i < len; i ++){
            arr[i] = 0;
        }
    }

    public static void FeelWithZeros(long[] arr,int start){
        int len = arr.length;
        for(int i = start; i < len; i++){
            arr[i] = 0;
        }
    }

    public static void FeelWithZeros(long[] arr, int start, int end){
        int len = arr.length;
        for(int i = start; i < end; i++){
            arr[i] = 0;
        }
    }

    public static long[] MulOne(long[] a, long b){
        long carry = 0;
        long two32 = 1;
        two32<<=32;two32--;
        int a_len = a.length;
        long[] c = new long[2*a_len];
        for(int i =0; i< a_len; i++){
            long temp = a[i]*b + carry;
            c[i] = temp&(two32);
            carry = temp>>32;
        }
        c[a_len] = carry;
        return c;
    }

    public static void ShiftIt(long[] arr,int i){
        int len = arr.length;
        long a = arr[0], b = 0;
        for(int j = 0; j < len/2 + i && i>0; j++ ){
            b = arr[j+1];
            arr[j+1] = a;
            a = b;
        }
    }





  public static long[] LongMul(long []a, long[] b){
        int a_len = a.length;
        long[] temp = new long[2*a_len];
        long[] c = new long[2*a_len];
        FeelWithZeros(c);
        for(int i =0; i < a_len; i++){
            temp = MulOne(a,b[i]);
            ShiftIt(temp,i);
            c = add(c,temp);
        }
        return c;
    }



    public static void main(String[] args) {
        String basic_str1 = "1FFFFFFFFFFFFF12";
        String basic_str2 = "1FFFFFFFFFFFFF2E";
        long[] a = HextoDec(basic_str1,8);
        long[] b = HextoDec(basic_str2,8);
        long[] a_1 = a.clone();
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        /*long[] rez_add = add(a,b);
        substraction(a_1,b);
        System.out.println(DectoString(rez_add));
        System.out.println(DectoString(a_1));*/
        long [] rez_mul = LongMul(a,b);
    }


}

