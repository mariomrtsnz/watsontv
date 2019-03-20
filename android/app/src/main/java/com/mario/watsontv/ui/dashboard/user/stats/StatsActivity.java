package com.mario.watsontv.ui.dashboard.user.stats;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mario.watsontv.R;
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
    private Context ctx;
    List<SliceValue> pieData = new ArrayList<>();
    int[] colorsArray = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.YELLOW};
    String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        jwt = UtilToken.getToken(ctx);
        setContentView(R.layout.activity_stats);
        pieChartView = findViewById(R.id.chart);
        getUserStats();
    }

    void getUserStats() {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<Map<String, Float>> call = service.getUserStats(UtilToken.getId(ctx));
        call.enqueue(new Callback<Map<String, Float>>() {
            @Override
            public void onResponse(Call<Map<String, Float>> call, Response<Map<String, Float>> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    userStats = response.body();
                    for (Map.Entry<String, Float> entry : userStats.entrySet()) {
                        Random random = new Random();
                        pieData.add(new SliceValue(entry.getValue(), colorsArray[random.nextInt(colorsArray.length)]).setLabel(entry.getKey() + " (" + String.valueOf(entry.getValue()) + "%)"));
                    }
                    PieChartData pieChartData = new PieChartData(pieData);
                    pieChartData.setHasLabels(true).setValueLabelTextSize(14);
                    pieChartView.setPieChartData(pieChartData);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Float>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(ctx, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
