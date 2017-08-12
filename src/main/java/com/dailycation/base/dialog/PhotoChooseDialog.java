package com.dailycation.base.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.dailycation.base.BaseApplication;
import com.dailycation.base.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Show a dialog with two option to choose a photo,pick one or snap one.
 * Remember the attached activity to implement {@link OnPhotoSelectedListener}.
 * @author hehu
 * @version 1.1 2016/1/29
 */
public class PhotoChooseDialog extends DialogFragment {
    public static final int REQUEST_CODE = 1000;
    private final static int REQUEST_PICK_PHOTO = 1001;
    private final static int REQUEST_TAKE_PHOTO = 1002;
    private static final int REQUEST_CUT_PHOTO = 1003;
    private final static int OUTPUT_SIZE_WIDTH = 100;
    private final static int OUTPUT_SIZE_HEIGHT = 100;
    public static final String EXTRA_BITMAP = "extra_bitmap";
    private final static String TAG = PhotoChooseDialog.class.getSimpleName();
    private static final int PIC_PERMISSION_REQUEST = 302;
    private OnPhotoSelectedListener mListener;
    public String mCurrentPhotoPath;
    private boolean askedPermission = false;
    private int TAKE_PERMISSION_REQUEST = 301;
    private BaseApplication application = BaseApplication.getInstance();
    private String mFileDir = "temp";

    /**
     * is need mNeedCrop after choose or pick.
     */
    private boolean mNeedCrop = true;

    public void setNeedCrop(boolean mNeedCrop) {
        this.mNeedCrop = mNeedCrop;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnPhotoSelectedListener){
            mListener = (OnPhotoSelectedListener)activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        initDialog();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
            mCurrentPhotoPath = savedInstanceState.getString("path");
    }

