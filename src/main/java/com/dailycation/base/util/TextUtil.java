package com.dailycation.base.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by hoffer on 17/2/10.
 */

public class TextUtil {
    public static String getSimpleTime(long l) {
        //TODO need to be done
        return "";
    }

    public static String getFormattedTime(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(l));
    }

    public static String getTimedFileName(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date(l));
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String  byteArrayToHexString(byte[] data) {
        if (data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for(byte byteChar : data)
                stringBuilder.append(String.format("%02X ", byteChar));
            return stringBuilder.toString();
        }
        return null;
    }






}
