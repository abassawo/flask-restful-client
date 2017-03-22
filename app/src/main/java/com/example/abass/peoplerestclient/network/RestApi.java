package com.example.abass.peoplerestclient.network;


import com.example.abass.peoplerestclient.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestApi {

    @GET("/api/v1.0/people")
    Call<List<Person>> getPeople();

    @GET("/api/v1.0/people/{id}")
    Call<Person> getPerson(@Path("id") Integer id);

    @GET("/api/v1.0/people/{id}")
    Call<Person> deletePerson(@Path("id") Integer id);

    @POST("/api/v1.0/people")
    Call<List<Person>> postPerson(@Body Person person);

    @PUT("/api/v1.0/people{id}")
    Call<Person> updatePerson(@Path("id") Integer id, @Body Person person);
}
