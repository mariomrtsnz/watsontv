package com.mario.watsontv.ui.dashboard.media.collections.create;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mario.watsontv.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CreateCollectionDialog extends DialogFragment {
    private EditText name, description;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_create_collection_dialog, null);
        builder.setView(view).setTitle("Create Collection").setNegativeButton("Cancel", (dialog, which) -> {

        }).setPositiveButton("Save", (dialog, which) -> {

        });

        name = view.findViewById(R.id.create_collection_dialog_name);
        description = view.findViewById(R.id.create_collection_dialog_description);
        return builder.create();
    }
}
