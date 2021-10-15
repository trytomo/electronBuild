package com.d2i.stockmanagement.screen.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.d2i.stockmanagement.entity.response.LoginResponse;
import com.d2i.stockmanagement.entity.response.ServerResponse;
import com.d2i.stockmanagement.entity.request.LoginRequest;
import com.d2i.stockmanagement.screen.BaseActivity;
import com.d2i.stockmanagement.R;
import com.d2i.stockmanagement.screen.menu.MenuActivity;
import com.d2i.stockmanagement.service.AuthService;
import com.d2i.stockmanagement.utils.ApiHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {
    MaterialButton signInButton;
    MaterialButton forgotPasswordButton;
    TextInputEditText usernameTextInput;
    TextInputEditText passwordTextInput;

    String usernameValue = "";
    String passwordValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
    }

    private void initUI() {
        usernameTextInput = findViewById(R.id.username);
        usernameTextInput.addTextChangedListener(onUsernameChange);

        passwordTextInput = findViewById(R.id.password);
        passwordTextInput.addTextChangedListener(onPasswordChange);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> onSignInButtonClicked());

        forgotPasswordButton = findViewById(R.id.forget_password_button);
        forgotPasswordButton.setOnClickListener(view -> onForgetButtonClicked());
    }

    private final TextWatcher onUsernameChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            usernameValue = charSequence.toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private final TextWatcher onPasswordChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            passwordValue = charSequence.toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void onSignInButtonClicked() {
        ApiHelper apiHelper = new ApiHelper(getApplicationContext());
        Retrofit retrofit = apiHelper.getRetrofit();
        AuthService service = retrofit.create(AuthService.class);
        LoginRequest loginRequest = new LoginRequest(usernameValue, passwordValue);
        Call<ServerResponse<LoginResponse>> call = service.login(loginRequest);

        call.enqueue(new Callback<ServerResponse<LoginResponse>>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse<LoginResponse>> call, @NonNull Response<ServerResponse<LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getData().getToken();
                    Log.d("TOKEN", token);
                    apiHelper.setAccessToken(token);
                    if (token != null) {
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse<LoginResponse>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onForgetButtonClicked() {
        Toast.makeText(this, "Not implementation", Toast.LENGTH_LONG).show();
    }
}