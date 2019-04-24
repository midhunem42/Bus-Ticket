package com.neuroid.etickets.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neuroid.etickets.R;

public class Signup extends AppCompatActivity {
  AutoCompleteTextView username,password,email;
  Button signup;
  TextView signIn;
  String uname,passwordSt,stEmail;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);

    username=findViewById(R.id.atvUsernameReg);
    password=findViewById(R.id.atvPasswordReg);
    email = findViewById(R.id.atvEmailReg);

    signup=findViewById(R.id.btnSignUp);
    signIn=findViewById(R.id.tvSignIn);

    signIn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
        startActivity(new Intent(Signup.this,Login.class));
      }
    });


    signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        uname= username.getText().toString();
        passwordSt= password.getText().toString();
        stEmail=email.getText().toString();

        if(uname.equals("")){
          Toast.makeText(Signup.this,"Username can't be empty",Toast.LENGTH_SHORT).show();
          return;
        }
        if(passwordSt.equals("")){
          Toast.makeText(Signup.this,"Password can't be empty",Toast.LENGTH_SHORT).show();
          return;
        }
        if(email.equals("")){
          Toast.makeText(Signup.this,"Email can't be empty",Toast.LENGTH_SHORT).show();
          return;
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", uname);
        editor.putString("password", passwordSt);
        editor.putString("email", stEmail);
        editor.commit();
        Toast.makeText(Signup.this,"User Registered",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(Signup.this,Login.class));
      }
    });
  }
}
