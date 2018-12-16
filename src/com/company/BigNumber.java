package com.company;

import java.util.Arrays;

public class BigNumber extends ABigNumber {
    long[] mod;


    // Compare переделать. Можно найти i , j - индексы, когда нули закончились.
    // Везде, где фигурирует compare можно ли переписать под идею вверху ?

    private long[] cutFunction(long[] arr){
        int _len = arr.length;
        int i = _len - 1;
        while (true){
            if(arr[i] != 0 || i==0){
                break;
            }
            i--;
        }
        long[] new_arr = new long[i+1];
        for(int k = 0; k < i+1; k++){
            new_arr[k] = arr[k];
        }
        return new_arr;
    }

    public long[] zeroization(long[] arr,int k){
        if(k==0){
            return arr;
        }
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


    public int compare(long [] a, long[] b){
        int alen = a.length; int blen = b.length;
        for(int i = alen - 1; i > 0; i--){
            if(a[i]!=0){
                alen = i;
                break;
            }
        }
        for(int j = blen - 1; j > 0; j--){
            if(b[j]!=0){
                blen = j;
                break;
            }
        }
        if( alen != blen ){
            return Long.signum(alen - blen);
        }
        for( int i = alen - 1; i > -1; i--){
            if(a[i] != b[i]){
                return Long.signum(a[i] - b[i]);
            }
        }
        return 0;
    }

    private int shortBitLength(long base, long a){
        if(a == 0 ) {
            return 1;
        }
        int rez;
        rez = (int) (Math.log(a) / Math.log(base)) + 1;
        return rez;
    }


    private long[] MulOne(long[] a, long b){
        int base = 16;
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

    private void shiftArrayElem(long[] arr,int i){
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

    private void fillWithZeros(long[] arr){
        int len = arr.length;
        for( int i =0; i < len; i ++){
            arr[i] = 0;
        }
    }

    private String arrToBinStr(long[] dec_arr){
        int base = 16;
        dec_arr = cutFunction(dec_arr);
        int len_arr = dec_arr.length;
        StringBuffer str = new StringBuffer("");
        str.append(Long.toBinaryString(dec_arr[len_arr-1]));
        for(int i = len_arr-2; i>-1; i--){
            int nums_number  = shortBitLength(2,dec_arr[i]);
            int zeros_number = base - nums_number;
            String str_0 = new String(new char[zeros_number]).replace("\0", "0");
            str.append(str_0);
            str.append(Long.toBinaryString(dec_arr[i]));
        }
        String ret_str = str.toString();
        return ret_str;
    }



    private long[] karathubaShift(long[] arr, int i){
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

    private long[] cutArray(long[] arr, int n_1, int n_2){
        int len = n_2 - n_1;
        long[] rez = new long[len];
        for(int i = n_1; i < n_2; i++){
            rez[i-n_1] = arr[i];
        }
        return rez;
    }

    private int longBitLength(long[] arr,int base){
        int _len = arr.length;
        int max_number_in_mass_elem = ( base == 32 ? 10 : 5);
        int rez = 1;
        for( int i =_len-1;i >= 0;i--){
            if(arr[i] == 0){
                continue;
            }
            rez = shortBitLength(10,arr[i]) + max_number_in_mass_elem*i;
            break;
        }
        return rez;
    }

    private long[] shiftBigNumber(long[] arr,int k){ // умножение числа на 10^k;
        float max_number_in_mass_elem = 5f;
        if(k>0) {
            int _len = (int)(k /max_number_in_mass_elem) + 1;
            long[] dec_arr = new long[_len];
            int index = k/(int)max_number_in_mass_elem, power = k%(int)max_number_in_mass_elem;
            dec_arr[index] = (long)Math.pow(10,power);
            long[] rez = mul(arr, dec_arr,this.mod);
            rez = cutFunction(rez);
            return rez;
        }
        return arr;
    }


    private void bitShiftRight(long[] arr){
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
    private long[] bitShiftLeft(long[] arr){
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

    private boolean isEven(long[] a){
        return (a[0]&1)==0;
    }

    private long[] Return_Min(long[] a, long[] b){
        int compare =  compare(a,b);
        if(compare < 0){
            return a;
        }
        return b;
    }

    private long[] absSub(long[] a, long[] b){
        int base = 16;
        long two_base = 1, carry = 0;
        int a_len = a.length, b_len = b.length;
        int compare = compare(a,b);
        long[] ref;
        if(compare < 0){ref = a; a = b; b = ref;}
        if(a_len < b_len){a = zeroization(a,b_len - a_len);}
        else{b = zeroization(b,a_len - b_len);}
        b_len = b.length;
        two_base=two_base<<base;
        for (int i = 0; i < b_len; i++) {
            long temp = (a[i] - b[i] - carry);
            carry = temp >>> 63;
            a[i] = carry * two_base + temp;
        }
        return a;
    }

    private boolean isNull(long[] arr){
        cutFunction(arr);
        int len = arr.length;
        boolean bool = true;
        for(int i = 0; i < len; i++){
            bool = (arr[i] == 0)&&bool;
        }
        return bool;
    }


    /* --------------------------------------*/


    /* ----------------PUBLIC----------------*/


    /* --------------------------------------*/

    public long[] hexToArray(String str){
        int k = 4;
        int str_len = str.length();
        int arr_len = str_len/k + ( str_len%k>0 ? 1:0 ) ;
        StringBuffer str_buf = new StringBuffer(str);
        long[] arr = new long [arr_len];
        for(int i = k, j = 0; i<=str_len && j <arr_len; i+=k, j++){
            arr[j] = Long.parseLong(str_buf.substring(str_len-i,str_len-i+k),16);
        }
        if (str_len%k!=0) {
            arr[arr_len - 1] = Long.parseLong(str_buf.substring(0, str_len % k), 16);
        }
        return arr;
    }
    public String arrayToHex(long[] arr){
        int k = 4;
        arr = cutFunction(arr); // Отрезаю старшие нули.
        int len_arr = arr.length;
        StringBuffer str = new StringBuffer("");
        for(int i = len_arr-1; i>-1; i--){
            int nums_number  = shortBitLength(16,arr[i]); // к-во цифр в хексовом представлении в  данном числе
            int zeros_number = k/4 - nums_number;
            String str_0 = new String(new char[zeros_number]).replace("\0", "0");
            str.append(str_0);
            str.append(Long.toHexString(arr[i]));
        }
        String ret_str = str.toString();
        return ret_str;
    }
    public long[] add(long[] a, long[] b,long[] mod){
        a = cutFunction(a);
        b = cutFunction(b);
        int base = 16;
        int a_len = a.length, b_len = b.length;
        if(a_len < b_len){a = zeroization(a,b_len - a_len);}
        else{b = zeroization(b,a_len - b_len);}
        a_len = a.length;
        int rez_len = a_len + 1; long[] rez = new long[rez_len];
        long two_base = 1,carry = 0; two_base=two_base<<base; two_base--;
        int i = 0;
        for(i = i; i < a_len; i++){
            long temp = ( a[i] + b[i] + carry );
            carry = temp >> base;  ;
            rez[i] = temp&((two_base));
        }
        rez[i] = rez[i] + carry;
        return rez;
    }
    public long[] sub(long[] a, long[] b, long[] mod){
        int base = 16;
        long two_base = 1, carry = 0;
        int a_len = a.length, b_len = b.length;
        if(a_len < b_len){a = zeroization(a,b_len - a_len);}
        else{b = zeroization(b,a_len - b_len);}
        b_len = b.length;
        two_base=two_base<<base;
        long[] rez = new long[b_len];
        if(compare(a, b) >= 0) {
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


    public long[] div(long[] a, long[] b){
        int base = 16;
        int k = longBitLength(a,base), t = longBitLength(b,base), a_len = a.length, b_len = b.length;
        float max_number_in_mass_elem = 5f;
        long[] r = a;
        if(a_len>=b_len){
            int compare = compare(a, b);
            long[] q = new long[a_len];
            fillWithZeros(q);
            long[] temp;
            while (compare >= 0) {
                temp = shiftBigNumber(b,k-t);
                if(compare(r,temp) < 0){
                    k = k - 1;
                    continue;
                }
                sub(r,temp,this.mod);
                int index = (k - t) / (int)max_number_in_mass_elem, power = (k - t) % (int)max_number_in_mass_elem;
                q[index] += (long) Math.pow(10, power);
                compare = compare(r, b);
            }
            return q;
        }
        return a;
    }


    public long[] mul(long[] a,long[] b, long[] mod){
        a = cutFunction(a);
        b = cutFunction(b);
        int base = 16;
        int b_len = b.length, a_len = a.length;
        if(a_len < b_len){a = zeroization(a,b_len - a_len);}
        else{b = zeroization(b,a_len - b_len);}
        a_len = a.length;
        long[] temp;
        long[] c = new long[2*a_len];
        fillWithZeros(c);
        for(int i =0; i < a_len; i++){
            temp = MulOne(a,b[i]);
            shiftArrayElem(temp,i);
            c = add(c,temp,mod);
        }
        return c;
    }
    public long[] getSquared(long[] a, long[] mod ){
        long[] res = mul(a,a,mod);
        return res;
    }
    public long[] getPower(long[] a,long[] b, long[]mod){
        String bin_string_b = new String(arrToBinStr(b));
        int string_len = bin_string_b.length();
        long[] arr = new long[]{1};
        char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = bin_string_b.charAt(i);
            if(chr == '1'){
                arr = mul(a,arr,mod);
            }
            a = mul(a,a,mod);
        }
        return arr;
    }



    public long[] getGCD(long[] a, long[] b){
        long[] d = new long[1];
        d[0] = 1;
        while((isEven(a))&&(isEven(b))){
            bitShiftRight(a);
            bitShiftRight(b);
            d = bitShiftLeft(d);
            d = cutFunction(d);
        }
        while(isEven(a)){
            bitShiftRight(a);
        }
        long[] temp;
        while(!isNull(b)){
            while(isEven(b) && (!isNull(b))){
                bitShiftRight(b);
                b = cutFunction(b);
            }
            temp = Return_Min(a,b);
            b = absSub(a,b);
            a = temp;
            a = cutFunction(a);
            b = cutFunction(b);

        }
        d = mul(d,a,mod);
        return d;
    }


    public long[] mulKarathuba(long[] a, long[] b){
        int alen = a.length; int blen = b.length;
        if(a.length != b.length){
            System.out.println("Smth is going wrong");
            return new long[1];
        }
        if(alen < 5){
            long[] elem_mul = mul(a,b,this.mod);
            return elem_mul;
        }
        if(a.length%2 == 1){
            a = zeroization(a,1);
            alen = a.length;
        }
        if(b.length%2 == 1){
            b = zeroization(b,1);
            blen = b.length;
        }
        long[] a_1 = cutArray(a,alen/2,alen);
        long[] a_0 = cutArray(a,0,alen/2);
        long[] b_1 = cutArray(b,blen/2,blen);
        long[] b_0 = cutArray(b,0,blen/2);
        long[] p_1 = mulKarathuba(a_1,b_1);
        long[] p_0 = mulKarathuba(a_0,b_0);
        long[] arg1 = mulKarathuba(a_1,b_0);
        long[] arg2 = mulKarathuba(a_0,b_1);
        long[] p_3  = add(arg1,arg2,new long[1]);
        p_3 = karathubaShift(p_3,alen/2);
        p_1 = karathubaShift(p_1,alen);
        p_1 = add(p_1,p_3,this.mod);
        p_1 = add(p_1,p_0,this.mod);
        p_1 = cutFunction(p_1);
        return p_1;
    }


    public long[] getLCM(long[] a, long[] b){
        long[] prd1 = mul(a,b,this.mod);
        long[] prd2 = getGCD(a,b);
        long[] res = div(prd1,prd2);
        return res;

    }
}
