package com.mario.watsontv.ui.dashboard.user.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mario.watsontv.R;
import com.mario.watsontv.ui.auth.LoginActivity;
import com.mario.watsontv.ui.dashboard.media.collections.list.CollectionListFragment;
import com.mario.watsontv.ui.dashboard.user.friends.list.FriendsFragment;
import com.mario.watsontv.ui.dashboard.user.profile.settings.SettingsFragment;
import com.mario.watsontv.ui.dashboard.user.stats.StatsActivity;
import com.mario.watsontv.util.UtilToken;

import java.util.Objects;


public class ProfileFragment extends Fragment implements ProfileListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProfileListener mListener;
    private ImageView profilePic;
    private TextView username;
    private Context ctx;
    private CardView stats, collections, settings, friends, logout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(getActivity()).setTitle("Profile");
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic = layout.findViewById(R.id.user_details_civ_profilePic);
        username = layout.findViewById(R.id.user_details_tv_username);
        Glide.with(this).load(UtilToken.getProfilePic(ctx)).into(profilePic);
        username.setText(UtilToken.getName(ctx));
        collections = layout.findViewById(R.id.profile_card_collections);
        collections.setOnClickListener(v -> goToCollections());
        stats = layout.findViewById(R.id.profile_card_stats);
        stats.setOnClickListener(v -> goToStats());
        settings = layout.findViewById(R.id.profile_card_settings);
        settings.setOnClickListener(v -> goToSettings());
        friends = layout.findViewById(R.id.profile_card_friends);
        friends.setOnClickListener(v -> goToFriends());
        logout = layout.findViewById(R.id.profile_card_logout);
        logout.setOnClickListener(v -> logout());
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
    public void goToStats() {
        Intent i = new Intent(ctx, StatsActivity.class);
        startActivity(i);
    }

    @Override
    public void goToCollections() {
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, new CollectionListFragment()).commit();
    }

    @Override
    public void goToSettings() {
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, new SettingsFragment()).commit();
    }

    @Override
    public void goToFriends() {
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, new FriendsFragment()).commit();
    }

    @Override
    public void logout() {
        UtilToken.setId(ctx, null);
        UtilToken.setToken(ctx, null);
        UtilToken.setUserLoggedData(ctx, null);
        getActivity().finish();
        Intent logoutIntent = new Intent(getActivity(), LoginActivity.class);
        logoutIntent.putExtra("isLogin", true);
        startActivity(logoutIntent);
    }
}
