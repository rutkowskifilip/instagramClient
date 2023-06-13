package com.example.instagram.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.util.UnstableApi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.instagram.databinding.ActivityLoginBinding;
import com.example.instagram.requests.LoginRequest;
import com.example.instagram.store.Store;
import com.example.instagram.viewmodel.LoginViewModel;
import com.example.instagram.viewmodel.LoginViewModelListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UnstableApi
public class LoginActivity extends AppCompatActivity implements LoginViewModelListener {
    private ActivityLoginBinding binding;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.setListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText emailET = binding.email;
        TextInputLayout emailL = binding.emailTextField;
        TextInputLayout passL = binding.passwordTextField;
        emailET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email = String.valueOf(emailET.getText());
                if (!b) {
                    if (!validate(email) && !email.equals("")) {
                        emailL.setError("wrong email");
                    } else {
                        emailL.setError(null);
                    }
                }
            }
        });
        binding.loginBtn.setOnClickListener(v -> {
            String email = emailET.getText().toString();
            String password = binding.password.getText().toString();

            if (validate(email) && !password.isEmpty()) {
                emailL.setError(null);
                passL.setError(null);

                LoginRequest user = new LoginRequest(email, password);

                loginViewModel.login(user);
//                    Intent mainpage = new Intent(LoginActivity.this, MainPageActivity.class);
//                    String token = "123abc";
//                    mainpage.putExtra("token", token);
//                    startActivity(mainpage);

            } else {
                emailL.setError(email.isEmpty() ? "empty field" : (validate(email) ? null : "wrong email"));
                passL.setError(password.isEmpty() ? "empty field" : null);
            }
        });
        loginViewModel.getObservedUser().observe(LoginActivity.this, s -> {
            if (s != null) {
                Log.d("xxxx", "login: " + s.getUser().getUsername());
                Store.setToken(s.getToken());
                Store.setUser(s.getUser());
                Log.d("xxx", String.valueOf(s));
                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                startActivity(intent);
            } else {
                Log.d("xxx", "incorrect data");
            }
        });

        binding.registerBtn.setOnClickListener(v -> {
            Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(register);
        });
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Cancel", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}