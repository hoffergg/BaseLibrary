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


}
