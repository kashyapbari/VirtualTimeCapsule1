package com.example.barir.virtualtimecapsule1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class successfull extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfull);
        ImageView imagegif= findViewById(R.id.tickgif);
        Glide.with(this).load(R.drawable.success_tick).into(imagegif);
    }
}
