package com.mario.watsontv.ui.dashboard.media.collections.list;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mario.watsontv.R;
import com.mario.watsontv.responses.CollectionResponse;
import com.mario.watsontv.responses.ResponseContainer;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.CollectionService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.ui.dashboard.media.collections.create.CreateCollectionDialog;
import com.mario.watsontv.ui.dashboard.media.collections.detail.CollectionFragment;
import com.mario.watsontv.util.UtilToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectionListFragment extends Fragment implements CollectionListListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String ARG_COLUMN_COUNT = "column-count";
    String jwt;
    CollectionService service;
    UserService userService;
    List<CollectionResponse> items;
    CollectionListAdapter adapter;
    RecyclerView recycler;
    private String selectedGenre;
    private int mColumnCount = 1;
    ProgressDialog pgDialog;
    private CollectionListListener mListener;
    private Context ctx;
    SwipeRefreshLayout swipeLayout;
    private FloatingActionButton fab;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public CollectionListFragment() {}

    // TODO: Rename and change types and number of parameters
    public static CollectionListFragment newInstance(int columnCount) {
        CollectionListFragment fragment = new CollectionListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public void deleteOne(String id) {
        service = ServiceGenerator.createService(CollectionService.class, jwt, AuthType.JWT);
        Call<CollectionResponse> call = service.delete(id);
        call.enqueue(new Callback<CollectionResponse>() {
            @Override
            public void onResponse(Call<CollectionResponse> call, Response<CollectionResponse> response) {
                if (response.code() != 204) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    pgDialog.dismiss();
                    listMyCollections();
                    Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    adapter = new CollectionListAdapter(ctx, items, mListener);
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CollectionResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
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
                    pgDialog.dismiss();
                    items = response.body();
                    adapter = new CollectionListAdapter(ctx, items, mListener);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Objects.requireNonNull(getActivity()).setTitle("My Collections");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        jwt = UtilToken.getToken(getContext());
        mListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_collection_list, container, false);
        if (layout instanceof SwipeRefreshLayout) {
            ctx = layout.getContext();
            recycler = layout.findViewById(R.id.collection_list_recyclerview);
            if (mColumnCount <= 1) {
                recycler.setLayoutManager(new LinearLayoutManager(ctx));
            } else {
                recycler.setLayoutManager(new GridLayoutManager(ctx, mColumnCount));
            }
            items = new ArrayList<>();
            listMyCollections();
            pgDialog = new ProgressDialog(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            pgDialog.setIndeterminate(true);
            pgDialog.setCancelable(false);
            pgDialog.setTitle("Loading data");
            pgDialog.show();
            adapter = new CollectionListAdapter(ctx, items, mListener);
            recycler.setAdapter(adapter);
            swipeLayout = layout.findViewById(R.id.collection_list_swipeContainer);
            swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary), ContextCompat.getColor(getContext(), R.color.colorAccent));
            swipeLayout.setOnRefreshListener(() -> {
                listMyCollections();
                if (swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
            });
        }
        fab = layout.findViewById(R.id.collection_list_fab_create);
        fab.setOnClickListener(v -> {
            CreateCollectionDialog createCollectionDialog = new CreateCollectionDialog();
            createCollectionDialog.setTargetFragment(this, Activity.RESULT_OK);
            createCollectionDialog.show(getFragmentManager(), "create dialog");
        });
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            listMyCollections();
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void delete(String collectionId) {
        deleteOne(collectionId);
    }

    @Override
    public void goToDetails(CollectionResponse collection) {
        CollectionFragment collectionDetailFragment = new CollectionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedCollection", collection);
        collectionDetailFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.content_main_container, collectionDetailFragment).addToBackStack(null).commit();
    }
}
