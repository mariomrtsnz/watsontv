package com.mario.watsontv.ui.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mario.watsontv.R;
import com.mario.watsontv.dto.RegisterDto;
import com.mario.watsontv.responses.LoginResponse;
import com.mario.watsontv.retrofit.generator.ServiceGenerator;
import com.mario.watsontv.retrofit.services.LoginService;
import com.mario.watsontv.ui.auth.login.LoginFragment;
import com.mario.watsontv.ui.auth.login.LoginListener;
import com.mario.watsontv.ui.auth.signup.SignupFragment;
import com.mario.watsontv.ui.auth.signup.SignupListener;
import com.mario.watsontv.ui.dashboard.MainActivity;
import com.mario.watsontv.util.UtilToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  implements LoginListener, SignupListener {
    FragmentTransaction fragmentChanger;
    private Fragment login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = new LoginFragment();
        signup = new SignupFragment();
        fragmentChanger = getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container, new LoginFragment());
        fragmentChanger.commit();
    }

    @Override
    public void onLoginSubmit(String credentials) {
        LoginService service = ServiceGenerator.createService(LoginService.class);
        Call<LoginResponse> call = service.doLogin(credentials);

        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() != 201) {
                    // error
                    Log.e("Request Error", response.message());
                    Toast.makeText(getApplicationContext(), "Error trying to login", Toast.LENGTH_SHORT).show();
                } else {
                    // exito
                    UtilToken.setToken(getApplicationContext(), response.body().getToken());
                    UtilToken.setId(getApplicationContext(), response.body().getUser().get_id());
                    UtilToken.setUserLoggedData(getApplicationContext(), response.body().getUser());
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error. Can't connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void goToSignup() {
        fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.login_fragment_container, signup);
        fragmentChanger.commit();
    }

    @Override
    public void onSignupSubmit(RegisterDto registerDto) {
        LoginService service = ServiceGenerator.createService(LoginService.class);
        Call<LoginResponse> call = service.doSignUp(registerDto);

        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() != 201) {
                    Log.e("Request Error", response.message());
                    Toast.makeText(getApplicationContext(), "Error trying to register", Toast.LENGTH_SHORT).show();
                } else {
                    UtilToken.setToken(getApplicationContext(), response.body().getToken());
                    UtilToken.setId(getApplicationContext(), response.body().getUser().get_id());
                    UtilToken.setUserLoggedData(getApplicationContext(), response.body().getUser());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error. Can't connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void goToLogin() {
        fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.login_fragment_container, login);
        fragmentChanger.commit();
    }
}