    public void setPhotoSelectedListener(OnPhotoSelectedListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        setTargetFragment(null,0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //default constructor
    public PhotoChooseDialog(){}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("path",mCurrentPhotoPath);
    }

    public void initDialog(){
        Button btnGallery,btnCam,btnCancel;
        final Dialog dialog;
        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View viewDialog = inflator.inflate(R.layout.dialog_choose_photo, null);
        btnGallery = (Button)viewDialog.findViewById(R.id.btn_gallery);
        btnCam = (Button)viewDialog.findViewById(R.id.btn_cam);
        btnCancel = (Button)viewDialog.findViewById(R.id.cancel);
        getDialog().setContentView(viewDialog);
        btnGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dispatchPickPictureIntent();
            }});
        btnCam.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                takePhotoWithPermission();
            }});
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }});
    }

    public static PhotoChooseDialog newInstance(){
        PhotoChooseDialog fragment = new PhotoChooseDialog();
        return fragment;
    }

    private void dispatchPickPictureIntent() {
        if( Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PIC_PERMISSION_REQUEST);
                return;
            }
        }

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if(photoPickerIntent.resolveActivity(getActivity().getPackageManager())!=null)
            startActivityForResult(photoPickerIntent, REQUEST_PICK_PHOTO);
    }

    public void takePhotoWithPermission(){
        if( Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        TAKE_PERMISSION_REQUEST);
                return;
            } else
                dispatchTakePictureIntent();
        }else
            dispatchTakePictureIntent();
    }

    /**
     * check whether permission and camera is ready.
     * @return
     */
    public boolean isReady(){
        if(PermissionChecker.checkCallingOrSelfPermission(getActivity(),Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED){
            String title = getString(R.string.no_camera_permission);
            String content = getString(R.string.camera_permission_tip);
            showPermissionDialog(title,content);
            return false;
        }
        if(!checkCamera()){
            showPermissionDialog(getString(R.string.can_not_open_camera),getString(R.string.open_camera_tip));
            return false;
        }
        return true;
    }

    public void showPermissionDialog(String title,String content){
        Toast.makeText(application,content,Toast.LENGTH_SHORT).show();
    }

    /**
     * 检查相机是否正常
     */
    public boolean checkCamera(){
        Camera mCamera = getCameraInstance(0);
        Camera.CameraInfo cameraInfo = null;
        if(mCamera!=null) {
            cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(0, cameraInfo);
        }
        if (mCamera != null && cameraInfo != null) {
            // Camera is not available, display error message
            mCamera.release();
            return true;
        }else return false;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception ex) {
                // SocketError occurred while creating the File
                Log.e(TAG,"dispatchTakePictureIntent error:" + ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                try {
                    //某些华为机型的权限管理工具会禁止启动这个intent，但不会抛出异常
                    PhotoChooseDialog.this.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }catch (Exception e){
                    //e.printStackTrace();
                }
            }
        }
    }

    /**
     * 在app的目录内创建图片文件
     * @return
     */
    private File createImageFile() {
        // Create an image file name
        File storageDir = new File(Environment.getExternalStorageDirectory().toString() + "/" + mFileDir);
        if(!storageDir.exists())
            storageDir.mkdirs();
        //创建.nomedia文件防止，被Gallery扫描到
        createNoMediaFile(storageDir);
        File image = new File(storageDir + "/avatar.tmp");
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * prevent the image to be scaned by Gallery
     * @param directory
     */
    public void createNoMediaFile(File directory){
        try {
            File noMediaFile = new File(directory,".nomedia");
            noMediaFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除最新拍的图片
     */
    private void deleteLastPhotoTaken() {
        String[] projection = new String[] {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE };
        final Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                null,null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            int column_index_data =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String image_path = cursor.getString(column_index_data);
            File file = new File(image_path);
            if (file.exists()) {
                if(file.delete())
                    scanMediaFile(file.getAbsolutePath());
            }
        }
    }

    /**
     * 重新触发媒体扫描，刷新Gallery数据库
     * @param mediaDir
     */
    private void scanMediaFile(String mediaDir) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mediaDir);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public String createImageName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return timeStamp;
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("mNeedCrop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", OUTPUT_SIZE_WIDTH);
        intent.putExtra("outputY", OUTPUT_SIZE_HEIGHT);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        if(intent.resolveActivity(getActivity().getPackageManager())!=null)
            startActivityForResult(intent, REQUEST_CUT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_TAKE_PHOTO:
                if(resultCode == Activity.RESULT_OK) {
                    File myFile = new File(mCurrentPhotoPath);
                    if(mCurrentPhotoPath!=null) {
                        if(mNeedCrop)
                            crop(Uri.parse(mCurrentPhotoPath));
                        else
                            onResultPath(mCurrentPhotoPath);
                    }
                    else
                        Log.e(TAG,"mCurrentPhotoPath is null");
                }
                break;
            case REQUEST_PICK_PHOTO:
                if(resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getData();
                        if(mNeedCrop)
                            crop(uri);
                        else
                            onResultPath(getPath(uri));
                    }
                }
                break;
            case REQUEST_CUT_PHOTO:
                if(resultCode == Activity.RESULT_OK) {
                    Bitmap bitmap;
                    try {
                        bitmap = data.getParcelableExtra("data");
                        onResult(bitmap);
                        getDialog().cancel();

                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
                break;
        }
    }

    /**
     * get path from content uri,like content://media/external/images/media/15
     * @param contentUri
     * @return
     */
    public String getPath(Uri contentUri){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(
                contentUri, filePathColumn, null, null, null);
        if(cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }

    public interface OnPhotoSelectedListener{
        /**
         * the bitmap user choose
         * @param bitmap
         */
        public void onGetBitmap(Bitmap bitmap);

        /**
         * send the image uri
         * @param uri
         */
        void onGetImageUri(Uri uri);

        /**
         * send image path
         * @param path
         */
        void onGetImagePath(String path);
    }

    /**
     * send uri result to target activity
     * @param uri
     */
    public void onResultUri(Uri uri){
        if(mListener!=null)
            mListener.onGetImageUri(uri);
        getDialog().cancel();
    }

    /**
     * send uri result to target activity
     */
    public void onResultPath(String path){
        if(mListener!=null)
            mListener.onGetImagePath(path);
        getDialog().cancel();
    }

    /**
     * send bitmap result to target.
     * @param bitmap
     */
    public void onResult(Bitmap bitmap){
        if (mListener != null)
            mListener.onGetBitmap(bitmap);
        if(getTargetFragment()!=null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_BITMAP, bitmap);
            getTargetFragment().onActivityResult(REQUEST_CODE, Activity.RESULT_OK, intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == TAKE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                dispatchTakePictureIntent();
            } else {
                // TODO: display better error message.
            }
        }else if(requestCode == PIC_PERMISSION_REQUEST){
            dispatchPickPictureIntent();
        }
    }

    public Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            if(cameraId == -1)
                c = Camera.open();
            else
                c = Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
