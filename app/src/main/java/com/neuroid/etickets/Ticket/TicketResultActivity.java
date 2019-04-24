package com.neuroid.etickets.Ticket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neuroid.etickets.R;
import com.neuroid.etickets.Scanning;
import com.neuroid.etickets.View.TicketView;

import org.json.JSONObject;

public class TicketResultActivity extends AppCompatActivity {

  private static final String TAG = TicketResultActivity.class.getSimpleName();
  private TicketView ticketView;

  // widgets
  private TextView txt_error,fromTicketTV,destTicketTv,lblPersons,priceTicketView,txt_Paying;
  private Button back;
  private ProgressBar progressBar;
  private String data;
  private  SharedPreferences pref;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ticket_result);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    // initialize widgets
    initialize();

    // set values to widgets


    new Handler().postDelayed(new Runnable(){
      @Override
      public void run() {
        setValues();
      }
    }, 5000);
  }



  private void initialize() {
    txt_error = findViewById(R.id.txt_error);
    txt_Paying = findViewById(R.id.txt_Paying);
    fromTicketTV = findViewById(R.id.fromTicketTV);
    destTicketTv = findViewById(R.id.destTicketTv);
    lblPersons = findViewById(R.id.lblPersons);
    priceTicketView = findViewById(R.id.priceTicketView);
    ticketView = findViewById(R.id.layout_ticket);
    back = findViewById(R.id.btn_back);
    progressBar = findViewById(R.id.progressBar);
    pref = getApplicationContext().getSharedPreferences("data", 0);
    data = pref.getString("obj", null);
  }

  private void setValues() {
    progressBar.setVisibility(View.GONE);
    txt_Paying.setVisibility(View.GONE);



    try {
      back.setVisibility(View.VISIBLE);
      ticketView.setVisibility(View.VISIBLE);

      back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(new Intent(TicketResultActivity.this, Scanning.class));
          finish();
        }
      });

      JSONObject obj = new JSONObject(data);

      priceTicketView.setText(obj.getString("price"));
      destTicketTv.setText(obj.getString("to"));
      fromTicketTV.setText(obj.getString("from"));
      lblPersons.setText(obj.getString("persons"));
    } catch (Throwable t) {
      txt_error.setVisibility(View.VISIBLE);
//            Log.e("My App", "Could not parse malformed JSON: \"" + data + "\"");
    }

  }
}
