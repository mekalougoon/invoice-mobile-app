package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    // for user to change to register
    public void onSignUpBtnClick(View view) {
        Intent registerIntent = new Intent(this, MainActivity.class);
        startActivity(registerIntent);
    }

    // for user to log in
    public void onLoginBtnClick(View view) {

        EditText usernameET, passwordET, passwordConfirmET;
        SharedPreferences loginsp = getApplicationContext().getSharedPreferences("loginPref", 0);

        // get username input
        usernameET = findViewById(R.id.usernameET);
        String username = usernameET.getText().toString();

        String usernameSaved = loginsp.getString("Username", null);

        // get password input
        passwordET = findViewById(R.id.passwordET);
        String password = passwordET.getText().toString();

        String passwordSaved = loginsp.getString("Password", null);

        // check against saved fields


        // unsuccessful attempt
        if (!username.equals(usernameSaved) || !password.equals(passwordSaved)) {
            Toast.makeText(this, "Username or password is incorrect", Toast.LENGTH_LONG).show();
        }

        // on successful attempt
        if (username.equals(usernameSaved) && password.equals(passwordSaved)) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();

            // switch to login page
            Intent invoiceIntent = new Intent(this, InvoiceActivity.class);
            startActivity(invoiceIntent);
            finish();
        }
    }
}
