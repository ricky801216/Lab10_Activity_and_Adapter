package com.example.student.lab10_activity_and_adapter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.student.lab10_activity_and_adapter.App.MyApp;
import com.example.student.lab10_activity_and_adapter.R;
import com.example.student.lab10_activity_and_adapter.model.UserAnswer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// abstract activity 不需要宣告在 manifest
public abstract class QuestionActivity extends AppCompatActivity
    implements QuestionAdapterFactory.Receiver{
    public static final String FILENAME = "QuestionActivity_UserAnswers";
    private static final String TAG = "QuestionActivity";
    private TextView m_tv_no;
    private TextView m_tv_question;
    private RadioButton m_radio_a;
    private RadioButton m_radio_b;
    private RadioButton m_radio_c;

    private Button m_btn_back;
    private Button m_btn_next;

    private static int sLastQuestionIndex;   // 上個畫面的 index
    private static int sQuestionIndex = 0;   // 只需要一個 index，所以宣告為靜態
    private static QuestionAdapter sAdapter; // 只需要一個 adapter，所以宣告為靜態

    private static UserAnswer sUserAnswer;

    public static UserAnswer getsUserAnswer() {
        return sUserAnswer;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initQuestions();
        initBackNextButtons();
        Log.d(this.toString(), "onCreate , index = " + sQuestionIndex);
    }

    public void onSaveInstanceState(Bundle outSate, PersistableBundle outPersistentState){
        super.onSaveInstanceState(outSate, outPersistentState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void saveData(){
        Log.d(TAG, "saveData() serialize");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            //openFileOutput() 繼承自 Context , Activity 繼承自 Context
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            //儲存 Activity 中的欄位資訊 (非 UI 部分)
            oos.writeInt(sQuestionIndex);
            oos.writeInt(sLastQuestionIndex);
            oos.writeObject(sUserAnswer);

        }catch (java.io.IOException e){
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }finally {
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e){
                    Log.e(TAG, e.toString());
                }
            }
        }
    }
    private void restoreData(){
        Log.d(TAG, "restoreData() deserialize");
        FileInputStream fis = null;
        ObjectInput ois = null;
        try {
            //openFileOutput() 繼承自 Context , Activity 繼承自 Context
            fis = openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);
            //儲存 Activity 中的欄位資訊 (非 UI 部分)
            sQuestionIndex = ois.readInt();
            sLastQuestionIndex = ois.readInt();
            sUserAnswer = (UserAnswer) ois.readObject();
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }finally {
            if(ois != null){
                try {
                    ois.close();
                }catch (IOException e){
                    Log.e(TAG, e.toString());
                }
            }
        }
    }

    // 抑制 setVisibility() 錯誤訊息
    @SuppressWarnings("ResourceType")
    private void initBackNextButtons() {
        m_btn_back = (Button)findViewById(R.id.btn_back);
        m_btn_next = (Button)findViewById(R.id.btn_next);

        // 設定 Back Next 按鈕是否能看見
        // Visibility 只能設定為 View.VISIBLE / View.INVISIBLE / View.GONE
        m_btn_back.setVisibility(getBackButtonVisibility());
        m_btn_next.setVisibility(getNextButtonVisibility());
    }

    private void initQuestions() {
        m_tv_no = (TextView) findViewById(R.id.tv_no);
        m_tv_question = (TextView) findViewById(R.id.tv_question);
        m_radio_a = (RadioButton) findViewById(R.id.radio_a);
        m_radio_b = (RadioButton) findViewById(R.id.radio_b);
        m_radio_c = (RadioButton) findViewById(R.id.radio_c);

        // 題號
        String no = String.valueOf(sQuestionIndex + 1);
        m_tv_no.setText(no);

        // 題目 與 選項
        if (sAdapter == null) {
//            sAdapter = QuestionAdapterFactory.getQuestionAdapter();
            //Factory 無法立即回傳 Adapter 所以需要傳入一個實作 Receiver 的物件
            QuestionAdapterFactory.getQuestionAdapter(this);//Activity自己(this)實作了 Receiver

        }
        updateQuestionText();
    }

        //實現 QuestionAdapterFactory.Receiver能接收 adapter
        public void receiveQuestionAdapter(QuestionAdapter adapter){
        Log.d(TAG, "receiveQuestionAdapter");
            MyApp.setAdapter(adapter);//避免將來重複下載 寄放在MyApp (將來要在 MyApp實作)
            sAdapter = adapter;
            if(sUserAnswer == null){
                sUserAnswer = new UserAnswer(sAdapter.getQuestionCount());
            }
        updateQuestionText(); //畫面顯示題目資訊
    }
    private void updateQuestionText(){
        if(sAdapter == null){return;}
        findViewById(R.id.pgb_loading).setVisibility(View.GONE);
        m_tv_question.setText(Html.fromHtml(sAdapter.getQuestion(sQuestionIndex).toString()));
        m_radio_a.setText(Html.fromHtml(sAdapter.getQuestionOptionsA(sQuestionIndex).toString()));
        m_radio_b.setText(Html.fromHtml(sAdapter.getQuestionOptionsB(sQuestionIndex).toString()));
        m_radio_c.setText(Html.fromHtml(sAdapter.getQuestionOptionsC(sQuestionIndex).toString()));
    }

    // 按下 BACK
    public void back(View view) {
        sLastQuestionIndex = sQuestionIndex; // 準備要切換到前一個 Activity，備份目前 index
        sQuestionIndex--;  // 1
        // 建立新 Intent: new Intent( 來源 , 目的)
        Intent intent = new Intent(this, getBackActivityClass());
        // 將先前的 Acvivity 移到最上層，而非產生新的 Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        // overridePendingTransition( 進場效果 , 出場效果 )
        // overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    // 按下 NEXT
    public void next(View view) {
        if(sQuestionIndex == 3){
            sQuestionIndex = 0;
        }else {
            sLastQuestionIndex = sQuestionIndex;
            sQuestionIndex++;
        }
        // 建立新 Intent: new Intent( 來源 , 目的)
        Intent intent = new Intent(this, getNextActivityClass());
        // 將先前的 Acvivity 移到最上層，而非產生新的 Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);


        // overridePendingTransition( 進場效果 , 出場效果 )
        // overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    // 設定 Back 按鈕文字
    public void setBackButtonText(CharSequence text) {
        m_btn_back.setText(text);
    }

    // 設定 Next 按鈕文字
    public void setNextButtonText(CharSequence text) {
        m_btn_next.setText(text);
    }

    // 按下 RadioButton 按鈕
    public void click(View view) {
        //按下旋鈕時
        RadioButton radio = (RadioButton)view;
        //透過 MyApp 取得 UserAnswer
        //UserAnswer userAnswer = MyApp.getUserAnswer();現在 Activity 自己管理sUserAnswer
        UserAnswer userAnswer = sUserAnswer;
        switch (view.getId()){
            case R.id.radio_a:
                userAnswer.setAnswer(sQuestionIndex, 'A', radio.getText());
                Log.d("QuestionActivity", "questionIndex=" + sLastQuestionIndex + "選了 A");
                break;
            case R.id.radio_b:
                userAnswer.setAnswer(sQuestionIndex, 'B', radio.getText());
                Log.d("QuestionActivity", "questionIndex=" + sLastQuestionIndex + "選了 B");
                break;
            case R.id.radio_c:
                userAnswer.setAnswer(sQuestionIndex, 'C', radio.getText());
                Log.d("QuestionActivity", "questionIndex=" + sLastQuestionIndex + "選了 C");
                break;
        }
    }
    //索引編號歸零
    public static void resetQuestionIndex(){
        sQuestionIndex = 0;
        sLastQuestionIndex = 0;
    }

    @Override
    public void onBackPressed() { // 當按下返回鍵
        return; // 直接返回不處理
    }

    @Override
    protected void onPause() { // 當畫面暫時離開
        super.onPause();
        Log.d(this.toString(), "onPause , index = " + sQuestionIndex);
        saveData();
    }

    @Override
    protected void onResume() { // 當畫面恢復(重現)，執行轉場動畫
        super.onResume();
        Log.d(this.toString(), "onResume , index = " + sQuestionIndex);

        restoreData();
        initQuestions();
        initBackNextButtons();
        if(sQuestionIndex < sLastQuestionIndex) { // 比較當前的 index 與 上個 Activity 的 index
            overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
        } else if(sQuestionIndex > sLastQuestionIndex) {
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(this.toString(), "onDestroy , index = " + sQuestionIndex);
    }

    // 子類別實現以下功能
    protected abstract Class getBackActivityClass(); // 切換下個畫面的 Activity.class
    protected abstract Class getNextActivityClass(); // 切換上個畫面的 Activity.class
    protected abstract @Visibility int getBackButtonVisibility(); // Back 按鈕是否能被看見
    protected abstract @Visibility int getNextButtonVisibility(); // Next 按鈕是否能被看見

    // @Visibility 返回的 int 只能是 View.VISIBLE View.INVISIBLE View.GONE 其中之一
    public static final int VISIBLE = View.VISIBLE;
    public static final int INVISIBLE = View.INVISIBLE;
    public static final int GONE = View.GONE;

    @IntDef({VISIBLE, INVISIBLE, GONE})
    public @interface Visibility {
    }


}

