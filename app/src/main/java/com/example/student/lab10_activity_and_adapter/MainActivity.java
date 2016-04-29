package com.example.student.lab10_activity_and_adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_COLOR_REQEST = 0;
    private static final int EDIT_TEXT_REQEST = 1;

    private int m_color = 0xFFFFFFFF; // 紀錄選了什麼顏色
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void next(View view){
        Intent intent = new Intent(this, Activity1.class);
        intent.putExtra(ColorPickerActivity.BUNDLE_KEY_COLOR_INT, m_color);
        startActivity(intent);

    }

    public void selectColor(View view){
        //透過 傳令兵(Intent) 傳令自身 Activity 和 ColorActivity
        Intent intent = new Intent(this, ColorPickerActivity.class);
//      startActivity(intent);//開始傳令
        startActivityForResult(intent, SELECT_COLOR_REQEST);//傳令返回所需 結果
    }

    public void editText(View view){
        //建立傳令兵
        //new Intent ( 發送者, 接收者 )
        Intent intent = new Intent(this, EditTextActivity.class);
        //啟動另一個 Activity ， 設定 傳令兵 與 請求碼
        startActivityForResult(intent, EDIT_TEXT_REQEST);
    }

    //取得Result(傳令兵-Intent 身上的 包裹-Bundle)
    //將顏色相關設定到TextView
    //Android 系統 好來烏模式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果回傳包裹 與 當初定義相同，就是我們的結果
        if(requestCode == SELECT_COLOR_REQEST){
            if(resultCode == RESULT_OK){
                //取得傳令兵包裹
                Bundle bundle = data.getExtras();
                //從包裹取出值
                int colorInt = bundle.getInt(ColorPickerActivity.BUNDLE_KEY_COLOR_INT);
                CharSequence colorName = bundle.getCharSequence(ColorPickerActivity.BUNDLE_KEY_COLOR_NAME);
                //將Color 相關資訊定到 TextView
                TextView tv_color = (TextView)findViewById(R.id.tv_color);
                tv_color.setGravity(Gravity.CENTER);
                tv_color.setText(colorName);

                //變更 ScrollView 背景顏色
                ScrollView scrollView = (ScrollView)findViewById(R.id.scroll_view);
                scrollView.setBackgroundColor(colorInt);
            }
        }else if(requestCode == EDIT_TEXT_REQEST){
            if (resultCode == RESULT_OK){
                //取得傳令包裹
                Bundle bundle = data.getExtras();
                //從包裹拿出 CharSequence 資料，須提供資料　key
                CharSequence text = bundle.getCharSequence(EditTextActivity.BUNDLE_KEY_TEXT);
                //變更 TextView 中的文字
                TextView tv_text = (TextView)findViewById(R.id.tv_color);
                tv_text.setGravity(Gravity.LEFT);//文字靠左對齊
                tv_text.setText(text);
            }
        }
    }




}

