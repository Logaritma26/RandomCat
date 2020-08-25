package com.log.randomcat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private long mLastClickTime = 0;

    TextView textView;
    CardView cardView;

    private String FACT = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        textView = findViewById(R.id.text2);
        cardView = findViewById(R.id.card);

        setRetrofit();

        

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                setRandomAnim(cardView, 1);
                textView.setText(FACT);
                setRetrofit();
            }
        });


    }

    private void setRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cat-fact.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolder holder = retrofit.create(JsonPlaceHolder.class);

        Call<POJO> call = holder.get_text();

        call.enqueue(new Callback<POJO>() {
            @Override
            public void onResponse(Call<POJO> call, Response<POJO> response) {
                if (response.isSuccessful()) {
                    POJO pojo = response.body();
                    FACT = pojo.getText_body();
                } else {
                    FACT = "There is a problem with connection, sorry for that -_-";
                }
            }

            @Override
            public void onFailure(Call<POJO> call, Throwable t) {
                FACT = "There is a problem with connection, sorry for that -_-";
                Log.d("callback", "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    private void setRandomAnim(CardView card, int val) {
        if (val == 0) {
            card.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.spin_left));
        } else if (val == 1) {
            card.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade));
        }

    }

}