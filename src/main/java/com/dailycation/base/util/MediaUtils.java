package com.dailycation.base.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 媒体工具类
 */
public class MediaUtils {

    /**
     * 获取所有本地视频列表
     * @param context
     * @return
     */
    public static void getVideoList(final Context context, final Handler handler) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<EntityVideo> sysVideoList = new ArrayList<>();
                String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID};
                String[] mediaColumns = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION};

                Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
                if (cursor!=null && cursor.moveToFirst()) {
                    do {
                        EntityVideo info = new EntityVideo();
                        int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                        Cursor thumbCursor = context.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                                thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                        + "=" + id, null, null);
                        if (thumbCursor.moveToFirst()) {
                            info.setThumbPath(thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                        }
                        info.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                        info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
                        //把没有缩略图的视频排除
                        //if(info.getThumbPath()!=null)
                        sysVideoList.add(info);
                    } while (cursor.moveToNext());
                }
                Message message = new Message();
                message.obj = sysVideoList;
                handler.sendMessage(message);
            }
        });
        thread.start();
    }

    /**
     * 文件拷贝
     * @param src
     * @param dst
     * @throws IOException
     */
    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    /**
     * 文件重命名
     * @param originPath
     * @param destName
     */
    public static String renameFile(String originPath,String destName){
        File from = new File(originPath);
        String path = from.getParent();
        File to = new File(path + destName);
        if(from.exists())
            from.renameTo(to);
        return to.getAbsolutePath();
    }

    public static class EntityVideo{
        /**
         * 缩略图位置
         */
        String thumbPath;

        /**
         * 视频位置
         */
        String path;

        /**
         * 视频长度
         */
        int duration;

        public void setThumbPath(String thumbPath) {
            this.thumbPath = thumbPath;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getThumbPath() {
            return thumbPath;
        }

        public String getPath() {
            return path;
        }

        public int getDuration() {
            return duration;
        }
    }
}
