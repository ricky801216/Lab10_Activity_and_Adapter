package com.example.student.lab10_activity_and_adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.example.student.lab10_activity_and_adapter.Adapter.QuestionActivity;
import com.example.student.lab10_activity_and_adapter.Adapter.QuestionAdapter;
import com.example.student.lab10_activity_and_adapter.Adapter.QuestionAdapterFactory;
import com.example.student.lab10_activity_and_adapter.Service.SubmitAnswerService;
import com.example.student.lab10_activity_and_adapter.model.UserAnswer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity3 extends QuestionActivity {
        public void onStart(){
            super.onStart();
            setNextButtonText("Finish");// 呼叫父類寫好的功能
        }

    public void next(View view){
        QuestionAdapter adapter = QuestionAdapterFactory.getQuestionAdapter();
        final UserAnswer userAnswer = QuestionActivity.getsUserAnswer();
        StringBuilder message = new StringBuilder();

        message.append("您的作答如下\n\n");
        for (int i = 0 ; i< adapter.getQuestionCount() ; i++){
            message.append(String.valueOf(i+1))
                    .append(": ")
                    .append(userAnswer.getAnswer(i))
                    .append("\n")
                    .append(userAnswer.getDescription(i))
                    .append("\n");
        }
        message.append("\n確定要結束 ?");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Activity3.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //提交答案至 Google 表單
             SubmitAnswerService  service = SubmitAnswerService.retrofit
                     .create(SubmitAnswerService.class);
                        char q1Ans = userAnswer.getAnswer(0);//使用者第一題答案
                        char q2Ans = userAnswer.getAnswer(1);//使用者第一題答案
                        char q3Ans = userAnswer.getAnswer(2);//使用者第一題答案
                        String url =
                         "/forms/d/1CmzVy2eR17nF8pAqHfvCv0uSGGEU9mTogkzHamEt998/formResponse?ifq&entry.487289351=" + q1Ans +"&entry" +
                                 ".1085064373="+ q2Ans +"&entry.1414453613="+ q3Ans +"&submit=Submit\"";
                        Call<String> call = service.send(url);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.isSuccessful()){
                                        Log.d("Retrofit", "onResponse() success");
                                        Log.d("Retrofit Response" , response.body());
                                    }else {
                                        Log.d("Retrofit", "onResponse() : error response, no access to response ?");
                                        Log.d("Retrofit", "response code = " + response.code());
                                    }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("Retrofit", "onFailure() :" + t.toString());
                            }
                        });
                        startActivity(intent);//回主畫面
                        QuestionActivity.resetQuestionIndex();//索引編號歸零

                    }
                }).setNegativeButton("NO",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .show();


    }


    @Override
    public Class getBackActivityClass() {
        return Activity2.class;
    }

    @Override
    public Class getNextActivityClass() {
        return null;
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
