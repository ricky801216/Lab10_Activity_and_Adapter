package com.example.student.lab10_activity_and_adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

public class ColorPickerActivity extends AppCompatActivity {

    public static final String BUNDLE_KEY_COLOR_INT = "com.student.android.ColorInt";
    public static final String BUNDLE_KEY_COLOR_NAME = "com.student.android.ColorName";
    private static final String TAG = "ColorPickerActivity";


    private int mColorInt;
    private CharSequence mColorName;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        initColorData();
        Log.d(TAG, "onCreate()");
    }
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()");
        restoreData();
    }
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
        saveData();
    }
    public void onSaveInstanceState(Bundle outSate, PersistableBundle outPersistentState){
        super.onSaveInstanceState(outSate, outPersistentState);
        Log.d(TAG, "onSaveInstanceSate");
    }
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }
    //保存資料
    private void saveData(){
        //取得偏好設定物件
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        //取得偏好設定編輯物件
        SharedPreferences.Editor editor= prefs.edit();
        //寄放資料包裹
        editor.putInt(BUNDLE_KEY_COLOR_INT, mColorInt);
        editor.putString(BUNDLE_KEY_COLOR_NAME, mColorName.toString());
        //送出資料
        editor.commit();
        Log.d(TAG, "saveData()mColorInt = " + mColorInt + "mColorName = " + mColorName);
    }
    //還原資料
    private void restoreData(){
        //取得偏好設定物件
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        //從包裹取回寄放的資料
        mColorInt = prefs.getInt(BUNDLE_KEY_COLOR_INT, 0);
        mColorName = prefs.getString(BUNDLE_KEY_COLOR_NAME, "holo_red_light");
        Log.d(TAG, "restoreData()mColorInt = " + mColorInt + "mColorName = " + mColorName);
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
