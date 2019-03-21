package com.mario.watsontv.ui.dashboard.user.profile.settings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mario.watsontv.R;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.ui.dashboard.user.profile.edit.EditProfile;
import com.mario.watsontv.util.UtilToken;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment implements SettingsListener {
    // TODO: Rename and change types of parameters
    private SettingsListener mListener;
    private Context ctx;
    private String jwt;
    private TextView tvEmail, tvUsername, tvMemberSince;
    private Switch switchDarkMode;
    private CircleImageView civPicture;
    private UserResponse loggedUser;
    private ProgressDialog pgDialog;
    private FloatingActionButton fabEdit;
    private SwipeRefreshLayout swipeLayout;

    public SettingsFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        mListener = this;
        jwt = UtilToken.getToken(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_settings, container, false);
        civPicture = layout.findViewById(R.id.edit_profile_settings_civ_profilePic);
        tvEmail = layout.findViewById(R.id.user_details_settings_tv_email);
        tvUsername = layout.findViewById(R.id.user_details_settings_tv_username);
        tvMemberSince = layout.findViewById(R.id.user_details_settings_tv_memberSince);
        switchDarkMode = layout.findViewById(R.id.user_details_settings_switch_darkMode);
        pgDialog = new ProgressDialog(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        fabEdit = layout.findViewById(R.id.user_details_settings_fab_edit);
        pgDialog.setIndeterminate(true);
        pgDialog.setCancelable(false);
        pgDialog.setTitle("Loading profile");
        pgDialog.show();
        getUserData();
        swipeLayout = layout.findViewById(R.id.user_details_settings_swipeRefresh);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary), ContextCompat.getColor(getContext(), R.color.colorAccent));
        swipeLayout.setOnRefreshListener(() -> {
            getUserData();
            if (swipeLayout.isRefreshing()) {
                swipeLayout.setRefreshing(false);
            }
        });
        return layout;
    }

    private void getUserData() {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<UserResponse> call = service.getUser(UtilToken.getId(ctx));
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
                    pgDialog.dismiss();
                } else {
                    loggedUser = response.body();
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
        Glide.with(ctx).load(loggedUser.getPicture()).into(civPicture);
        tvEmail.setText(loggedUser.getEmail());
        tvUsername.setText(loggedUser.getName());
        Calendar createdAt = null;
        try {
            createdAt = loggedUser.getCreatedAt();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String day = String.valueOf(createdAt.get(Calendar.DAY_OF_MONTH));
        String month = createdAt.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String year = String.valueOf(createdAt.get(Calendar.YEAR));
        tvMemberSince.setText("Member since " + month + " " + day + ", " + year);
        fabEdit.setOnClickListener(v -> mListener.editProfile());
        pgDialog.dismiss();
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
    public void editProfile() {
        Intent i = new Intent(ctx, EditProfile.class);
        Bundle bundle = new Bundle();
        i.putExtra("loggedUserData", loggedUser);
        startActivityForResult(i, Activity.RESULT_OK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            pgDialog = new ProgressDialog(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            fabEdit = getView().findViewById(R.id.user_details_settings_fab_edit);
            pgDialog.setIndeterminate(true);
            pgDialog.setCancelable(false);
            pgDialog.setTitle("Loading profile");
            pgDialog.show();
            getUserData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
