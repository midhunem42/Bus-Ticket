package com.neuroid.etickets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neuroid.etickets.Ticket.TicketResultActivity;

import org.json.JSONObject;

public class Payout extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    Button b1,b2;
    TextView price,from,to,FareTv;
    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout);
        cardView =findViewById(R.id.cvPayment);
        from=findViewById(R.id.from);
        to = findViewById(R.id.to);
        price=findViewById(R.id.price);
        FareTv = findViewById(R.id.FareTv);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("data", 0);
        String data= pref.getString("obj", null);
        Log.d("Payment" ,data);
        try {
            JSONObject obj = new JSONObject(data);
            String fares = "( " + obj.getString("fare") + " per person )";
            price.setText(obj.getString("price") );
            FareTv.setText(fares);
            to.setText(obj.getString("to"));
            from.setText(obj.getString("from"));

        } catch (Throwable t) {
//            Log.e("My App", "Could not parse malformed JSON: \"" + data + "\"");
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Payout.this, TicketResultActivity.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
