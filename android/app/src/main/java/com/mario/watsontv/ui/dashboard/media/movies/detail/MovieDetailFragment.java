package com.mario.watsontv.ui.dashboard.media.movies.detail;

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
import com.mario.watsontv.util.UtilToken;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;


public class MovieDetailFragment extends Fragment implements MediaDetailsListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mediaId, jwt;
    private Context ctx;
    private TextView tvTitle, tvReleaseDate, tvRuntime, tvSynopsis, tvRatings, tvEmptyCast;
    private RatingBar ratingBar;
    private Button btnGenre;
    private ImageView ivCoverImage;
    private MediaDetailsResponse media;
    private ProgressDialog pgDialog;
    private RecyclerView recycler;
    private MediaDetailsAdapter adapter;
    private MediaDetailsListener mListener;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
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
        mListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        recycler = layout.findViewById(R.id.movie_detail_recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
        ivCoverImage = layout.findViewById(R.id.movie_detail_iv_coverImage);
        tvTitle = layout.findViewById(R.id.movie_detail_tv_title);
        tvReleaseDate = layout.findViewById(R.id.movie_detail_tv_releaseDate);
        tvRatings = layout.findViewById(R.id.movie_detail_tv_ratings);
        ratingBar = layout.findViewById(R.id.movie_detail_ratingBar);
        tvRuntime = layout.findViewById(R.id.movie_detail_tv_runtime);
        tvSynopsis = layout.findViewById(R.id.movie_detail_tv_synopsis);
        btnGenre = layout.findViewById(R.id.movie_detail_btn_genre);
        tvEmptyCast = layout.findViewById(R.id.movie_detail_tv_cast_empty);
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
        mListener = null;
    }

    private void setData() {
        if (media.getCast().size() == 0)
            tvEmptyCast.setVisibility(View.VISIBLE);
        else {
            adapter = new MediaDetailsAdapter(ctx, media.getCast(), mListener);
            recycler.setAdapter(adapter);
        }
        Glide.with(ctx).load(media.getCoverImage()).into(ivCoverImage);
        tvTitle.setText(media.getTitle());
        tvRuntime.setText(String.valueOf(media.getRuntime()) + "mins");
        tvSynopsis.setText(media.getSynopsis());
        tvRatings.setText(String.valueOf(media.getRating().length));
        ratingBar.setRating(media.getTotalRating());
        try {
            tvReleaseDate.setText(String.valueOf(media.getReleaseDate().get(Calendar.YEAR)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        btnGenre.setText(media.getGenre().getName());
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
    public void goToGenreMedia(String genreId) {

    }
}
