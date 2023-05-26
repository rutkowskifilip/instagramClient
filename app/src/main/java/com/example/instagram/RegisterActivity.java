package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.instagram.databinding.ActivityLoginBinding;
import com.example.instagram.databinding.ActivityRegisterBinding;
import com.google.android.material.textfield.TextInputLayout;

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

        EditText nameET = binding.name;
        EditText lastnameET = binding.lastname;
        EditText emailET = binding.email;
        EditText passET = binding.password;
        EditText repaetPassET = binding.repeatPass;

        TextInputLayout emailL = binding.emailTextField;
        TextInputLayout repeatPassL = binding.repeatPassTextField;
        emailET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b) {
                    if(!validate(String.valueOf(emailET.getText())) && !String.valueOf(emailET.getText()).equals("")){
                        emailL.setError("wrong email");
                    }else{
                        emailL.setError(null);
                    }
                }
            }
        } );
        repaetPassET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b) {
                    if(!String.valueOf(passET.getText()).equals(String.valueOf(repaetPassET.getText())) && !String.valueOf(repaetPassET.getText()).equals("")) {
                        Log.d("xxx", String.valueOf(passET.getText()));
                        Log.d("xxx", String.valueOf(repaetPassET.getText()));
                        repeatPassL.setError("passwords don't match");
                    }else{
                        repeatPassL.setError(null);
                    }
                }
            }
        } );







        binding.registerBtn.setOnClickListener(v->{
            Log.d("xxx", "aaaa");
        });
    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}
