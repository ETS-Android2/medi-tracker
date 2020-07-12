package com.gautam.medicinetime;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HealthTipsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);
        TextView secondTextView = new TextView(this);
        TextView textView11 = (TextView) findViewById(R.id.texttitle11);
        TextView textView = (TextView) findViewById(R.id.texttitle13);
        TextView textView14 = (TextView) findViewById(R.id.texttitle14);
      //  textView = (TextView) findViewById(R.id.main_tv);
       // textView.setText("Tianjin, China".toUpperCase());

        TextPaint paint = textView.getPaint();
        float width = paint.measureText("Tianjin, China");

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);
        Shader textShader1 = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#8FA3D9"),
                        Color.parseColor("#7489CE"),
                        Color.parseColor("#6275CC"),
                        Color.parseColor("#5266C0"),
                        Color.parseColor("#4750B8"),
                }, null, Shader.TileMode.CLAMP);
        Shader textShader2 = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#8FA3D9"),
                        Color.parseColor("#7489CE"),
                        Color.parseColor("#6275CC"),
                        Color.parseColor("#5266C0"),
                        Color.parseColor("#4750B8"),
                }, null, Shader.TileMode.CLAMP);
//        textView11.getPaint().setShader(textShader1);
//        textView14.getPaint().setShader(textShader);
//        textView.getPaint().setShader(textShader);
    }
}