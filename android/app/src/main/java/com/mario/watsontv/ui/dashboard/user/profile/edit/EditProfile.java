package com.mario.watsontv.ui.dashboard.user.profile.edit;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.mario.watsontv.R;
import com.mario.watsontv.dto.ProfileEditDto;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.retrofit.generator.AuthType;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.UserService;
import com.mario.watsontv.util.UtilToken;

import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {
    private UserResponse loggedUser;
    private Button btnEdit;
    private FloatingActionButton fabEditProfile;
    private TextInputLayout email_input, name_input, password_input;
    private CircleImageView civProfilePic;
    private ProgressDialog pgDialog;
    private Context ctx;
    private String jwt;
    private boolean editProfileIsFinished = false;
//    private boolean editPasswordIsFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ctx = this;
        jwt = UtilToken.getToken(ctx);
        loggedUser = (UserResponse) getIntent().getSerializableExtra("loggedUserData");
        civProfilePic = findViewById(R.id.edit_profile_settings_civ_profilePic);
        Glide.with(this).load(loggedUser.getPicture()).into(civProfilePic);
        email_input = findViewById(R.id.edit_profile_email_input);
        email_input.getEditText().setText(loggedUser.getEmail());
        name_input = findViewById(R.id.edit_profile_name_input);
        name_input.getEditText().setText(loggedUser.getName());
//        password_input = findViewById(R.id.edit_profile_password_input);
        btnEdit = findViewById(R.id.edit_profile_btn_edit);
        fabEditProfile = findViewById(R.id.edit_profile_fab_photo);
        fabEditProfile.setEnabled(false);
        btnEdit.setOnClickListener(v -> {
            String username_txt = name_input.getEditText().getText().toString();
            String email_txt = email_input.getEditText().getText().toString();
//            String password_txt = password_input.getEditText().getText().toString();
            // Solo porque el cambiar contraseña no está siendo implementado pero quiero tenerlo disponible por si lo implemento.
            String password_txt = "";
            final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);

            if (username_txt.equals("") || email_txt.equals("")) {
                Toast.makeText(this, "Fields can't be empty!", Toast.LENGTH_LONG).show();
            } else if (!EMAIL_REGEX.matcher(email_txt).matches()) {
                Toast.makeText(this, "You need to use a correct email!", Toast.LENGTH_LONG).show();
            }
//            else if (password_txt.length() < 6) {
//                Toast.makeText(this, "Password must be at least 6 characters!", Toast.LENGTH_LONG).show();
//            }
            else {
                ProfileEditDto profileDto = new ProfileEditDto(email_txt, username_txt);
                editSubmit(profileDto, password_txt);
                pgDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                pgDialog.setIndeterminate(true);
                pgDialog.setCancelable(false);
                pgDialog.setTitle("Loading profile");
                pgDialog.show();
            }
        });
    }

    private void editSubmit(ProfileEditDto profileDto, String password) {
        editProfile(profileDto);
//        editPassword(password);
    }

    private void editProfile(ProfileEditDto profileEditDto) {
        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
        Call<UserResponse> call = service.editUser(UtilToken.getId(this), profileEditDto);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ctx, "Request Error", Toast.LENGTH_SHORT).show();
                    pgDialog.dismiss();
                } else {
                    editProfileIsFinished = true;
//                    if (editProfileIsFinished && editPasswordIsFinished) {
//                        finish();
//                        pgDialog.dismiss();
//                    }
                    setResult(RESULT_OK);
                    finish();
                    pgDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(ctx, "Network Error", Toast.LENGTH_SHORT).show();
                pgDialog.dismiss();
            }
        });
    }

//    private void editPassword(String password) {
//        UserService service = ServiceGenerator.createService(UserService.class, jwt, AuthType.JWT);
//        Call<UserResponse> call = service.editPassword(UtilToken.getId(this), password);
//        call.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(ctx, "Request Error", Toast.LENGTH_SHORT).show();
//                    pgDialog.dismiss();
//                } else {
//                    editPasswordIsFinished = true;
//                    if (editProfileIsFinished && editPasswordIsFinished) {
//                        finish();
//                        pgDialog.dismiss();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Log.e("Network Failure", t.getMessage());
//                Toast.makeText(ctx, "Network Error", Toast.LENGTH_SHORT).show();
//                pgDialog.dismiss();
//            }
//        });
//    }
}
