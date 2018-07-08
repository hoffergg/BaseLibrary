package com.dailycation.base.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.dailycation.base.R;

/**
 * Created by hoffer on 17/7/30.
 */

public class AgeChooseDialog extends DialogFragment {
    private OnAgeChooseListener mListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof OnAgeChooseListener)
            mListener = (OnAgeChooseListener) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Your age");
        builder.setItems(R.array.age, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mListener!=null)
                    mListener.onChooseAge(which + 17);
            }
        });
        return builder.create();
    }

    public interface OnAgeChooseListener{
        void onChooseAge(int age);
    }
}
