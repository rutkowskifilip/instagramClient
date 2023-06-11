package com.example.instagram.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.util.UnstableApi;

import com.example.instagram.databinding.ActivityRegisterBinding;
import com.example.instagram.databinding.DialogClickableMessageBinding;
import com.example.instagram.requests.RegisterRequest;
import com.example.instagram.viewmodel.RegisterViewModel;
import com.example.instagram.viewmodel.RegisterViewModelListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UnstableApi public class RegisterActivity extends AppCompatActivity implements RegisterViewModelListener {
    private ActivityRegisterBinding binding;
    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.setListener(this);
        binding.setRegisterViewModel(registerViewModel);



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
                    RegisterRequest user = new RegisterRequest(username, name, lastname, email, pass);
                    registerViewModel.register(user);

                }




                }
        });
        registerViewModel.getObservedUser().observe(RegisterActivity.this, s->{
            binding.setRegisterViewModel(registerViewModel);
            if(s != null){

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{
                Log.d("xxx", "incorrect data");
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

    @Override
    public void showAlert(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        DialogClickableMessageBinding binding = DialogClickableMessageBinding.inflate(inflater, null, false);
        binding.setViewModel(registerViewModel);

        View view = binding.getRoot();

        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}

