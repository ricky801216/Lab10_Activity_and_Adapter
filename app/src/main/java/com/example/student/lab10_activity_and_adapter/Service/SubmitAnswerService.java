package com.example.student.lab10_activity_and_adapter.Service;



import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface SubmitAnswerService {
    @POST
    Call<String> send (@Url String url);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://docs.google.com/")
            .addConverterFactory(new ToStringConverterFactory())
            .build();
}
