package com.itql.module.util;

import java.util.List;

public class StringUtil {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static final int HEX = 0xFF;
    private static final int HEX_HALF = 0x0F;

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static int byte2Int(byte b) {
        return b & HEX;
    }

    public static int byte2Int(byte[] bytes) {
        return byte2Int(bytes, 0, bytes.length);
    }

    public static int byte2Int(List<Byte> bytes) {
        return byte2Int(bytes, 0, bytes.size());
    }

    public static int byte2Int(byte[] bytes, int start, int length) {
        if (bytes.length < start || bytes.length < length || start > length) return -1;
        int value = 0;
        int temp;
        for (int i = 0; i < length; i++) {
            value <<= 8;
            temp = bytes[i + start] & HEX;
            value |= temp;
        }
        return value;
    }

    public static int byte2Int(List<Byte> bytes, int start, int length) {
        int arrLength = bytes.size();
        if (arrLength < start || arrLength < length || start > length) return -1;
        int value = 0;
        int temp;
        for (int i = 0; i < length; i++) {
            value <<= 8;
            temp = bytes.get(i + start) & HEX;
            value |= temp;
        }
        return value;
    }

    public static byte[] int2Byte2(int i) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((i >> 8) & HEX);
        bytes[1] = (byte) (i & HEX);
        return bytes;
    }

    public static byte[] int2Byte4(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((i >> 24) & HEX);
        bytes[1] = (byte) ((i >> 16) & HEX);
        bytes[2] = (byte) ((i >> 8) & HEX);
        bytes[3] = (byte) (i & HEX);
        return bytes;
    }

    public static String byte2HexString(byte b) {
        return String.valueOf(HEX_ARRAY[b & HEX]);
    }

    public static String byte2HexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        int value;
        for (byte b : bytes) {
            value = b & HEX;
            sb.append(HEX_ARRAY[value >>> 4]);
            sb.append(HEX_ARRAY[value & HEX_HALF]);
        }
        return sb.toString();
    }

    public static String byte2HexString(List<Byte> bytes) {
        StringBuilder sb = new StringBuilder(bytes.size() * 2);
        int value;
        for (byte b : bytes) {
            value = b & HEX;
            sb.append(HEX_ARRAY[value >>> 4]);
            sb.append(HEX_ARRAY[value & HEX_HALF]);
        }
        return sb.toString();
    }

    public static byte[] hexString2Byte(String s) {
        if (isEmpty(s)) return null;
        s = s.toUpperCase();
        if (s.length() % 2 != 0) s = "0" + s;
        int length = s.length() / 2;
        char[] hexChars = s.toCharArray();
        byte[] d = new byte[length];
        int pos;
        for (int i = 0; i < length; i++) {
            pos = i * 2;
            d[i] = (byte) (HEX_ARRAY[hexChars[pos]] << 4 | HEX_ARRAY[hexChars[pos + 1]]);
        }
        return d;
    }
}
