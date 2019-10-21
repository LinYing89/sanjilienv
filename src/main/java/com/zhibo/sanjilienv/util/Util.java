package com.zhibo.sanjilienv.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Util {

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public static float scale2(float f) {
        BigDecimal b = new BigDecimal(f);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static double scale2(double f) {
        BigDecimal b = new BigDecimal(f);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static double scale(double value, int scale) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double coefficient(double value, double coefficient) {
        return value * coefficient;
    }

    public static String formatValue(String format, double value) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }

    public static int bytesToInt(byte[] by) {
        int value = 0;
        for (byte b : by) {
            value = value << 8 | (b & 0xff);
        }
        return value;
    }

    /**
     * 补码表示的字节转为int
     */
    public static int complementalBytesToInt(byte[] by) {
        //符号位
        int symbol = (by[0] & 0xff) >> 7;
        //去掉符号位
//			int b0 = by[0] & 0xff;
//			byte bb0 = (byte)(b0 & 0x7f);
        by[0] = (byte)((by[0] & 0xff) & 0x7f);

        int value = 0;
        for (byte b : by) {
            value = value << 8 | (b & 0xff);
        }
        //补码取反+1获得原码, 取反后有符号位, 所有取绝对值
        value = Math.abs(~value  + 1);
        //添加符号位
        if(symbol == 1) {
            value = value * -1;
        }
        return value;
    }

    public static long bytesToLong(byte[] by) {
        long value = 0;
        for (byte b : by) {
            value = value << 8 | (b & 0xff);
        }
        return value;
    }

    public static float bytes2Float(byte[] arr){
        int value = bytesToInt(arr);
        return Float.intBitsToFloat(value);
    }

    public static String formatValue(int value){
        //固定28个长度
        return String.format("%-28s", value);
    }

    public static void main(String[] args) {
//        byte[] by = new byte[]{0x41, 0x26, 0x00, 0x00};
//        float d = bytes2Float(by);
        int a = 23;
        String value = String.format("%-5s", a);
        System.out.println("a" + value + "a");
    }
}
