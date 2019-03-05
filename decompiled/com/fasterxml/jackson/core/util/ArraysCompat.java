package com.fasterxml.jackson.core.util;

import java.lang.reflect.Array;

public class ArraysCompat {
    public static char[] copyOf(char[] cArr, int i) {
        return copyOfRange(cArr, 0, i);
    }

    public static int[] copyOf(int[] iArr, int i) {
        return copyOfRange(iArr, 0, i);
    }

    public static <T> T[] copyOf(T[] tArr, int i) {
        return copyOfRange((Object[]) tArr, 0, i);
    }

    public static char[] copyOfRange(char[] cArr, int i, int i2) {
        int i3 = i2 - i;
        int min = Math.min(i3, cArr.length - i);
        Object obj = new char[i3];
        System.arraycopy(cArr, i, obj, 0, min);
        return obj;
    }

    public static int[] copyOfRange(int[] iArr, int i, int i2) {
        int i3 = i2 - i;
        int min = Math.min(i3, iArr.length - i);
        Object obj = new int[i3];
        System.arraycopy(iArr, i, obj, 0, min);
        return obj;
    }

    public static <T> T[] copyOfRange(T[] tArr, int i, int i2) {
        int i3 = i2 - i;
        Object[] objArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), i3);
        System.arraycopy(tArr, i, objArr, 0, Math.min(i3, tArr.length - i));
        return objArr;
    }
}
