package com.mario.watsontv.ui.dashboard.media.series.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mario.watsontv.R;
import com.mario.watsontv.responses.MediaDetailsResponse;
import com.mario.watsontv.responses.MediaResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.MediaService;
import com.mario.watsontv.ui.dashboard.media.MediaDetailsAdapter;
import com.mario.watsontv.ui.dashboard.media.MediaDetailsListener;
import com.mario.watsontv.ui.dashboard.media.collections.list.CollectionListFragment;
import com.mario.watsontv.ui.dashboard.media.series.list.SeriesListFragment;
import com.mario.watsontv.ui.dashboard.media.series.season.SeasonFragment;
import com.mario.watsontv.util.UtilToken;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeriesDetailFragment extends Fragment implements SeriesDetailListener, MediaDetailsListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mediaId, jwt;
    private Context ctx;
    private TextView tvTitle, tvReleaseDate, tvBroadcaster, tvSynopsis, tvRatings, tvEmptyCast, tvEmptySeasons, tvAirsDayOfWeek;
    private RatingBar ratingBar;
    private Button btnGenre;
    private ImageView ivCoverImage;
    private MediaDetailsResponse media;
    private ProgressDialog pgDialog;
    MediaDetailsAdapter castAdapter;
    SeriesDetailAdapter seasonsAdapter;
    RecyclerView castRecycler;
    RecyclerView seasonsRecycler;
    private String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private SeriesDetailListener seriesDetailListener;
    private MediaDetailsListener mediaDetailsListener;

    public SeriesDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SeriesDetailFragment newInstance(String param1, String param2) {
        SeriesDetailFragment fragment = new SeriesDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mediaId = getArguments().getString("mediaId");
        }
        jwt = UtilToken.getToken(getContext());
        seriesDetailListener = this;
        mediaDetailsListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_series_detail, container, false);
        castRecycler = layout.findViewById(R.id.series_detail_castRecycler);
        castRecycler.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
        seasonsRecycler = layout.findViewById(R.id.series_detail_seasonsRecycler);
        seasonsRecycler.setLayoutManager(new GridLayoutManager(ctx, 4));
        ivCoverImage = layout.findViewById(R.id.series_detail_iv_coverImage);
        tvTitle = layout.findViewById(R.id.series_detail_tv_title);
        tvReleaseDate = layout.findViewById(R.id.series_detail_tv_releaseDate);
        tvRatings = layout.findViewById(R.id.series_detail_tv_ratings);
        ratingBar = layout.findViewById(R.id.series_detail_ratingBar);
        tvBroadcaster = layout.findViewById(R.id.series_detail_tv_broadcaster);
        tvSynopsis = layout.findViewById(R.id.series_detail_tv_synopsis);
        tvAirsDayOfWeek = layout.findViewById(R.id.series_detail_tv_airsDayOfWeek);
        btnGenre = layout.findViewById(R.id.series_detail_btn_genre);
        tvEmptyCast = layout.findViewById(R.id.series_detail_tv_cast_empty);
        tvEmptySeasons = layout.findViewById(R.id.series_detail_tv_seasons_empty);
        getMediaDetails();
        pgDialog = new ProgressDialog(ctx, R.style.MaterialAlertDialog_MaterialComponents_Title_Icon_CenterStacked);
        pgDialog.setIndeterminate(true);
        pgDialog.setCancelable(false);
        pgDialog.setTitle("Loading data");
        pgDialog.show();
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
        seriesDetailListener = null;
        mediaDetailsListener = null;
    }

    private void setData() {
        if (media.getCast().size() == 0)
            tvEmptyCast.setVisibility(View.VISIBLE);
        else {
            castAdapter = new MediaDetailsAdapter(ctx, media.getCast(), mediaDetailsListener);
            castRecycler.setAdapter(castAdapter);
        }
        if (media.getSeasons().size() == 0)
            tvEmptySeasons.setVisibility(View.VISIBLE);
        else {
            seasonsAdapter = new SeriesDetailAdapter(ctx, media.getSeasons(), seriesDetailListener);
            seasonsRecycler.setAdapter(seasonsAdapter);
        }
        Glide.with(ctx).load(media.getCoverImage()).into(ivCoverImage);
        tvTitle.setText(media.getTitle());
        tvBroadcaster.setText(String.valueOf(media.getBroadcaster()));
        tvAirsDayOfWeek.setText(daysOfWeek[media.getAirsDayOfWeek()-1]);
        tvSynopsis.setText(media.getSynopsis());
        tvRatings.setText(String.valueOf(media.getRating().length));
        ratingBar.setRating(media.getTotalRating());
        try {
            if (media.getReleaseDate() != null)
                tvReleaseDate.setText(String.valueOf(media.getReleaseDate().get(Calendar.YEAR)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        btnGenre.setText(media.getGenre().getName());
        btnGenre.setOnClickListener(v -> mediaDetailsListener.goToGenreMedia(media.getId()));
        pgDialog.dismiss();
    }

    private void getMediaDetails() {
        MediaService service = ServiceGenerator.createService(MediaService.class, jwt, AuthType.JWT);
        Call<MediaDetailsResponse> call = service.getOneMedia(mediaId);
        call.enqueue(new Callback<MediaDetailsResponse>() {
            @Override
            public void onResponse(Call<MediaDetailsResponse> call, Response<MediaDetailsResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    media = response.body();
                    setData();
                }
            }

            @Override
            public void onFailure(Call<MediaDetailsResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void goToSeasonDetail(String id) {
        SeasonFragment seasonDetailFragment = new SeasonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("seasonId", id);
        seasonDetailFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.content_main_container, seasonDetailFragment).addToBackStack(null).commit();
    }

    @Override
    public void goToGenreMedia(String genreId) {
        SeriesListFragment seriesListFragment = new SeriesListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selectedGenreId", genreId);
        seriesListFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.content_main_container, seriesListFragment).addToBackStack(null).commit();
    }
}
