package com.example.student.lab10_activity_and_adapter.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class QuestionList {
    @ElementList
    //欄位 List是一個介面 下載 Question
    private List<Question>  list;

    //getter
    public List<Question> getList(){
        return list;
    }


}
