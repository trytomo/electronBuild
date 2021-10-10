package com.d2i.stockmanagement.screen.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.d2i.stockmanagement.screen.BaseActivity;
import com.d2i.stockmanagement.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

public class LoginActivity extends BaseActivity {
    MaterialButton signInButton;
    MaterialButton forgotPasswordButton;
    TextInputEditText usernameTextInput;
    TextInputEditText passwordTextInput;

    AuthRepository authRepository;

    String usernameValue = "";
    String passwordValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authRepository = new AuthRepository();

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

    private TextWatcher onUsernameChange = new TextWatcher() {
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

    private TextWatcher onPasswordChange = new TextWatcher() {
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
        try {
            boolean success = authRepository.login(usernameValue, passwordValue);
            if (success) {
                Intent menuIntent = new Intent(this, null);
                startActivity(menuIntent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onForgetButtonClicked() {
        Toast.makeText(this, "Belum di implementasi", Toast.LENGTH_LONG).show();
    }
}