package com.dailycation.base.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dailycation.base.R;

/**
 * Created by hoffer on 17/7/30.
 */

public class SimpleInputDialog extends DialogFragment {
    private String mTitle;
    private String mHint;
    private String mContent;
    private int mRequestCode;
    private OnInputActionListener mListener;

    public String getTitle() {
        return mTitle;
    }

    public String getHint() {
        return mHint;
    }

    public int getRequestCode() {
        return mRequestCode;
    }


    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public void setHint(String mHint) {
        this.mHint = mHint;
    }

    public void setRequestCode(int mRequestCode) {
        this.mRequestCode = mRequestCode;
    }

    public SimpleInputDialog() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof OnInputActionListener)
            mListener = (OnInputActionListener) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText editText = new EditText(getActivity());
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        builder.setView(editText);
        if(mTitle!=null)
            builder.setTitle(mTitle);
        if(mHint!=null)
            editText.setHint(mHint);
        if(mContent!=null)
            editText.setText(mContent);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mListener!=null)
                    mListener.onComplete(mRequestCode,editText.getEditableText().toString().trim());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mListener!=null)
                    mListener.onCancel();
            }
        });

        return builder.create();
    }

    public interface OnInputActionListener{

        /**
         * input finish
         * @param text
         * @param requestCode
         */
        void onComplete(int requestCode,String text);

        /**
         * cancel to input.
         */
        void onCancel();
    }
}
