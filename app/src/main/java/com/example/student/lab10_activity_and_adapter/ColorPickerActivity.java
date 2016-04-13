package com.example.student.lab10_activity_and_adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class ColorPickerActivity extends AppCompatActivity {

    public static final String BUNDLE_KEY_COLOR_INT = "com.student.android.ColorInt";
    public static final String BUNDLE_KEY_COLOR_NAME = "com.student.android.ColorName";

    private int mColorInt;
    private CharSequence mColorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        initColorData();
    }
    //建立按下時產生預選的畫面
    public void initColorData(){
        //預選為 紅色
        RadioButton radio = (RadioButton)findViewById(R.id.radio_holo_red_light);
        mColorInt = radio.getCurrentTextColor();
        mColorName = radio.getText();

    }

    public void clickColor(View view){
        //建立RadioButton按下時 傳出Color
        RadioButton radio = (RadioButton)view;
        mColorInt = radio.getCurrentTextColor();
        mColorName =radio.getText();
    }
    //RESULT_OK
    public void ok(View view){
        //建立意圖
        Intent intent = new Intent();
        //設定包裹
        intent.putExtra(BUNDLE_KEY_COLOR_INT, mColorInt);//Key: String,Value: int
        intent.putExtra(BUNDLE_KEY_COLOR_NAME, mColorName);//Key: String,Value: String
        setResult(RESULT_OK, intent);//設定結果 OK, 傳送意圖
        finish();//傳送完成
    }

    public void cancel(View view){
        setResult(RESULT_CANCELED);
        finish();
    }



}
