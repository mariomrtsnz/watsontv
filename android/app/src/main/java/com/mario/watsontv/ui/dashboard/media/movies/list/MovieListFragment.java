package com.mario.watsontv.ui.dashboard.media.movies.list;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.GenreResponse;
import com.mario.watsontv.responses.MediaResponse;
import com.mario.watsontv.responses.ResponseContainer;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.GenreService;
import com.mario.watsontv.retrofit.services.MediaService;
import com.mario.watsontv.retrofit.services.UserService;
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

public class MovieListFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_COLUMN_COUNT = "column-count";
    String jwt;
    MediaService service;
    UserService userService;
    List<MediaResponse> items;
    MovieListAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    RecyclerView recycler;
    Spinner spinner;
    private String selectedGenre;
    private List<GenreResponse> genres = new ArrayList<>();
    private Context ctx;
    private int mColumnCount = 3;
    ProgressDialog pgDialog;
    private MovieListListener mListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public MovieListFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static MovieListFragment newInstance(int columnCount) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Objects.requireNonNull(getActivity()).setTitle("Movies");
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
        listMovies();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(ctx, "Adios", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.media_search:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void listMovies() {
        MediaService service = ServiceGenerator.createService(MediaService.class, jwt, AuthType.JWT);
        Call<ResponseContainer<MediaResponse>> call = service.getAllMovies(selectedGenre);
        call.enqueue(new Callback<ResponseContainer<MediaResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<MediaResponse>> call, Response<ResponseContainer<MediaResponse>> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    pgDialog.dismiss();
                    items = response.body().getRows();
                    adapter = new MovieListAdapter(ctx, items, mListener);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_media_list, container, false);
//        layout.findViewById(R.id.fab_map).setVisibility(View.GONE);
//        layout.findViewById(R.id.fab_list).setVisibility(View.VISIBLE);

        if (layout instanceof SwipeRefreshLayout) {
            ctx = layout.getContext();
            recycler = layout.findViewById(R.id.media_recyclerview);
            if (mColumnCount <= 1) {
                recycler.setLayoutManager(new LinearLayoutManager(ctx));
            } else {
                recycler.setLayoutManager(new GridLayoutManager(ctx, mColumnCount));
            }
            items = new ArrayList<>();
            listMovies();
            pgDialog = new ProgressDialog(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            pgDialog.setIndeterminate(true);
            pgDialog.setCancelable(false);
            pgDialog.setTitle("Loading data");
            pgDialog.show();
            adapter = new MovieListAdapter(ctx, items, mListener);
            recycler.setAdapter(adapter);
            swipeLayout = layout.findViewById(R.id.swipeContainer);
            swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary), ContextCompat.getColor(getContext(), R.color.colorAccent));
            swipeLayout.setOnRefreshListener(() -> {
                listMovies();
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
        if (context instanceof MovieListListener) {
            mListener = (MovieListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MovieListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
