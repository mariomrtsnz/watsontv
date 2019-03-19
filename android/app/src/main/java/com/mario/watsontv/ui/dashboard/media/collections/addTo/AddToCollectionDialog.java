package com.mario.watsontv.ui.dashboard.media.collections.addTo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.CollectionResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.CollectionService;
import com.mario.watsontv.util.UtilToken;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCollectionDialog extends DialogFragment implements AddToCollectionListener {
    private AlertDialog dialog;
    private CollectionService service;
    private Context ctx;
    private String jwt, id;
    AddToCollectionAdapter adapter;
    RecyclerView recycler;
    private AddToCollectionListener mListener;
    private List<CollectionResponse> items;
    private List<String> selectedCollections = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_collection_dialog, null);
        ctx = view.getContext();
        recycler = view.findViewById(R.id.add_collection_dialog_recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
        recycler.setLayoutManager(linearLayoutManager);
        id = getArguments().getString("mediaId");
        jwt = UtilToken.getToken(ctx);
        listMyCollections();
        builder.setView(view).setTitle("Collections").setNegativeButton("Cancel", (dialog, which) -> {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
        }).setPositiveButton("Add", (dialog, which) -> {
            addToCollectionsSubmit();
        });
        dialog = builder.create();
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
    }

    @Override
    public void addToCollections(String id) {
        selectedCollections.add(id);
        dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
    }

    @Override
    public void removeFromCollections(String id) {
        selectedCollections.remove(id);
        if (selectedCollections.size() == 0)
            dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
    }

    public void addToCollectionsSubmit() {
        service = ServiceGenerator.createService(CollectionService.class, jwt, AuthType.JWT);
        Call<CollectionResponse> call = service.addToCollections(selectedCollections, id);
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

    public void listMyCollections() {
        service = ServiceGenerator.createService(CollectionService.class, jwt, AuthType.JWT);
        Call<List<CollectionResponse>> call = service.getUserCollections(UtilToken.getId(ctx));
        call.enqueue(new Callback<List<CollectionResponse>>() {
            @Override
            public void onResponse(Call<List<CollectionResponse>> call, Response<List<CollectionResponse>> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println(response.body());
                    items = response.body();
                    adapter = new AddToCollectionAdapter(ctx, items, mListener);
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<CollectionResponse>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
