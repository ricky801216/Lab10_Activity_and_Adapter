package com.example.student.lab10_activity_and_adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditTextActivity extends AppCompatActivity {

    //key 值的最佳實踐方式 以 App 的 package 作為前綴詞
    public static final String BUNDLE_KEY_TEXT = "com.student.android.text";//包裹中資料的 key
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
    }
    public void ok(View view){
        //取得文字
        EditText editText = (EditText)findViewById(R.id.ed_text);
        CharSequence text = editText.getText();
        //建立傳令兵
        Intent intent = new Intent();
        //將輸入文字的結果放進傳令兵包裹裡
        intent.putExtra(BUNDLE_KEY_TEXT, text);
        //傳送結果 OK
        setResult(RESULT_OK, intent);
        //結束目前 Activity
        finish();
    }
    public void cancel(View view){
        setResult(RESULT_CANCELED);//傳送結果取消 因此包裹沒放資料
        finish();//結束目前 Activity
    }
}
