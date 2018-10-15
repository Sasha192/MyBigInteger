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


    public static long[] HexToArr(String basic_str,int k){
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

    public static String ArrToHexStr(long[] dec_arr,int base){
        dec_arr = CutFunction(dec_arr); // Отрезаю старшие нули.
        int len_arr = dec_arr.length;
        StringBuffer str = new StringBuffer("");
        for(int i = len_arr-1; i>-1; i--){
            int nums_number  = ShortBilLength(16,dec_arr[i]); // к-во цифр в хексовом представлении в  данном числе
            int zeros_number = base/4 - nums_number;
            String str_0 = new String(new char[zeros_number]).replace("\0", "0");
            str.append(str_0);
            str.append(Long.toHexString(dec_arr[i]));
        }
        String ret_str = str.toString();
        return ret_str;
    }

    public static String ArrToBinStr(long[] dec_arr,int base){
        dec_arr = CutFunction(dec_arr); // Отрезаю старшие нули.
        int len_arr = dec_arr.length;
        StringBuffer str = new StringBuffer("");
        str.append(Long.toBinaryString(dec_arr[len_arr-1]));
        for(int i = len_arr-2; i>-1; i--){
            int nums_number  = ShortBilLength(2,dec_arr[i]); // к-во цифр в binary представлении числа
            int zeros_number = base - nums_number;
            String str_0 = new String(new char[zeros_number]).replace("\0", "0");
            str.append(str_0);
            str.append(Long.toBinaryString(dec_arr[i]));
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


    public static long[] add(long[] a,long[] b,int base){
        int a_len = a.length, b_len = b.length;
        if(a_len < b_len){a = ZerosFunc(a,b_len - a_len);}
        else{b = ZerosFunc(b,a_len - b_len);}
        a_len = a.length;
        int rez_len = a_len + 1;
        long[] rez = new long[rez_len];
        long two_base = 1,carry = 0;
        two_base=two_base<<base; two_base--;
/*        System.out.println("Base = " + base);
        System.out.println("A = " + Arrays.toString(a));
        System.out.println("B = " + Arrays.toString(b));
        System.out.println("Rez = " + Arrays.toString(rez));*/
        for(int i = 0; i < a_len; i++){
            long temp = ( a[i] + b[i] + carry );
            carry = temp >> base;  ;
            rez[i] = temp&((two_base));
        }
        rez[rez_len-1] = carry;
        rez = CutFunction(rez);
        return rez;
    }

    public static boolean substraction(long[] a, long[] b,int base){
        long two_base = 1, carry = 0;
        int a_len = a.length, b_len = b.length;
        if(a_len < b_len){a = ZerosFunc(a,b_len - a_len);}
        else{b = ZerosFunc(b,a_len - b_len);}
        b_len = b.length;
        two_base=two_base<<base;
        if(!Compare(a, b)) {
            for (int i = 0; i < b_len; i++) {
                long temp = (a[i] - b[i] - carry);
                carry = temp >>> 63;
                a[i] = carry * two_base + temp;
            }
            return true;
        }else{
            System.out.println("Negative number");
            return false;
        }
    }



    public static long[] MulOne(long[] a, long b, int base){
        long carry = 0;
        long two_base = 1;
        two_base<<=base;two_base--;
        int a_len = a.length;
        long[] c = new long[2*a_len];
        for(int i =0; i< a_len; i++){
            long temp = a[i]*b + carry;
            c[i] = temp&(two_base);
            carry = temp>>base;
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


    public static long[] LongMul(long []a, long[] b, int base){
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
            temp = MulOne(a,b[i],base);
            ShiftArrayElemenets(temp,i);
            c = add(c,temp,base);
        }
        return c;
    }

    public static int ShortBilLength(long base, long a){
        // base тут это базис системы исчисления. base = 10 в нашем житейском случае.
        if(a == 0 ) {
            return 1;
        }
        int rez;
        rez = (int) (Math.log(a) / Math.log(base)) + 1;
        return rez;
    }

    public static int LongBitLength(long[] arr,int base){
        int _len = arr.length;
        int max_number_in_mass_elem = ( base == 32 ? 10 : 5);
        int rez = 1;
        for( int i =_len-1;i >= 0;i--){
            // ищем первое наличие ненулевых элементов слева на право, считаем к-во цифр
            // в этом первом ненулевом элементе, а все остальные елементы заполнены полностью по 5 или 10 цифр, базы: 32 или 16.
            if(arr[i] == 0){
                continue;
            }
            rez = ShortBilLength(10,arr[i]) + max_number_in_mass_elem*i;
            break;
        }
        return rez;
    }



    public static long[] ShiftNumber(long[] arr,int k,int base){
        float max_number_in_mass_elem = ( base == 32 ? 10f : 5f);
        if(k>0) {
            int _len = (int)(k /max_number_in_mass_elem) + 1;
            long[] dec_arr = new long[_len];
            int index = k/(int)max_number_in_mass_elem, power = k%(int)max_number_in_mass_elem;
            dec_arr[index] = (long)Math.pow(10,power);
            long[] rez = LongMul(arr, dec_arr,base);
            rez = CutFunction(rez);
            return rez;// длина может увеличиться нулями со стороны старших разрядов.
        }
        return arr;
    }



    public static long[] division(long[] a, long[] b,int base){
        // Взять во внимание, что дятлы могут ввести -> 00000000 <- FFFFFFFFFFF или деление на ноль !!!!!
        int k = LongBitLength(a,base), t = LongBitLength(b,base), a_len = a.length, b_len = b.length;
        float max_number_in_mass_elem = ( base == 32 ? 10f : 5f);
        long[] r = a;
        if(a_len>=b_len){
            long[] q = new long[a_len];
            FeelWithZeros(q);
            long[] temp;
            boolean bool = Compare(a, b); // Почему Compare лучше не делать bool методом ?
            while (!bool) {
                temp = ShiftNumber(b,k-t,base);
                if(Compare(r,temp)){
                    k = k - 1;
                    continue;
                }
                substraction(r,temp,base); // Проблема в Compare.
                int index = (k - t) / (int)max_number_in_mass_elem, power = (k - t) % (int)max_number_in_mass_elem;
                q[index] += (long) Math.pow(10, power);
                bool = Compare(r, b);
            }
            return q;
        }
        System.out.println(" A less then B ");
        return a;
    }


    public static long[] LongPower(long[] a,long[] b,int base){
        String bin_string_b = new String(ArrToBinStr(b,base));
        int string_len = bin_string_b.length();
        long[] arr = new long[]{1};
        char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = bin_string_b.charAt(i);
            if(chr == '1'){
                arr = LongMul(a,arr,base);
            }
            a = LongMul(a,a,base);
            System.out.println("ARR = " + Arrays.toString(arr));
        }
        return arr;
    }





    public static void main(String[] args) {
        String basic_str1 = "A";
        String basic_str2 = "AA";
        long[] a_32 = HexToArr(basic_str1,8);
        long[] b_32 = HexToArr(basic_str2,8);
        long[] a_16 = HexToArr(basic_str1,4);
        long[] b_16 = HexToArr(basic_str2,4);
        System.out.println("A_16 = " + Arrays.toString(a_16));
        System.out.println("A_32 = " + Arrays.toString(a_32));
        System.out.println("B_16 = " + Arrays.toString(b_16));
        System.out.println("B_32 = " + Arrays.toString(b_32));
        /*long[] rez_32 = LongMul(a_32,b_32,32);
        long[] rez_16 = LongMul(a_16,b_16,16);
        System.out.println("LONGMUL_REZ_32 = " + Arrays.toString(rez_32));
        System.out.println("LONGMUL_REZ_16 = " + Arrays.toString(rez_16));
        System.out.println("LONGMUL_HEX_32 = " + ArrToHexStr(rez_32,32));
        System.out.println("LONGMUL_HEX_16 = " + ArrToHexStr(rez_16,16));
        long[] rez_div_32 = division(rez_32,b_32,32);
        long[] rez_div_16 = division(rez_16,b_16,16);
        System.out.println("Division, A_16 = " + Arrays.toString(rez_div_16));
        System.out.println("Division, A_32 = " + Arrays.toString(rez_div_32));*/
        long[] _power_rez_32 = LongPower(a_32,b_32,32);
        long[] _power_rez_16 = LongPower(a_16,b_16,16);
        System.out.println("LongPower, REZ_32 = " + Arrays.toString(_power_rez_32));
        System.out.println("LongPower, REZ_16 = " + Arrays.toString(_power_rez_16));
        System.out.println(ArrToHexStr(_power_rez_16,16));

    }


}