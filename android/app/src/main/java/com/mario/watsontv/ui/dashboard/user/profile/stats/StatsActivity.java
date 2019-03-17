package com.mario.watsontv.ui.dashboard.user.profile.stats;

import androidx.appcompat.app.AppCompatActivity;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import android.graphics.Color;
import android.os.Bundle;

import com.mario.watsontv.R;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        pieChartView = findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, Color.BLUE).setLabel("Comedy (12.5%)"));
        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Drama (11.8%)"));
        pieData.add(new SliceValue(11, Color.RED).setLabel("Action (11.4%)"));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Family"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartView.setPieChartData(pieChartData);
    }
}
