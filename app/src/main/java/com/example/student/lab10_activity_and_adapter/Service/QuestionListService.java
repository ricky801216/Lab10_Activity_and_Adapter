package com.example.student.lab10_activity_and_adapter.Service;


import com.example.student.lab10_activity_and_adapter.model.QuestionList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public interface QuestionListService {
    //宣告抽象方方法 public abstact 可省略
    @GET("/uc?export=download&id=0B0vSBNwQqu77WVRHZ05oVWg4T0U")
    Call<QuestionList>getQuestionList();//getQuestionList() 可自行命名

    //宣告 final 靜態欄位 (介面的欄位必定是 public static final , 可省略)
    Retrofit retrofit = new Retrofit.Builder() // 打造一個Retrofit 物件, 欄位retrofit可自行命名
    .baseUrl("https://drive.google.com/")
            .addConverterFactory(SimpleXmlConverterFactory.create())//將獨到的資料交給 SimpleXml 解析
            .build();
}
