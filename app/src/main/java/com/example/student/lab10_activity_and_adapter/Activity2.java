package com.example.student.lab10_activity_and_adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    public static final String Q2_ANSWER_KEY = "Q2";

    private TextView m_tv_no;
    private TextView m_tv_question;
    private RadioButton m_radio_a;
    private RadioButton m_radio_b;
    private RadioButton m_radio_c;
    private CharSequence m_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        init();
    }
    public void init(){
        m_tv_no = (TextView)findViewById(R.id.tv_no);
        m_tv_question = (TextView)findViewById(R.id.tv_question);
        m_radio_a = (RadioButton)findViewById(R.id.radio_a);
        m_radio_b = (RadioButton)findViewById(R.id.radio_b);
        m_radio_c = (RadioButton)findViewById(R.id.radio_c);

        m_tv_no.setText("2");
        m_tv_question.setText(Html.fromHtml(getString(R.string.question_2)));
        m_radio_a.setText(Html.fromHtml(getString(R.string.question_2_a)));
        m_radio_b.setText(Html.fromHtml(getString(R.string.question_2_b)));
        m_radio_c.setText(Html.fromHtml(getString(R.string.question_2_c)));

    }
    public void next(View view){
        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra(Q2_ANSWER_KEY, m_answer);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);

    }
    public void back(View view){
        finish();
    }
    public void click(View view){
        m_answer = view.getTag().toString();
    }
}
