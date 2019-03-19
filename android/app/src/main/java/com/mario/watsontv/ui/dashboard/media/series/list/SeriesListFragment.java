package com.mario.watsontv.ui.dashboard.media.series.list;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.GenreResponse;
import com.mario.watsontv.responses.MediaResponse;
import com.mario.watsontv.responses.ResponseContainer;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.GenreService;
import com.mario.watsontv.retrofit.services.MediaService;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.ui.dashboard.media.series.MediaListListener;
import com.mario.watsontv.util.UtilToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesListFragment extends Fragment implements AdapterView.OnItemSelectedListener, MediaListListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    String jwt;
    MediaService service;
    UserService userService;
    List<MediaResponse> items;
    SeriesListAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    RecyclerView recycler;
    Spinner spinner;
    private String selectedGenre;
    private List<GenreResponse> genres = new ArrayList<>();
    private Context ctx;
    private int mColumnCount = 3;
    ProgressDialog pgDialog;
    boolean isScrolling = false;
    int currentPage = 1;
    int maxPage;
    int maxItemsInPage = 30;
    int totalItems;
    private MediaListListener mListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public SeriesListFragment() {}

    // TODO: Rename and change types and number of parameters
    public static SeriesListFragment newInstance(int columnCount) {
        SeriesListFragment fragment = new SeriesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Objects.requireNonNull(getActivity()).setTitle("Series");
        inflater.inflate(R.menu.fragment_media_menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) item.getActionView();
        spinner.setOnItemSelectedListener(this);
        getGenres();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        GenreResponse selectedGenreResponse = (GenreResponse) parent.getSelectedItem();
        selectedGenre = selectedGenreResponse.getId();
        currentPage = 1;
        listSeries(currentPage);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.media_search:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void listSeries(int page) {
        MediaService service = ServiceGenerator.createService(MediaService.class, jwt, AuthType.JWT);
        Call<ResponseContainer<MediaResponse>> call = service.getAllSeries(selectedGenre, page);
        call.enqueue(new Callback<ResponseContainer<MediaResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<MediaResponse>> call, Response<ResponseContainer<MediaResponse>> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    totalItems = (int) response.body().getCount();
                    maxPage = totalItems/maxItemsInPage;
                    pgDialog.dismiss();
                    if (page == 1) {
                        items.clear();
                        items = response.body().getRows();
                    } else
                        items.addAll(response.body().getRows());
                    adapter = new SeriesListAdapter(ctx, items, mListener);
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<MediaResponse>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        jwt = UtilToken.getToken(getContext());
        mListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_media_list, container, false);

        if (layout instanceof SwipeRefreshLayout) {
            ctx = layout.getContext();
            recycler = layout.findViewById(R.id.media_recyclerview);
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(ctx, mColumnCount);
            recycler.setLayoutManager(gridLayoutManager);
            items = new ArrayList<>();
            pgDialog = new ProgressDialog(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            pgDialog.setIndeterminate(true);
            pgDialog.setCancelable(false);
            pgDialog.setTitle("Loading data");
            pgDialog.show();
            adapter = new SeriesListAdapter(ctx, items, mListener);
            recycler.setAdapter(adapter);
            recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                        isScrolling = true;
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (isScrolling && currentPage <= maxPage && gridLayoutManager.findFirstVisibleItemPosition() + items.size() >= totalItems) {
                        currentPage++;
                        isScrolling = false;
                        listSeries(currentPage);
                    }
                }
            });
            swipeLayout = layout.findViewById(R.id.swipeContainer);
            swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary), ContextCompat.getColor(getContext(), R.color.colorAccent));
            swipeLayout.setOnRefreshListener(() -> {
                currentPage = 1;
                listSeries(currentPage);
                if (swipeLayout.isRefreshing()) {
                    isScrolling = false;
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
                    currentPage = 1;
                    listSeries(currentPage);
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
                    currentPage = 1;
                    listSeries(currentPage);
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
