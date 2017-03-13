package com.dailycation.base.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.dailycation.base.util.TextUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


/**
 * Log helper class,control log output in develop/publish mode.
 *
 * @author hehu
 * @version 1.0 2015/12/28
 */
public class LogHelper {
    private static final String DEFAULT_TAG = "antilost";
    public final static String TAG_MAIN = DEFAULT_TAG;
    public static final String TAG_SOURCE = "source";

    public static int LOG_LEVEL = 6;   //6 means debug mode,-1 means publish mode.
    public static int EXCEPTION = 0;
    public static int ERROR = 1;
    public static int WARN = 2;
    public static int INFO = 3;
    public static int DEBUG = 4;
    public static int VERBOSE = 5;
    public static FileOutputStream fos;
    public static FileWriter writer = null;

    /**
     * Log for UncaughtException.
     */
    public static void ex(String tag, Exception ex) {
        if (LOG_LEVEL > EXCEPTION) {
            StackTraceElement[] ste = ex.getCause().getStackTrace();
            for (int i = 0; 1 < ste.length; i++) {
                LogHelper.e(tag, "at " + ste[i].getClassName() + "." + ste[i].getMethodName()
                        + "(" + ste[i].getFileName() + ":" + ste[i].getLineNumber() + ")");
            }
            LogHelper.e(tag, ex.toString());
        }
    }

    public static void ex(Exception ex) {
        if (LOG_LEVEL > EXCEPTION) {
            StackTraceElement[] ste = ex.getCause().getStackTrace();
            for (int i = 0; 1 < ste.length; i++) {
                LogHelper.e(DEFAULT_TAG, "at " + ste[i].getClassName() + "." + ste[i].getMethodName()
                        + "(" + ste[i].getFileName() + ":" + ste[i].getLineNumber() + ")");
            }
            LogHelper.e(ex.toString());
        }
    }

