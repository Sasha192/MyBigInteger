package com.company;

public interface IABigNumber {
    public long[] hexToArray(String str);
    public String arrayToHex(long[] arr);
    public long[] add(long[] a, long[] b,long[] mod);
    public long[] sub(long[] a, long[] b, long[] mod);
    public long[][] div(long[] a, long[] b);
    public long[] mul(long[] a,long[] b, long[] mod);
    public long[] getSquared(long[] a);
    public long[] getPower(long[] a,long[] b);
    public long[] getGCD(long[] a, long[] b);
    public long[] getLCM(long[] a, long[] b);
    public int compare(long[] a, long[] b);
}
