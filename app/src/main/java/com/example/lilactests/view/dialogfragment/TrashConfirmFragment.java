package com.example.lilactests.view.dialogfragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.lilactests.utils.RxBus;
import com.example.lilactests.view.dialogfragment.event.TrashEvent;

/**
 * DialogFragment to confirm whether trash.
 */
public class TrashConfirmFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog
                .Builder(getActivity())
                .setTitle("Give up?")
                .setPositiveButton("yes", this)
                .setNegativeButton("no", null);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        RxBus.getDefault().post(new TrashEvent());
        this.dismiss();
    }
}
