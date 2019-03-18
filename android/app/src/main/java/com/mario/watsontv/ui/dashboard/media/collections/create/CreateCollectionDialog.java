package com.mario.watsontv.ui.dashboard.media.collections.create;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.dto.CollectionDto;
import com.mario.watsontv.responses.CollectionResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.CollectionService;
import com.mario.watsontv.util.UtilToken;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCollectionDialog extends DialogFragment {
    private AlertDialog dialog;
    private EditText name, description;
    boolean nameFocus, descriptionFocus;
    private CollectionService service;
    private Context ctx;
    private String jwt;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ctx = getContext();
        jwt = UtilToken.getToken(ctx);
        View view = inflater.inflate(R.layout.fragment_create_collection_dialog, null);
        builder.setView(view).setTitle("Create Collection").setNegativeButton("Cancel", (dialog, which) -> {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
        }).setPositiveButton("Save", (dialog, which) -> {
            createCollection();
        });
        name = view.findViewById(R.id.create_collection_dialog_name);
        description = view.findViewById(R.id.create_collection_dialog_description);
        dialog = builder.create();
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (name.getText().length() > 0 && description.getText().length() > 0) {
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (name.getText().length() > 0 && description.getText().length() > 0) {
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });
        name.setOnFocusChangeListener((view, hasFocus) -> nameFocus = hasFocus);
        description.setOnFocusChangeListener((v, hasFocus) -> descriptionFocus = hasFocus);
    }

    public void createCollection() {
        service = ServiceGenerator.createService(CollectionService.class, jwt, AuthType.JWT);
        CollectionDto collectionDto = new CollectionDto(name.getText().toString(), description.getText().toString(), UtilToken.getId(ctx));
        Call<CollectionResponse> call = service.create(collectionDto);
        call.enqueue(new Callback<CollectionResponse>() {
            @Override
            public void onResponse(Call<CollectionResponse> call, Response<CollectionResponse> response) {
                if (response.code() != 201) {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                } else {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                }
            }

            @Override
            public void onFailure(Call<CollectionResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
            }
        });
    }

}
