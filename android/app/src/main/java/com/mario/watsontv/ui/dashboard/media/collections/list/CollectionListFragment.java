package com.mario.watsontv.ui.dashboard.media.collections.list;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.CollectionResponse;
import com.mario.watsontv.retrofit.services.CollectionService;
import com.mario.watsontv.retrofit.services.UserService;

import java.util.List;

public class CollectionListFragment extends Fragment {
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public CollectionListFragment() {}

    // TODO: Rename and change types and number of parameters
    public static CollectionListFragment newInstance(String param1, String param2) {
        CollectionListFragment fragment = new CollectionListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void getLists() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
        if (context instanceof CollectionListListener) {
            mListener = (CollectionListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CollectionListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
