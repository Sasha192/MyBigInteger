package com.company;

import java.util.Arrays;
import java.util.Scanner;
import java.lang.Long;
import java.lang.Math.*;



public class Main {
    public static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }
    public static long[] CutFunction(long[] arr){

        int _len = arr.length;
        int i = _len - 1;
        for(i = i; i > 0; i--){ // но оставляем самые младшие нули, если все число [0,0,0,0,0]
            if(arr[i] != 0){
                break;
            }
        }
        long[] new_arr = new long[i+1];
        for(int k = 0; k < i+1; k++){
            new_arr[k] = arr[k];
        }
        return new_arr;

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


    public static boolean Compare(long [] a, long[] b){
        long[] copy_a = a.clone();
        long[] copy_b = b.clone();
        copy_a = CutFunction(copy_a);
        copy_b = CutFunction(copy_b);
        int a_len = copy_a.length, b_len = copy_b.length;
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
        dec_arr = CutFunction(dec_arr); // Отрезаю старшие нули.
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

    public static long[] ZerosFunc(long[] arr,int k){
        int rez_len = arr.length + k, a_len = arr.length;
        long[] rez = new long[rez_len];
        for( int i = 0; i < a_len; i ++){
            rez[i] = arr[i];
        }
        for(int j = a_len; j < rez_len; j ++){
            rez[j] = 0;
        }
        return rez;
    }


    public static long[] add(long[] a,long[] b){
        int a_len = a.length, b_len = b.length;
        if(a_len < b_len){a = ZerosFunc(a,b_len - a_len);}
        else{b = ZerosFunc(b,a_len - b_len);}
        int rez_len = a_len + 1;
        long[] rez = new long[rez_len];
        long two32 = 1,carry = 0;
        two32=two32<<32; two32--;
        for(int i = 0; i < a_len; i++){
            long temp = ( a[i] + b[i] + carry );
            carry = temp >> 32;  ;
            rez[i] = temp&((two32));
        }
        rez[rez_len-1] = carry;
        return rez;
    }

    public static boolean substraction(long[] a, long[] b){
        long two32 = 1, carry = 0;
        int a_len = a.length, b_len = b.length;
        if(a_len < b_len){a = ZerosFunc(a,b_len - a_len);}
        else{b = ZerosFunc(b,a_len - b_len);}
        b_len = b.length;
        two32=two32<<32;
        if(!Compare(a, b)) {
            for (int i = 0; i < b_len; i++) {
                long temp = (a[i] - b[i] - carry);
                carry = temp >>> 63;
                a[i] = carry * two32 + temp;
            }
            return true;
        }else{
            System.out.println("Negative number");
            return false;
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

    public static void ShiftArrayElemenets(long[] arr,int i){
        int len = arr.length;
        if( i != 0 ) {
            for (int j = len - 1; j > -1; j--) {
                if(j+i >= len ){
                    continue;
                }
                arr[j + i] = arr[j];
                arr[j] = 0;
            }
        }
    }


    public static long[] LongMul(long []a, long[] b){
        /*

                LongMul выдает правильный ответ с точностью в ~5% :
                Короче, прикол в том, что около 5% из всех букв отличаются
                с заданым наперед ответом.

        */
        int b_len = b.length, a_len = a.length;
        int loop_len  =(a_len < b_len ? b_len : a_len);
        if(a_len < b_len){a = ZerosFunc(a,b_len - a_len);}
        else{b = ZerosFunc(b,a_len - b_len);}
        long[] temp;
        long[] c = new long[2*a_len];
        FeelWithZeros(c);
        for(int i =0; i < loop_len; i++){
            temp = MulOne(a,b[i]);
            ShiftArrayElemenets(temp,i);
            c = add(c,temp);
        }
        return c;
    }

    public static int ShortBilLength(long base, long a){
        int rez;
        rez = (int)(Math.log(a)/Math.log(base)) + 1;
        return rez;
    }

    public static int LongBitLength(long[] arr){ // Потому что со стороны старших разрядов в элементах массива
        // могут быть нули 0000000000 | 1221412 | 0000000000  - разбиение длинного числа в массиве. справа налево ростут индексы.
        int _len = arr.length;
        int rez = 1;
        for( int i =_len-1;i >= 0;i--){
            if(arr[i] == 0){
                continue;
            }
            rez = ShortBilLength(10,arr[i]) + 10*i; // 10*i - все остальные заполнены, неважно чем, хоть нулями. Смотреть пример выше.
            break;
        }
        return rez;
    }



    public static long[] ShiftNumber(long[] arr,int k){
        // with help of multiplication;

        if(k>0) {
            int _len = (int)(k /10f) + 1;
            long[] dec_arr = new long[_len];
            int index = k/10, power = k%10;
            dec_arr[index] = (long)Math.pow(10,power);
            long[] rez = LongMul(arr, dec_arr);
            rez = CutFunction(rez);
            return rez;// длина может увеличиться нулями со стороны старших разрядов.
        }
        return arr;
    }



    public static long[] division(long[] a, long[] b){
        // Взять во внимание, что дятлы могут ввести -> 00000000 <- FFFFFFFFFFF или деление на ноль
        int k = LongBitLength(a), t = LongBitLength(b), a_len = a.length, b_len = b.length;
        long[] r = a;
        if(a_len>=b_len){
            long[] q = new long[a_len];
            FeelWithZeros(q);
            long[] temp;
            boolean bool = Compare(a, b); // Просмотреть Compare - можно ли улучшить ?
            while (!bool) {
                temp = ShiftNumber(b,k-t);
                if(Compare(r,temp)){
                    k = k - 1;
                    continue;
                }
                substraction(r,temp); // Проблема в Compare.
                int index = (k - t) / 10, power = (k - t) % 10;
                q[index] += (long) Math.pow(10, power);
                bool = Compare(r, b);
            }
            return q;
        }
        System.out.println(" A less then B ");
        return a;
    }

    public static long[] LongPower(long[] a,long[] b){
        long[] c = new long[1];
        c[0] = 1;
        int b_len = b.length;
        long one = 1, var = 0; //
        boolean bool = false;
        int m = ShortBilLength(2,b[b_len-1]) + 32*(b_len - 1);
        System.out.println(m%32);
        for(int i = 0,j = 0; i < m%32 && j < b_len; i++){
            System.out.println("i = " + i + '\n' + "j = " + j);
            var = one<<i;
            bool = (var&b[j]) != 0;
            long somrez = var&b[j];
            System.out.println("var = " + var);
            System.out.println("b[j] = " + b[j] + '\n' + "bool = " + bool);
            System.out.println("bool-1 = " + somrez);
            if(bool){
                c = LongMul(a,c);
            }
            a = LongMul(a,a);
            if(i==32){
                j++; i = -1;
            }
            c = CutFunction(c);
            System.out.println("c = " + Arrays.toString(c));
            System.out.println("__________________________________________________");
        }
        return c;
    }





    public static void main(String[] args) {
        String basic_str1 = "10";
        String basic_str2 = "10000";
        long[] a = HextoDec(basic_str1,8);
        long[] b = HextoDec(basic_str2,8);
        System.out.println(Arrays.toString(a));
        long[] a_1 = a.clone();
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
//      long[] rez_add = add(a,b);
//      substraction(a_1,b);
        long[] rez = HextoDec("",8);
//      long[] rez_mul = LongMul(a,b);
//        long[] q = division(a,b);
//        System.out.println("q = " + Arrays.toString(q) + "\nr = " + Arrays.toString(a));
//        System.out.println(Arrays.toString(rez));
//        System.out.println(DectoString(rez));
        long[] rez_pow = LongPower(a,b);
        System.out.println("rez_pow = " + Arrays.toString(rez_pow));


    }


}