    public static void e(String msg) {
        if (LOG_LEVEL > ERROR) {
            Log.e(DEFAULT_TAG, msg);
            logToFile("E", DEFAULT_TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_LEVEL > ERROR) {
            Log.e(tag, msg);
            logToFile("E", tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_LEVEL > WARN) {
            Log.w(tag, msg);
            logToFile("W", tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOG_LEVEL > INFO) {
            Log.i(tag, msg);
            logToFile("I", tag, msg);
        }
    }

    public static void i(String msg) {
        if (LOG_LEVEL > INFO) {
            Log.i("dlx--", msg);
            logToFile("I", "dlx--", msg);
        }
    }

    public static void dd(String msg) {
        if (LOG_LEVEL > INFO) {
            Log.d("dlx--", msg);
            logToFile("D", "dlx--", msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_LEVEL > DEBUG) {
            Log.d(tag, msg);
            logToFile("D", tag, msg);
        }
    }

    public static void d(String msg) {
        if (LOG_LEVEL > DEBUG) {
            Log.d(DEFAULT_TAG, msg);
            logToFile("D", DEFAULT_TAG, msg);
        }
    }

    public static void w(String msg) {
        if (LOG_LEVEL > WARN) {
            Log.w(DEFAULT_TAG, msg);
            logToFile("W", DEFAULT_TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (LOG_LEVEL > VERBOSE) {
            Log.v(tag, msg);
            logToFile("V", tag, msg);
        }
    }

    public static void t(String msg) {
        if (LOG_LEVEL > DEBUG)
            Log.d(DEFAULT_TAG, msg);
    }

    public static void t(String tag, String msg) {
        if (LOG_LEVEL > DEBUG)
            Log.d(tag, msg);
    }

    public static void st(String msg) {
        if (LOG_LEVEL > DEBUG)
            Log.d(DEFAULT_TAG, getClassName() + "->" + getMethodName() + "->" + msg);
    }

    /**
     * 初始化日记保存系统
     */
    public static void initLogFile(Context context) {
        if (LOG_LEVEL >= EXCEPTION) {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = null;
            try {
                info = manager.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e2) {
            }
            String model = Build.MODEL;
            if (!model.startsWith(Build.MANUFACTURER))
                model = Build.MANUFACTURER + " " + model;

            String fileDir = Environment.getExternalStorageDirectory() + "/dou_logs/";
            String fileName = TextUtil.getSimpleTime(System.currentTimeMillis()) + ".log";
            String url = fileDir + fileName;
            File dir = new File(fileDir);
            if (!dir.exists())
                dir.mkdirs();
            try {
                fos = new FileOutputStream(new File(url), true);
                String deviceVersion = "Android version: " + Build.VERSION.SDK_INT + "\n";
                String device = "Device: " + model + "\n";
                String appVersion = "App version: " + (info == null ? "(null)" : info.versionCode) + "\n";
                fos.write(deviceVersion.getBytes("UTF-8"));
                fos.write(device.getBytes("UTF-8"));
                fos.write(appVersion.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 另一种记录日志到文件的方法
     *
     * @param context
     */
    @Deprecated
    public static void startLogToFile(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
        }
        String model = Build.MODEL;
        if (!model.startsWith(Build.MANUFACTURER))
            model = Build.MANUFACTURER + " " + model;

        // Make file name - file must be saved to external storage or it wont be readable by
        // the email app.
        String path = Environment.getExternalStorageDirectory() + "/dou_logs/";
        String fileName = TextUtil.getSimpleTime(System.currentTimeMillis()) + "_logcat_.log";
        String fullName = path + fileName;

        // Extract to file.
        File file = new File(fullName);
        InputStreamReader reader = null;
        try {
            // For Android 4.0 and earlier, you will get all app's log output, so filter it to
            // mostly limit it to your app's output.  In later versions, the filtering isn't needed.
            String cmd = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) ?
                    "logcat -d -v time com.orieange.loveonline:v dalvikvm:v System.err:v *:s" :
                    "logcat -d -v time";

            // get input stream
            Process process = Runtime.getRuntime().exec(cmd);
            reader = new InputStreamReader(process.getInputStream());

            // write output stream
            if (writer == null)
                writer = new FileWriter(file);
            writer.write("Android version: " + Build.VERSION.SDK_INT + "\n");
            writer.write("Device: " + model + "\n");
            writer.write("App version: " + (info == null ? "(null)" : info.versionCode) + "\n");

            char[] buffer = new char[10000];
            do {
                int n = reader.read(buffer, 0, buffer.length);
                if (n == -1)
                    break;
                writer.write(buffer, 0, n);
            } while (true);
            reader.close();
            writer.close();
        } catch (IOException e) {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e1) {
                }
        }
    }

    /**
     * 保存日志到文件
     *
     * @param level
     * @param tag
     * @param msg
     */
    public static void logToFile(String level, String tag, String msg) {
        String log = TextUtil.getFormattedTime(System.currentTimeMillis()) + " " + level + "/" + tag + ":" + msg + "\r\n";
        try {
            byte[] data = log.getBytes("UTF-8");
            if (fos != null)
                fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLogToFile() {
        if (fos != null) {
            try {
                fos.close();
                fos = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean show = true;
    public static String TAG = "lvwei";

    public static void I() {
        if (!show) return;
        Log.i(TAG, getClassName() + "-" + getMethodName() + "-" + getLineNumber());
    }

    public static void I(String str) {
        if (!show) return;
        Log.i(TAG, getClassName() + "-" + getMethodName() + "-" + getLineNumber() + ":" + str);
    }

    public static void I(int i) {
        if (!show) return;
        Log.i(TAG, getClassName() + "-" + getMethodName() + "-" + getLineNumber() + ":" + String.valueOf(i));
    }
    public static void I(float i) {
        if (!show) return;
        Log.i(TAG, getClassName() + "-" + getMethodName() + "-" + getLineNumber() + ":" + String.valueOf(i));
    }
    public static void I(long i) {
        if (!show) return;
        Log.i(TAG, getClassName() + "-" + getMethodName() + "-" + getLineNumber() + ":" + String.valueOf(i));
    }

    public static void I(Boolean i) {
        if (!show) return;
        Log.i(TAG, getClassName() + "-" + getMethodName() + "-" + getLineNumber() + ":" + String.valueOf(i ? "true" : "false"));
    }

    public static void I(Map<String, String> map) {
        if (!show) return;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.i(TAG, getClassName() + "-" + getMethodName() + "-" + getLineNumber() + ": " + entry.getKey() + " = " + entry.getValue());
        }
    }

    public static void I(String str, String str2) {
        if (!show) return;
        Log.i(TAG, getClassName() + "-" + getMethodName() + "-" + getLineNumber() + "--" + str + ":" + str2);
    }

    public static void I(List<String> list) {
        if (!show) return;
        for (String str : list)
            Log.i(TAG, getClassName() + "-" + getMethodName() + "-" + getLineNumber() + "--" + str);
    }


    /**
     * 获取类名
     *
     * @return 类名
     */
    public static String getClassName() {
        String classname = new Throwable().getStackTrace()[2].getClassName();
        String[] str = classname.split("\\.");
        return str[str.length - 1];
    }

    /**
     * 获取方法名
     *
     * @return 方法名
     */
    public static String getMethodName() {
        return new Throwable().getStackTrace()[2].getMethodName();
    }

    /**
     * 获取日志打印的行号
     *
     * @return 调用行号
     */
    public static String getLineNumber() {
        return String.valueOf(new Throwable().getStackTrace()[2].getLineNumber());
    }


}
