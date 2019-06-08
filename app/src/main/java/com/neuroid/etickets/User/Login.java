package com.neuroid.etickets.User;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neuroid.etickets.R;
import com.neuroid.etickets.Scanning;

public class Login extends AppCompatActivity {

  AutoCompleteTextView atemail,atPassword;
  Button signin,btnExternalScanner;
  TextView signup;
  String savedEmail,savedPassword,email,password;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    btnExternalScanner = findViewById(R.id.btnExternalScanner);

    btnExternalScanner.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String packageName = "tw.mobileapp.qrcode.banner";

        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);

        if(intent == null) {
          try {
            // if play store installed, open play store, else open browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
          } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
          }
        }
        startActivity(intent);
      }
    });

    signin = findViewById(R.id.btnSignIn);
    SharedPreferences pref = getApplicationContext().getSharedPreferences("user", 0);
    savedEmail=pref.getString("email","savedEmail");
    savedPassword=pref.getString("password","pass");

    atemail=findViewById(R.id.atvEmailLog);
    atPassword=findViewById(R.id.atvPasswordLog);

    signup=findViewById(R.id.tvSignUp);

    signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
        startActivity(new Intent(Login.this,Signup.class));
      }
    });

    signin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        email=atemail.getText().toString();
        password=atPassword.getText().toString();
            if(savedEmail.equals(email)&& savedPassword.equals(password)){

              Toast.makeText(Login.this,"Login Success",Toast.LENGTH_SHORT).show();
              finish();
              startActivity(new Intent(Login.this, Scanning.class));
            }else {
              Toast.makeText(Login.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
            }
      }
    });
  }


}
