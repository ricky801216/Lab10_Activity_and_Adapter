package com.example.student.lab10_activity_and_adapter;

import com.example.student.lab10_activity_and_adapter.Adapter.QuestionActivity;

public class Activity1 extends QuestionActivity {


    @Override
    public Class getBackActivityClass() {
        return null;//回傳 null
    }

    @Override
    public Class getNextActivityClass() {
        return Activity2.class;
    }

    @Override
    public int getBackButtonVisibility() {
        return QuestionActivity.GONE;//消失不占空間
    }

    @Override
    public int getNextButtonVisibility() {
        return QuestionActivity.VISIBLE;
    }
}
