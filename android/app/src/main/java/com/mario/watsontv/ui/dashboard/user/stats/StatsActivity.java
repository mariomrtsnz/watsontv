package com.mario.watsontv.ui.dashboard.user.stats;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.UserTimeStats;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.util.UtilToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsActivity extends AppCompatActivity {
    PieChartView pieChartView;
    Map<String, Float> userStats;
    UserTimeStats userTimeStats;
    private Context ctx;
    List<SliceValue> pieData = new ArrayList<>();
    int[] colorsArray = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.YELLOW};
    String jwt, selectedUserId;
    TextView atEpisodesNumber, atMoviesNumber, atEpisodesTime, atMoviesTime;
    private ImageView placeholderPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        jwt = UtilToken.getToken(ctx);
        if (getIntent().getStringExtra("selectedUserId") == null) selectedUserId = UtilToken.getId(ctx);
        else selectedUserId = getIntent().getStringExtra("selectedUserId");
        setContentView(R.layout.activity_stats);
        atEpisodesNumber = findViewById(R.id.profile_stats_series_at_number);
        atMoviesNumber = findViewById(R.id.profile_stats_movies_at_number);
        atEpisodesTime = findViewById(R.id.profile_stats_series_at_time);
        atMoviesTime = findViewById(R.id.profile_stats_movies_at_time);
        placeholderPieChart = findViewById(R.id.profile_stats_placeHolderPieChart);
        pieChartView = findViewById(R.id.chart);
        getUserStats();
        getUserTimeStats();
    }

    void getUserStats() {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<Map<String, Float>> call = service.getUserStats(selectedUserId);
        call.enqueue(new Callback<Map<String, Float>>() {
            @Override
            public void onResponse(Call<Map<String, Float>> call, Response<Map<String, Float>> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    int nullValues = 0;
                    userStats = response.body();
                    for (Map.Entry<String, Float> entry : userStats.entrySet()) {
                        if (entry.getValue() != null) {
                            Random random = new Random();
                            pieData.add(new SliceValue(entry.getValue(), colorsArray[random.nextInt(colorsArray.length)]).setLabel(entry.getKey() + " (" + String.valueOf(entry.getValue()) + "%)"));
                        } else nullValues +=1 ;
                    }
                    if (nullValues == 0) {
                        PieChartData pieChartData = new PieChartData(pieData);
                        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
                        pieChartView.setPieChartData(pieChartData);
                        placeholderPieChart.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Float>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(ctx, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String timeConvert(int time) {
        return time/24/60 + "d " + time/60%24 + "h " + time%60 + "m";
    }

    void getUserTimeStats() {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<UserTimeStats> call = service.getUserTimeStats(selectedUserId);
        call.enqueue(new Callback<UserTimeStats>() {
            @Override
            public void onResponse(Call<UserTimeStats> call, Response<UserTimeStats> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    userTimeStats = response.body();
                    atEpisodesNumber.setText(String.valueOf(userTimeStats.getEpisodes().getTotalNumber()) + " episodes");
                    atEpisodesTime.setText(timeConvert(userTimeStats.getEpisodes().getTotalTime()));
                    atMoviesNumber.setText(String.valueOf(userTimeStats.getMovies().getTotalNumber()) + " movies");
                    atMoviesTime.setText(timeConvert(userTimeStats.getMovies().getTotalTime()));
                }
            }

            @Override
            public void onFailure(Call<UserTimeStats> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(ctx, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
