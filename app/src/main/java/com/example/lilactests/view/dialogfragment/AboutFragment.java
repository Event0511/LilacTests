package com.example.lilactests.view.dialogfragment;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

import com.example.lilactests.R;

/**
 * A Fragment is show me.
 */
public class AboutFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        return new AlertDialog.Builder(getActivity()).setView(R.layout.dialog_about).create();
    }
}
