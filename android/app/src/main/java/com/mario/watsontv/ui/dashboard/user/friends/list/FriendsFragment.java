package com.mario.watsontv.ui.dashboard.user.friends.list;

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
import android.widget.SearchView;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.ResponseContainer;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.ui.dashboard.user.friends.detail.UserDetailsFragment;
import com.mario.watsontv.util.UtilToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment implements FriendsListener, SearchView.OnQueryTextListener {
    private static final String ARG_COLUMN_COUNT = "column-count";
    UserService userService;
    List<UserResponse> items;
    FriendsAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    RecyclerView recycler;
    private int mColumnCount = 3;
    ProgressDialog pgDialog;
    boolean isScrolling = false;
    int currentPage = 1;
    int maxPage, totalItems;
    int maxItemsInPage = 30;
    private boolean allUsers = false;
    private Context ctx;
    private String jwt, nameQuery;
    private FriendsListener mListener;


    public FriendsFragment() {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Objects.requireNonNull(getActivity()).setTitle("Users");
        inflater.inflate(R.menu.fragment_friends_menu, menu);
//        allUsers = getArguments().getBoolean("allUsers");
        MenuItem filter = menu.findItem(R.id.menu_friends_filter);
        SearchView searchView = (SearchView) menu.findItem(R.id.friends_search).getActionView();
        searchView.setOnQueryTextListener(this);
        filter.setChecked(!allUsers);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        nameQuery = query;
        if (allUsers) listUsers(currentPage);
        else listFriends(currentPage);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() == 0) {
            nameQuery = newText;
            if (allUsers) listUsers(currentPage);
            else listFriends(currentPage);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.friends_befriended_filter:
                if(item.isChecked()){
                    allUsers = !allUsers;
                    item.setChecked(allUsers);
                    listUsers(1);
                } else {
                    allUsers = !allUsers;
                    item.setChecked(allUsers);
                    listFriends(1);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO: Rename and change types and number of parameters
    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allUsers = getArguments().getBoolean("allUsers");
        }
        Objects.requireNonNull(getActivity()).setTitle("Friends");
        jwt = UtilToken.getToken(getContext());
        mListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_friends, container, false);
        if (layout instanceof SwipeRefreshLayout) {
            ctx = layout.getContext();
            recycler = layout.findViewById(R.id.friends_recycler);
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(ctx, mColumnCount);
            recycler.setLayoutManager(gridLayoutManager);
            items = new ArrayList<>();
            if (allUsers) listUsers(currentPage); else listFriends(currentPage);
            pgDialog = new ProgressDialog(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            pgDialog.setIndeterminate(true);
            pgDialog.setCancelable(false);
            pgDialog.setTitle("Loading data");
            pgDialog.show();
            adapter = new FriendsAdapter(ctx, items, mListener);
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
                        listUsers(currentPage);
                    }
                }
            });
            swipeLayout = layout.findViewById(R.id.friends_swipeContainer);
            swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary), ContextCompat.getColor(getContext(), R.color.colorAccent));
            swipeLayout.setOnRefreshListener(() -> {
                currentPage = 1;
                if (allUsers) listUsers(currentPage); else listFriends(currentPage);
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

    public void listUsers(int page) {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<ResponseContainer<UserResponse>> call = service.listUsers(nameQuery, page);
        call.enqueue(new Callback<ResponseContainer<UserResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<UserResponse>> call, Response<ResponseContainer<UserResponse>> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                    pgDialog.dismiss();
                } else {
                    totalItems = (int) response.body().getCount();
                    maxPage = totalItems/maxItemsInPage;
                    pgDialog.dismiss();
                    if (page == 1) {
                        items.clear();
                        items = response.body().getRows();
                    } else
                        items.addAll(response.body().getRows());
                    adapter = new FriendsAdapter(ctx, items, mListener);
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<UserResponse>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                pgDialog.dismiss();
            }
        });
    }

    public void listFriends(int page) {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<List<UserResponse>> call = service.listFriends(nameQuery, page);
        call.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                if (response.code() != 200) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                    pgDialog.dismiss();
                } else {
                    maxPage = totalItems/maxItemsInPage;
                    pgDialog.dismiss();
                    if (page == 1) {
                        items.clear();
                        items = response.body();
                    } else
                        items.addAll(response.body());
                    adapter = new FriendsAdapter(ctx, items, mListener);
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                pgDialog.dismiss();
            }
        });
    }

    @Override
    public void updateFriend(String id) {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<UserResponse> call = service.updateFriended(id);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                } else {
                    if (allUsers) listUsers(currentPage); else listFriends(currentPage);
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
    public void goToUserDetails(String id) {
        UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selectedUserId", id);
        userDetailsFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, userDetailsFragment).commit();
    }
}
