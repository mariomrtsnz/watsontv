package com.mario.watsontv.ui.dashboard.media.series.season;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

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
import android.widget.TextView;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.SeasonDetailsResponse;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.EpisodeService;
import com.mario.watsontv.retrofit.services.SeasonService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.ui.dashboard.media.collections.addTo.AddToCollectionDialog;
import com.mario.watsontv.util.UtilToken;

public class SeasonFragment extends Fragment implements SeasonListener {
    // TODO: Rename parameter arguments, choose names that match

    // TODO: Rename and change types of parameters
    private String seasonId, jwt;
    private SeasonListener mListener;
    private Context ctx;
    private RecyclerView recycler;
    private SeasonAdapter adapter;
    private ProgressDialog pgDialog;
    private SeasonDetailsResponse season;
    private TextView seriesTitle, seasonNumber, episodeSeasonNumber;

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
        seriesTitle = layout.findViewById(R.id.season_detail_tv_seriesname);
        seasonNumber = layout.findViewById(R.id.season_detail_tv_seasonNumber);
        ctx = layout.getContext();
        recycler = layout.findViewById(R.id.season_detail_episodeRecycler);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx, RecyclerView.VERTICAL, false);
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
        Call<SeasonDetailsResponse> call = service.getOne(seasonId);
        call.enqueue(new Callback<SeasonDetailsResponse>() {
            @Override
            public void onResponse(Call<SeasonDetailsResponse> call, Response<SeasonDetailsResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    pgDialog.dismiss();
                    season = response.body();
                    setData();
                }
            }

            @Override
            public void onFailure(Call<SeasonDetailsResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData() {
        seriesTitle.setText(season.getSeries().getTitle());
        seasonNumber.setText(String.valueOf(season.getNumber()));
        adapter = new SeasonAdapter(ctx, season.getEpisodes(), mListener, season.getNumber());
        recycler.setAdapter(adapter);
    }

    @Override
    public void updateWatched(String id) {
        EpisodeService service = ServiceGenerator.createService(EpisodeService.class, jwt, AuthType.JWT);
        Call<UserResponse> call = service.updateWatched(id);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    listEpisodes();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateWatchlisted(String id) {
        EpisodeService service = ServiceGenerator.createService(EpisodeService.class, jwt, AuthType.JWT);
        Call<UserResponse> call = service.updateWatchlisted(id);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    listEpisodes();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateCollected(String id) {

    }
}
