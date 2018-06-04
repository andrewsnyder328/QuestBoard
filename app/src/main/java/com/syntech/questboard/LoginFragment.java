package com.syntech.questboard;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Character;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

import static android.content.ContentValues.TAG;

public class LoginFragment extends Fragment{

    TextInputLayout emailLayout;
    TextInputLayout nameLayout;
    private EditText emailField;
    private EditText nameField;
    private TextView submitTv;
    android.support.v4.app.FragmentTransaction transaction;
    private MainFragment fragment;
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submitTv = (TextView) view.findViewById(R.id.submit_tv);
        emailField = (EditText) view.findViewById(R.id.email_field);
        nameField = (EditText) view.findViewById(R.id.name_field);
        emailLayout = (TextInputLayout) view.findViewById(R.id.input_layout_email);
        nameLayout = (TextInputLayout) view.findViewById(R.id.input_layout_name);

        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nameLayout.setError(null);
            }
        });

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                emailLayout.setError(null);
            }
        });

        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new MainFragment();
                fm = getFragmentManager();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.contentFragment, fragment);
                if (isValidEmail(emailField.getText().toString())&& nameField.getText().toString().length() > 0){
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("email", emailField.getText().toString());

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://challenge2015.myriadapps.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RequestInterface request = retrofit.create(RequestInterface.class);
                    Call<Message> call = request.registerEmail(emailField.getText().toString());
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message msg = response.body();
                            Log.i(TAG, msg.getMessage());
                            editor.commit();
                            transaction.commit();
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Log.e(TAG, t.getMessage());
                            call.cancel();
                        }
                    });

                } else {
                    if (!isValidEmail(emailField.getText().toString())){
                        emailLayout.setError("You must enter a valid Email.");
                        emailLayout.requestFocus();
                    }

                    if (nameField.getText().toString().length() == 0){
                        nameLayout.setError("You must enter a valid name.");
                        nameLayout.requestFocus();
                    }
                }
            }
        });

    }



    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
