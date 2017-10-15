package com.dailycation.base.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.dailycation.base.R;

/**
 * Created by hoffer on 17/10/14.
 */

public class SimpleAlertDialog extends DialogFragment {

    private String mTitle;
    private String mMsg;
    private OnAlertActionListener mListener;

    public static SimpleAlertDialog  getInstance(String title, String msg){
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("msg",msg);
        SimpleAlertDialog fragment = new SimpleAlertDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args!=null){
            mTitle = args.getString("title");
            mMsg = args.getString("msg");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(mTitle!=null)
            builder.setTitle(mTitle);
        if(mMsg!=null)
            builder.setMessage(mMsg);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(mListener!=null)
                    mListener.onNegative();
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(mListener!=null)
                    mListener.onPositive();
            }
        });
        dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(mListener!=null)
                    mListener.onDismiss();
            }
        });
        return builder.create();
    }

    public void setListener(OnAlertActionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnAlertActionListener{
        void onPositive();
        void onNegative();

        void onDismiss();
    }
}
