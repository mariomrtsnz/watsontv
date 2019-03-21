package com.mario.watsontv.ui.dashboard.media.collections.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.CollectionResponse;
import com.mario.watsontv.responses.GenreResponse;
import com.mario.watsontv.responses.MediaResponse;
import com.mario.watsontv.responses.ResponseContainer;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.CollectionService;
import com.mario.watsontv.retrofit.services.GenreService;
import com.mario.watsontv.retrofit.services.MediaService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.ui.dashboard.media.MediaListAdapter;
import com.mario.watsontv.ui.dashboard.media.MediaListListener;
import com.mario.watsontv.ui.dashboard.media.collections.addTo.AddToCollectionDialog;
import com.mario.watsontv.ui.dashboard.media.movies.detail.MovieDetailFragment;
import com.mario.watsontv.ui.dashboard.media.series.detail.SeriesDetailFragment;
import com.mario.watsontv.util.UtilToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionFragment extends Fragment implements AdapterView.OnItemSelectedListener, MediaListListener{

    private MediaListListener mListener;
    private Context ctx;
    private String jwt, selectedGenre;
    private static final String ARG_COLUMN_COUNT = "column-count";
    MediaService service;
    UserService userService;
    List<MediaResponse> items;
    MediaListAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    RecyclerView recycler;
    Spinner spinner;
    CollectionResponse selectedCollection;
    private List<GenreResponse> genres = new ArrayList<>();
    private int mColumnCount = 3;
    private boolean isWatchlist;
    ProgressDialog pgDialog;
    TextView tvDescription, tvEmptyList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!isWatchlist) Objects.requireNonNull(getActivity()).setTitle("Collection - " + selectedCollection.getName());
        else Objects.requireNonNull(getActivity()).setTitle("Collection - Watchlist");
        inflater.inflate(R.menu.fragment_media_menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) item.getActionView();
        spinner.setOnItemSelectedListener(this);
        getGenres();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public CollectionFragment() {}

    // TODO: Rename and change types and number of parameters
    public static CollectionFragment newInstance(int columnCount) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedCollection = (CollectionResponse) getArguments().getSerializable("selectedCollection");
            isWatchlist = getArguments().getBoolean("isWatchlist");
        }
        mListener = this;
        jwt = UtilToken.getToken(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_collection, container, false);
        if (layout instanceof SwipeRefreshLayout) {
            ctx = layout.getContext();
            recycler = layout.findViewById(R.id.collection_details_recyclerview);
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(ctx, mColumnCount);
            recycler.setLayoutManager(gridLayoutManager);
            items = new ArrayList<>();
            if (isWatchlist) listUserWatchlist(); else listCollectionMedia();
            pgDialog = new ProgressDialog(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            pgDialog.setIndeterminate(true);
            pgDialog.setCancelable(false);
            pgDialog.setTitle("Loading data");
            pgDialog.show();
            tvDescription = layout.findViewById(R.id.collection_details_description);
            tvEmptyList = layout.findViewById(R.id.collection_details_tv_empty_list);
            if (!isWatchlist) tvDescription.setText(selectedCollection.getDescription());
            else tvDescription.setVisibility(View.GONE);
            adapter = new MediaListAdapter(ctx, items, mListener);
            recycler.setAdapter(adapter);
            swipeLayout = layout.findViewById(R.id.swipeContainer);
            swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary), ContextCompat.getColor(getContext(), R.color.colorAccent));
            swipeLayout.setOnRefreshListener(() -> {
                if (isWatchlist) listUserWatchlist(); else listCollectionMedia();
                if (swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
            });
        }
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

    private void getGenres() {
        GenreService service = ServiceGenerator.createService(GenreService.class, jwt, AuthType.JWT);
        Call<ResponseContainer<GenreResponse>> call = service.getAllGenres("name");
        call.enqueue(new Callback<ResponseContainer<GenreResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<GenreResponse>> call, Response<ResponseContainer<GenreResponse>> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    genres = response.body().getRows();
                    GenreResponse defaultGenre = new GenreResponse();
                    defaultGenre.setName("All Genres");
                    genres.add(0, defaultGenre);
                    ArrayAdapter<GenreResponse> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, genres);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<GenreResponse>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listCollectionMedia() {
        CollectionService service = ServiceGenerator.createService(CollectionService.class, jwt, AuthType.JWT);
        Call<List<MediaResponse>> call = service.getCollectionMedia(selectedCollection.getId());
        call.enqueue(new Callback<List<MediaResponse>>() {
            @Override
            public void onResponse(Call<List<MediaResponse>> call, Response<List<MediaResponse>> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    pgDialog.dismiss();
                    items = response.body();
                    adapter = new MediaListAdapter(ctx, items, mListener);
                    recycler.setAdapter(adapter);
                    if (selectedCollection.getCollected().size() <= 0 || items.size() <= 0) tvEmptyList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<MediaResponse>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listUserWatchlist() {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<List<MediaResponse>> call = service.getUserWatchlist();
        call.enqueue(new Callback<List<MediaResponse>>() {
            @Override
            public void onResponse(Call<List<MediaResponse>> call, Response<List<MediaResponse>> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    pgDialog.dismiss();
                    items = response.body();
                    adapter = new MediaListAdapter(ctx, items, mListener);
                    recycler.setAdapter(adapter);
                    if (items.size() <= 0) tvEmptyList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<MediaResponse>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void updateWatched(String id) {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<UserResponse> call = service.updateWatched(id);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    listCollectionMedia();
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
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<UserResponse> call = service.updateWatchlisted(id);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    listCollectionMedia();
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
        AddToCollectionDialog addToCollectionDialog = new AddToCollectionDialog();
        Bundle bundle = new Bundle();
        bundle.putString("mediaId", id);
        addToCollectionDialog.setArguments(bundle);
        addToCollectionDialog.setTargetFragment(this, Activity.RESULT_OK);
        addToCollectionDialog.show(getFragmentManager(), "create dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listCollectionMedia();
    }

    @Override
    public void goToDetail(String id, String mediaType) {
        if (mediaType.toLowerCase().equals("movie")) {
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("mediaId", id);
            movieDetailFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.content_main_container, movieDetailFragment).addToBackStack(null).commit();
        } else {
            SeriesDetailFragment movieDetailFragment = new SeriesDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("mediaId", id);
            movieDetailFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.content_main_container, movieDetailFragment).addToBackStack(null).commit();
        }
    }
}
