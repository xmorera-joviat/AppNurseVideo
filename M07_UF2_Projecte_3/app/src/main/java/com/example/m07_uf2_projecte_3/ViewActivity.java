package com.example.m07_uf2_projecte_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    private ImageView imageView;
    TextView textView;
    Button btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        imageView = findViewById(R.id.imgView);
        textView = findViewById(R.id.idTextView);
        btnDelete = findViewById(R.id.btnDelete);






    }
}
