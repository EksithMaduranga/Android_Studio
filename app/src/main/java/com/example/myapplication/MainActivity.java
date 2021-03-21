package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn= findViewById(R.id.button11);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Activity2.class);
                startActivity(intent);
            }
        });




        TextView textview = findViewById(R.id.txt);
        String text="MY FIT";
        SpannableString ss= new SpannableString(text);
        StyleSpan boldSpan= new StyleSpan(Typeface.BOLD);
        UnderlineSpan underlineSpan= new UnderlineSpan();
        ss.setSpan(boldSpan, 0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(underlineSpan,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textview.setText(ss);

    }
}