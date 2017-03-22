package com.example.abass.peoplerestclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.abass.peoplerestclient.network.RestApi;
import com.example.abass.peoplerestclient.network.RestClient;

import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RestApi restApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restApi = RestClient.getInstance().getRestApi();
        makeNetworkCallsInOrder();
    }

    private void makeNetworkCallsInOrder() {
        Person person = new Person();
        person.setName("Sean");
        person.setFavoriteCity("New York");
        restApi.postPerson(person).enqueue(this);
        final RestApi api = this.restApi;
        restApi.getPeople().enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                Log.d(TAG, response.toString());
                if(response.body() == null) {
                    return;
                }
                if(response.body().size() > 0) {
                    Person person = response.body().get(0);
                    person.setFavoriteCity("Brooklyn");
                    api.updatePerson(person.getId(), person);
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        restApi.getPerson(1).enqueue(this);
        restApi.getPeople().enqueue(this);

    }

    @Override
    public void onResponse(Call call, Response response) {
        Log.d(TAG, response.toString());
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}
