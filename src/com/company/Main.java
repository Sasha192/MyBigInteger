package com.company;

import java.lang.reflect.Array;
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

    public static void Len_Equal(long[] a, long[] b){
        int a_len = a.length; int b_len = b.length;
        if(a_len < b_len){a = ZerosFunc(a,b_len - a_len);}
        else{b = ZerosFunc(b,a_len - b_len);}
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
        arr = null;
        System.gc();
        return rez;
    }


    public static long[] add(long[] a,long[] b,int base){
        int a_len = a.length, b_len = b.length;
        Len_Equal(a,b);
        a_len = a.length; int rez_len = a_len + 1; long[] rez = new long[rez_len];
        long two_base = 1,carry = 0; two_base=two_base<<base; two_base--;
        for(int i = 0; i < a_len; i++){
            long temp = ( a[i] + b[i] + carry );
            carry = temp >> base;  ;
            rez[i] = temp&((two_base));
        }
        rez[rez_len-1] = carry;
        rez = CutFunction(rez);
        return rez;
    }

    public static long[] subtruction(long[] a, long[] b,int base){
        long two_base = 1, carry = 0;
        int a_len = a.length, b_len = b.length;
        Len_Equal(a,b);
        b_len = b.length;
        two_base=two_base<<base;
        long[] rez = new long[b_len];
        if(!Compare(a, b)) {
            for (int i = 0; i < b_len; i++) {
                long temp = (a[i] - b[i] - carry);
                carry = temp >>> 63;
                rez[i] = carry * two_base + temp;
            }
            return rez;
        }else{
            System.out.println("Negative number");
            return a;
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
        int b_len = b.length, a_len = a.length;
        int loop_len  =(a_len < b_len ? b_len : a_len);
        Len_Equal(a,b);
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
                subtruction(r,temp,base); // Проблема в Compare.
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

//____________________LAB2___________________________//

    public static long[] zeroization(long[] b, int k){
        int rez_len = b.length + k; int b_len = b.length;
        long[] rez = new long[rez_len];
        for( int i = 0 ; i < b_len; i++){
            rez[i] = b[i];
        }
        for(int i = b_len; i < rez_len; i++){
            rez[i] = 0;
        }
        return rez;

    }

    public static long[] KillLastDigits(long[] x, int k) {
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
    }


    public static long[] Long_Mod_Add(long[] a, long[] b, long[] n){
        int base = 16;

        int a_len = a.length, b_len = b.length;
        if(a_len < b_len){a = ZerosFunc(a,b_len - a_len);}
        else{b = ZerosFunc(b,a_len - b_len);}
        a_len = a.length;
        int rez_len = a_len + 1;
        long[] rez = new long[rez_len];
        long two_base = 1,carry = 0;
        two_base=two_base<<base; two_base--;
        for(int i = 0; i < a_len; i++){
            long temp = ( a[i] + b[i] + carry );
            carry = temp >> base;  ;
            rez[i] = temp&((two_base));
        }
        rez[rez_len-1] = carry;
        rez = CutFunction(rez);
        rez = division(rez,n,base);
        return rez;
    }

    public static boolean Subt_Mod(long[] a, long[] b,long[] n){
        int base = 16;

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
            a = division(a,n,base);
            return true;
        }else{
            System.out.println("Negative number");
            return false;
        }
    }

    public static long[] LongMul_Mod(long []a, long[] b, long[] n){
        int base = 16;

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
        c = division(c,n,base);
        return c;
    }

    public static void Bit_Shift_Right(long[] arr){
        short base = 16;
        long rest = 1; rest = rest << 16;
        int length = arr.length;
        long carry = 1&(arr[length-1]);
        arr[length-1] = arr[length-1]>>1;
        for(int i = length - 2 ; i > -1 ; i--){
            arr[i] = carry*rest + arr[i];
            carry = 1&arr[i];
            arr[i] = arr[i]>>1;
        }
    }

    public static long[] Bit_Shift_Left(long[] arr){
        short base = 16;
        long rest = 1; rest = rest << 16; rest--;
        int length = arr.length;
        long[] arr_rez = new long[length+1];
        long carry = 1;
        for(int i = 0 ; i < length ; i++){
            arr_rez[i] = arr[i]<<1;
            carry = arr_rez[i]>>base;
            arr_rez[i] = arr_rez[i]&(rest);
        }
        arr_rez[length] = carry;
        return arr_rez;
    }

    public static boolean Is_Even(long[] a){
        return (a[0]&1)==0;
    }

    public static long[] Return_Min(long[] a, long[] b){
        boolean bool = Compare(a,b);
        if(bool){
            return a;
        }
        return b;
    }

    public static long[] Abs_Subt(long[] a, long[] b){
        int base = 16;
        long two_base = 1, carry = 0;
        int a_len = a.length, b_len = b.length;
        boolean bool = Compare(a,b);
        long[] ref;
        if(bool){ref = a; a = b; b = ref;}
        if(a_len < b_len){a = ZerosFunc(a,b_len - a_len);}
        else{b = ZerosFunc(b,a_len - b_len);}
        b_len = b.length;
        two_base=two_base<<base;
            for (int i = 0; i < b_len; i++) {
                long temp = (a[i] - b[i] - carry);
                carry = temp >>> 63;
                a[i] = carry * two_base + temp;
            }
            return a;
    }

    public static boolean Is_Null_Array(long[] arr){
        CutFunction(arr);
        int len = arr.length;
        boolean bool = true;
        for(int i = 0; i < len; i++){
            bool = (arr[i] == 0)&&bool;
        }
        return bool;
    }

    public static long[] Long_GCD(long[] a,long[] b){
        long[] d = new long[1];
        d[0] = 1;
        while((Is_Even(a))&&(Is_Even(b))){
            Bit_Shift_Right(a);
            Bit_Shift_Right(b);
            d = Bit_Shift_Left(d);
            d = CutFunction(d);
        }
        while(Is_Even(a)){
            Bit_Shift_Right(a);
        }
        long[] temp;
        while(!Is_Null_Array(b)){
            while(Is_Even(b) && (!Is_Null_Array(b))){
                Bit_Shift_Right(b);
                b = CutFunction(b);
            }
            temp = Return_Min(a,b);
            b = Abs_Subt(a,b);
            a = temp;
            a = CutFunction(a);
            b = CutFunction(b);

        }
        d = LongMul(d,a,16);
        return d;
    }

    public static long[] Karathuba_Shift(long[] arr, int i){
        int len = arr.length;
        long[] rez_arr = new long[len+i];
        for(int k = 0; k < i; k++){
            rez_arr[k] = 0;
        }
        for(int k = i; k < len+i; k ++){
            rez_arr[k] = arr[k-i];
        }
        return rez_arr;
    }

    public  static long[] Cut_Array(long[] arr, int n_1, int n_2){
        int len = n_2 - n_1;
        long[] rez = new long[len];
        for(int i = n_1; i < n_2; i++){
            rez[i-n_1] = arr[i];
        }
        return arr;
    }

    public static long[] Pre_Mul_Karathuba(long[] a, long[] b){

        /*

            Надо шото с этим делать. Дофига лонгов.

         */
        int len_a = a.length; int len_b = b.length;
        if(len_a==1){
            long[] elem_mul = LongMul(a,b,16);
            CutFunction(elem_mul);
            return elem_mul;
        }
        long[] a_1 = Cut_Array(a,len_a/2,len_a);
        long[] a_0 = Cut_Array(a,0,len_a/2);
        long[] b_1 = Cut_Array(b,len_b/2,len_b);
        long[] b_0 = Cut_Array(b,0,len_b/2);
        long[] p_1 = Pre_Mul_Karathuba(a_1,b_1);
        long[] p_0 = Pre_Mul_Karathuba(a_0,b_0);
        long[] p_3 = Pre_Mul_Karathuba(add(a_1,a_0,16),add(b_0,b_1,16));
        long[] p_3_1 = subtruction(p_3,p_1,16);
        long[] p_3_rez = subtruction(p_3_1,p_0,16);
        long[] rez_1 = Karathuba_Shift(p_1,len_a);
        long[] rez_2 = Karathuba_Shift(p_3_rez,len_a/2);
        long[] p_4 = add(rez_1,rez_2,16);
        long[] p_5 = add(p_4,p_0,16);


    }


    public static void main(String[] args) {
        String basic_str1 = "FFFFFFFF000";
        String basic_str2 = "FFFFFFFFFFFFF000";
        long[] a_16 = HexToArr(basic_str1,4);
        long[] b_16 = HexToArr(basic_str2,4);
        System.out.println(Arrays.toString(a_16));
        System.out.println(Arrays.toString(b_16));
        System.out.println(Arrays.toString(Karathuba_Shift(a_16,2)));
        if(a_len < b_len){a = ZerosFunc(a,b_len - a_len);}
        else{b = ZerosFunc(b,a_len - b_len);}
    }
}