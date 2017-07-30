package com.dailycation.base.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by hoffer on 17/7/30.
 */

public class BitmapUtil {
    public static byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
