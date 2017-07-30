package com.dailycation.base.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.dailycation.base.R;

/**
 * Created by hoffer on 17/7/30.
 */

public class SexChooseDialog extends DialogFragment {
    private OnSexChooseListener mListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof OnSexChooseListener)
            mListener = (OnSexChooseListener) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Gender");
        builder.setItems(new String[]{getActivity().getString(R.string.male), getActivity().getString(R.string.female)}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mListener!=null)
                    mListener.onChooseSex(which + 1);
            }
        });
        return super.onCreateDialog(savedInstanceState);
    }

    public interface OnSexChooseListener{
        /**
         * complete sex choose
         * @param gender 1-male 2-female
         */
        void onChooseSex(int gender);
    }
}
