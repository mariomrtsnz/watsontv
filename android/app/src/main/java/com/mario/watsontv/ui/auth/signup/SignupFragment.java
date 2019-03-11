package com.mario.watsontv.ui.auth.signup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mario.watsontv.R;
import com.mario.watsontv.dto.RegisterDto;
import com.mario.watsontv.ui.auth.login.LoginListener;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SignupListener mListener;
    private Context ctx;
    TextInputLayout email_input, name_input, password_input;
    TextView goToLogin;
    Button btn_signup;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email_input = getActivity().findViewById(R.id.signup_email_input);
        password_input = getActivity().findViewById(R.id.signup_password_input);
        name_input = getActivity().findViewById(R.id.signup_name_input);
        btn_signup = getActivity().findViewById(R.id.btn_signup_submit);
        goToLogin = getActivity().findViewById(R.id.tv_go_to_LogIn);
        goToLogin.setOnClickListener(v -> mListener.goToLogin());
        btn_signup.setOnClickListener(v -> {
                    String username_txt = name_input.getEditText().getText().toString();
                    String email_txt = email_input.getEditText().getText().toString();
                    String password_txt = password_input.getEditText().getText().toString();
                    final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);

                    if (username_txt.equals("") || email_txt.equals("") || password_txt.equals("")) {
                        Toast.makeText(ctx, "Fields can't be empty!", Toast.LENGTH_LONG).show();
                    } else if (!EMAIL_REGEX.matcher(email_txt).matches()) {
                        Toast.makeText(ctx, "You need to use a correct email!", Toast.LENGTH_LONG).show();
                    } else if (password_txt.length() < 6) {
                        Toast.makeText(ctx, "Password must be at least 6 characters!", Toast.LENGTH_LONG).show();
                    } else {
                        RegisterDto registerDto = new RegisterDto(username_txt, email_txt, password_txt);
                        mListener.onSignupSubmit(registerDto);
                    }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(RegisterDto registerDto) {
        if (mListener != null) {
            mListener.onSignupSubmit(registerDto);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
        if (context instanceof SignupListener) {
            mListener = (SignupListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SignupListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
