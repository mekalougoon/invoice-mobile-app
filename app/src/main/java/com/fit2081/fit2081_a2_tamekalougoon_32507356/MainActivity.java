package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // for user to change to log in
    public void onLoginBtnClick (View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    // for user to register
    public void onRegisterBtnClick (View view) {

        EditText usernameET, passwordET, passwordConfirmET;

        // get username input
        usernameET = findViewById(R.id.usernameET);
        String username = usernameET.getText().toString();

        // get both password inputs
        passwordET = findViewById(R.id.passwordET);
        String password = passwordET.getText().toString();

        passwordConfirmET = findViewById(R.id.passwordConfirmET);
        String passwordConfirm = passwordConfirmET.getText().toString();

        // error prevention
        if (username.equals("")) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if (!password.equals(passwordConfirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
        }

        // on successful attempt
        else if (password.equals(passwordConfirm) && !username.equals("")) {

            SharedPreferences loginSP = getApplicationContext().getSharedPreferences("loginPref", 0);
            SharedPreferences.Editor editor = loginSP.edit();
            //store in shared pref
            editor.putString("Username", username);
            editor.putString("Password", password);
            editor.putString("PasswordConfirm", passwordConfirm);

            editor.commit();


            String usernameSaved = loginSP.getString("username", null);
            String passwordSaved = loginSP.getString("password", null);

            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();

            // switch to login page
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }




}