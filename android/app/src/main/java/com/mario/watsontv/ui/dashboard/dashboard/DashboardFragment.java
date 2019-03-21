package com.mario.watsontv.ui.dashboard.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.mario.watsontv.responses.UserTimeStats;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.util.UtilToken;

import java.util.Objects;

public class DashboardFragment extends Fragment implements DashboardListener {

    // TODO: Rename and change types of parameters
    private String jwt;
    private DashboardListener mListener;
    private Context ctx;
    private TextView tvUsername, tvEpisodesTime, tvMoviesTime;
    private UserTimeStats userTimeStats;

    public DashboardFragment() {}

    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        Objects.requireNonNull(getActivity()).setTitle("WatsonTV");
        mListener = this;
        jwt = UtilToken.getToken(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_dashboard, container, false);
        tvUsername = layout.findViewById(R.id.dashboard_tv_username);
        tvEpisodesTime = layout.findViewById(R.id.dashboard_tv_stats_series_time);
        tvMoviesTime = layout.findViewById(R.id.dashboard_tv_stats_movies_time);
        tvUsername.setText(UtilToken.getName(ctx));
        getUserData();
        return layout;
    }

    private void getUserData() {
        getUserTimeStats();
        getUserDashboardMedia();
    }

    private void getUserTimeStats() {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<UserTimeStats> call = service.getUserTimeStats(UtilToken.getId(ctx));
        call.enqueue(new Callback<UserTimeStats>() {
            @Override
            public void onResponse(Call<UserTimeStats> call, Response<UserTimeStats> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    userTimeStats = response.body();
                    setTimeStats();
                }
            }

            @Override
            public void onFailure(Call<UserTimeStats> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(ctx, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserDashboardMedia() {
//        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
//        Call<UserTimeStats> call = service.getUserDashboardMedia(UtilToken.getId(ctx));
//        call.enqueue(new Callback<UserTimeStats>() {
//            @Override
//            public void onResponse(Call<UserTimeStats> call, Response<UserTimeStats> response) {
//                if (response.code() != 200) {
//                    Toast.makeText(ctx, "Request Error", Toast.LENGTH_SHORT).show();
//                } else {
//                    userTimeStats = response.body();
//                    setDashboardMedia();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserTimeStats> call, Throwable t) {
//                Log.e("Network Failure", t.getMessage());
//                Toast.makeText(ctx, "Network Error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void setTimeStats() {
        tvEpisodesTime.setText(timeConvert(userTimeStats.getEpisodes().getTotalTime()));
        tvMoviesTime.setText(timeConvert(userTimeStats.getMovies().getTotalTime()));
    }

    private String timeConvert(int time) {
        return time/24/60 + "d " + time/60%24 + "h " + time%60 + "m";
    }

    private void setDashboardMedia() {

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
