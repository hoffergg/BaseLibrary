package com.dailycation.base.util;

import android.content.Context;
import android.text.TextUtils;

import com.dailycation.base.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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

    public static boolean isPhoneNumber(String phone) {
        return !TextUtils.isEmpty(phone) && phone.length() == 11;
    }

    /**
     * 获取格式化的间隔时间
     * @return
     */
    public static String getFormatSpanTime(Context context,long time){
        String[] timeUnitArray = context.getResources().getStringArray(R.array.time_unit);
        if(time>60*24*3600*1000L){
            return "60 " + timeUnitArray[3];
        }else if(time>30*24*3600*1000L){
            return "30 " + timeUnitArray[3];
        }else if(time>15*24*3600*1000L){
            return "15 " + timeUnitArray[3];
        }else if(time>3*24*3600*1000L){
            return "3 " + timeUnitArray[3];
        }else if(time>24*3600*1000L){
            return "1 " + timeUnitArray[2];
        }else if(time>2*3600*1000){
            return time/(3600*1000) + " " + timeUnitArray[5];
        }else if(time>3600*1000){
            return "1 " + timeUnitArray[4];
        }else if(time>60*1000){
            return time/(60*1000) + " " + timeUnitArray[7];
        }else if(time>1000){
            return time/1000 + " " + timeUnitArray[8];
        }
        return "";
    }

    /**
     * 把时间长度格式化可读
     * @param seconds
     * @return
     */
    public static String getDurationTime(int seconds){
      return "00:00";
    }

    public static String formatTime(long timeMs) {
        return String.format(Locale.CHINA, "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(timeMs),
                TimeUnit.MILLISECONDS.toSeconds(timeMs) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeMs))
        );
    }

    /**
     * 格式化数据显示
     * @param count
     * @return
     */
    public static String formatCount(Context context,long count) {
        if(count>10000){
            return String.valueOf((float) count/10000) + "w";
        }else if(count>1000){
            return String.valueOf((float) count/1000) + "k";
        }else {
            return String.valueOf(count);
        }
    }

    public static String getFormatDate(int year,int month,int day){
        return year + "-" + (month+1)+ "-" + day;
    }

    public static String getFormatDate(Date date){
        if(date!=null)
            return date.getYear()+1900 + "-" + (date.getMonth()+1)+ "-" + date.getDay();
        return null;
    }
}
