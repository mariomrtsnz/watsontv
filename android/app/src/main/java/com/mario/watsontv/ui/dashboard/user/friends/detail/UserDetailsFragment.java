package com.mario.watsontv.ui.dashboard.user.friends.detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mario.watsontv.R;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.ui.dashboard.media.collections.list.CollectionListFragment;
import com.mario.watsontv.ui.dashboard.user.stats.StatsActivity;
import com.mario.watsontv.util.UtilToken;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

public class UserDetailsFragment extends Fragment implements UserDetailsListener {

    private UserDetailsListener mListener;
    private String selectedUserId, jwt;
    private Context ctx;
    private TextView tvUsername, tvMemberSince;
    private CircleImageView civProfilePic;
    private FloatingActionButton fabBefriend;
    private UserResponse selectedUser;
    private ProgressDialog pgDialog;
    private CardView cvStats, cvCollections;

    public UserDetailsFragment() {}

    // TODO: Rename and change types and number of parameters
    public static UserDetailsFragment newInstance(String param1, String param2) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedUserId = getArguments().getString("selectedUserId");
        }
        mListener = this;
        jwt = UtilToken.getToken(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_user_details, container, false);
        civProfilePic = layout.findViewById(R.id.user_details_civ_profilePic);
        cvCollections = layout.findViewById(R.id.user_details_collections);
        cvStats = layout.findViewById(R.id.user_details_stats);
        cvCollections.setOnClickListener(v -> mListener.goToCollections(selectedUserId));
        cvStats.setOnClickListener(v -> mListener.goToStats(selectedUserId));
        tvUsername = layout.findViewById(R.id.user_details_tv_username);
        tvMemberSince = layout.findViewById(R.id.user_details_tv_memberSince);
        fabBefriend = layout.findViewById(R.id.user_details_fab_befriend);
        fabBefriend.setOnClickListener(v -> mListener.updateFriend(selectedUserId));
        pgDialog = new ProgressDialog(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        pgDialog.setIndeterminate(true);
        pgDialog.setCancelable(false);
        pgDialog.setTitle("Loading data");
        pgDialog.show();
        getUserData();
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

    private void getUserData() {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<UserResponse> call = service.getUser(selectedUserId);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                    pgDialog.dismiss();
                } else {
                    selectedUser = response.body();
                    setData();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                pgDialog.dismiss();
            }
        });
    }

    private void setData() {
        Glide.with(ctx).load(selectedUser.getPicture()).into(civProfilePic);
        tvUsername.setText(selectedUser.getName());
        if (selectedUser.isFriend())
            fabBefriend.setImageResource(R.drawable.ic_remove_24dp);
        Calendar createdAt = null;
        try {
            createdAt = selectedUser.getCreatedAt();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String day = String.valueOf(createdAt.get(Calendar.DAY_OF_MONTH));
        String month = createdAt.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String year = String.valueOf(createdAt.get(Calendar.YEAR));

        tvMemberSince.setText("Member since " + month + " " + day + ", " + year);
        pgDialog.dismiss();
    }

    @Override
    public void goToStats(String id) {
        Intent i = new Intent(ctx, StatsActivity.class);
        i.putExtra("selectedUserId", selectedUserId);
        startActivity(i);
    }

    @Override
    public void goToCollections(String id) {
        CollectionListFragment collectionListFragment = new CollectionListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selectedUserId", id);
        collectionListFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, collectionListFragment).commit();
    }

    @Override
    public void updateFriend(String selectedUserId) {
        
    }
}
