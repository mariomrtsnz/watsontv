package com.mario.watsontv.ui.dashboard.media.series.season;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.EpisodeResponse;
import com.mario.watsontv.responses.SeasonResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.SeasonService;
import com.mario.watsontv.util.UtilToken;

import java.util.ArrayList;
import java.util.List;

public class SeasonFragment extends Fragment implements SeasonListener {
    // TODO: Rename parameter arguments, choose names that match

    // TODO: Rename and change types of parameters
    private String seasonId, jwt;
    private SeasonListener mListener;
    private Context ctx;
    private RecyclerView recycler;
    private SeasonAdapter adapter;
    private ProgressDialog pgDialog;
    private SeasonResponse season;

    public SeasonFragment() {}

    // TODO: Rename and change types and number of parameters
    public static SeasonFragment newInstance(String param1, String param2) {
        SeasonFragment fragment = new SeasonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            seasonId = getArguments().getString("seasonId");
        }
        jwt = UtilToken.getToken(getContext());
        mListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_season, container, false);
        ctx = layout.getContext();
        recycler = layout.findViewById(R.id.season_detail_episodeRecycler);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx, RecyclerView.VERTICAL, true);
        recycler.setLayoutManager(linearLayoutManager);
        pgDialog = new ProgressDialog(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        pgDialog.setIndeterminate(true);
        pgDialog.setCancelable(false);
        pgDialog.setTitle("Loading data");
        pgDialog.show();
        listEpisodes();
        return layout;
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

    private void listEpisodes() {
        SeasonService service = ServiceGenerator.createService(SeasonService.class, jwt, AuthType.JWT);
        Call<SeasonResponse> call = service.getOne(seasonId);
        call.enqueue(new Callback<SeasonResponse>() {
            @Override
            public void onResponse(Call<SeasonResponse> call, Response<SeasonResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    pgDialog.dismiss();
                    season = response.body();
                    adapter = new SeasonAdapter(ctx, season.getEpisodes(), mListener);
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<SeasonResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
