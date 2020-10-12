package com.example.bashlan;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

public class Activity_dialog extends AppCompatDialogFragment {
    private static final String TAG = "nathan";
    private TextInputLayout dialog_EDT_name;
    private TextInputLayout dialog_EDT_email;
    private TextInputLayout dialog_EDT_password;
    private ActivityDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_dialog, null);

        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Subsribe", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = "";
                        String email = "";
                        String password = "";

                        name = dialog_EDT_name.getEditText().getText().toString();
                        email = dialog_EDT_email.getEditText().getText().toString();
                        password = dialog_EDT_password.getEditText().getText().toString();

                        listener.getInfoUser(name, email, password);
                    }
                });

        dialog_EDT_name = view.findViewById(R.id.dialog_EDT_name);
        dialog_EDT_email = view.findViewById(R.id.dialog_EDT_email);
        dialog_EDT_password = view.findViewById(R.id.dialog_EDT_password);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ActivityDialogListener) context;
        } catch (ClassCastException e) {
            Log.d(TAG, "onAttach: " + e.getMessage());
            throw new ClassCastException(context.toString() + "must implement ActivityDialogListener");
        }

    }

    public interface ActivityDialogListener {
        void getInfoUser(String name, String email, String password);
    }
}
