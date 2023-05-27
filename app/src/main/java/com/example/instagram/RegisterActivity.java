package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.instagram.databinding.ActivityLoginBinding;
import com.example.instagram.databinding.ActivityRegisterBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        EditText usernameET = binding.username;
        EditText nameET = binding.name;
        EditText lastnameET = binding.lastname;
        EditText emailET = binding.email;
        EditText passET = binding.password;
        EditText repeatPassET = binding.repeatPass;

        TextInputLayout usernameL = binding.usernameTextField;
        TextInputLayout nameL = binding.nameTextField;
        TextInputLayout lastnameL = binding.lastnameTextField;
        TextInputLayout emailL = binding.emailTextField;
        TextInputLayout passL = binding.passwordTextField;
        TextInputLayout repeatPassL = binding.repeatPassTextField;

        emailET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email = String.valueOf(emailET.getText());
                if(!b) {

                    if(!validate(email) && !email.equals("")){
                        Log.d("xxx", "onFocusChange: ");
                        emailL.setError("wrong email");

                    } else{
                        Log.d("xxx", "onFocusChange: here");
                        emailL.setError(null);
                    }
                }
            }
        } );
        repeatPassET.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                String pass = String.valueOf(passET.getText());
                String repeatPass = String.valueOf(repeatPassET.getText());
                if(!b) {
                    if(!pass.equals(repeatPass) && !repeatPass.equals("")) {
                        repeatPassL.setError("passwords don't match");
                    }else{
                        repeatPassL.setError(null);
                    }
                }
            }
        } );
        binding.registerBtn.setOnClickListener(new View.OnClickListener()
         {
            @Override
            public void onClick(View view) {
                String username = usernameET.getText().toString();
                String name = nameET.getText().toString();
                String lastname = lastnameET.getText().toString();
                String email = emailET.getText().toString();
                String pass = passET.getText().toString();
                String repeatPass = repeatPassET.getText().toString();

                String[] fields = {username, name, lastname, email, pass, repeatPass};
                TextInputLayout[] errorFields = {usernameL, nameL, lastnameL, emailL, passL, repeatPassL};



                if (fields[0].isEmpty() || fields[1].isEmpty() || fields[2].isEmpty() || !validate(email) || pass.isEmpty() || !pass.equals(repeatPass)) {


                    for (int i = 0; i < errorFields.length; i++) {
                        errorFields[i].setError(fields[i].isEmpty() ? "empty field" : null);
                    }

                    if (!validate(email) && !email.isEmpty()) {
                        emailL.setError("wrong email");
                    }

                    if (!pass.equals(repeatPass) && !repeatPass.isEmpty() && !pass.isEmpty()) {
                        repeatPassL.setError("passwords don't match");
                    }
                } else {
                    Log.d("xxx", "register");
                    for (int i = 0; i < errorFields.length; i++) {
                        errorFields[i].setError(null);
                    }

                }




                }
        });
        binding.loginBtn.setOnClickListener(v->{
            Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(login);
        });



    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return true;
    }
}
