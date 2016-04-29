package com.example.student.lab10_activity_and_adapter;

import com.example.student.lab10_activity_and_adapter.Adapter.QuestionActivity;

public class Activity2 extends QuestionActivity {

    @Override
    public Class getBackActivityClass() {
        return Activity1.class;//回傳上一頁 Activity1
    }

    @Override
    public Class getNextActivityClass() {
        return Activity3.class;//回傳下一個 Activity2
    }

    @Override
    public int getBackButtonVisibility() {
        return QuestionActivity.VISIBLE;
    }

    @Override
    public int getNextButtonVisibility() {
        return QuestionActivity.VISIBLE;
    }
}

