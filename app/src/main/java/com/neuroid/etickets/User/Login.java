package com.neuroid.etickets.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neuroid.etickets.R;
import com.neuroid.etickets.Scanning;

public class Login extends AppCompatActivity {

  AutoCompleteTextView atemail,atPassword;
  Button signin;
  TextView signup;
  String savedEmail,savedPassword,email,password;
  WifiManager wifiManager;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);


    final String deviceSSID="Ticket_machine";
    String wifipassword ="raspberry";
    wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);


    WifiConfiguration wifiConfig = new WifiConfiguration();
    wifiConfig.SSID = String.format("\"%s\"", deviceSSID);
    wifiConfig.preSharedKey = String.format("\"%s\"",wifipassword);

    int netId = wifiManager.addNetwork(wifiConfig);
    wifiManager.disconnect();
    wifiManager.enableNetwork(netId, true);
    wifiManager.reconnect();

    if(wifiManager.isWifiEnabled() == false){
      wifiManager.setWifiEnabled(true);
    }



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
        String ssd= wifiManager.getConnectionInfo().getSSID();

        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        String url ="http://"+ip+":3000";

//        if(ssd.equals("\"Ticket_machine\"")){
            if(savedEmail.equals(email)&& savedPassword.equals(password)){

              SharedPreferences pref = getApplicationContext().getSharedPreferences("url", 0);
              SharedPreferences.Editor editor = pref.edit();
              editor.putString("url",url);
              editor.commit();

              Toast.makeText(Login.this,"Login Success",Toast.LENGTH_SHORT).show();
              finish();
              startActivity(new Intent(Login.this, Scanning.class));
            }else {
              Toast.makeText(Login.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
            }
//        }else{
//          dialog(Login.this).show();
//        }
      }
    });
  }

  public AlertDialog.Builder dialog(Context context){
    AlertDialog.Builder builder =new AlertDialog.Builder(context);
    builder.setTitle("Device not connected");
    builder.setMessage("You have to turn on Device to access this");

    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        finish();
      }
    });
    return  builder;
  }
}
