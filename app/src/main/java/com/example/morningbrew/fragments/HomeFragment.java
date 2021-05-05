package com.example.morningbrew.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.morningbrew.Brew;
import com.example.morningbrew.BrewAdapter;
import com.example.morningbrew.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    public static String API_URL= "http://api.openweathermap.org/data/2.5/weather?zip=08817,us&appid=d162c47b7d6374a9a98b555ade89ad29&units=imperial";
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView rvBrews;
    protected BrewAdapter adapter;
    protected List<Brew> allBrews;
    private int low;
    private int high;
    private String desc;

    public HomeFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvBrews = view.findViewById(R.id.rvBrews);
        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "fetching data");
                allBrews = new ArrayList<>();
                adapter = new BrewAdapter(getContext(), allBrews);
                rvBrews.setAdapter(adapter);
                rvBrews.setLayoutManager(new LinearLayoutManager(getContext()));
                queryBrews();
                swipeContainer.setRefreshing(false);
            }
        });

        AsyncHttpClient client= new AsyncHttpClient();
        client.get(API_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i(TAG,"onSuccess");
                JSONObject jsonObject= json.jsonObject;
                try {
                    JSONObject main = jsonObject.getJSONObject("main");
                    low= main.getInt("temp_min");
                    high= main.getInt("temp_max");
                    JSONArray weather= jsonObject.getJSONArray("weather");
                    JSONObject jsonObject1= weather.getJSONObject(0);
                    desc= jsonObject1.getString("description");
                    Log.i(TAG,desc+" "+low+" "+high+" "+main.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG,"onFailure",throwable);
            }
        });

        allBrews = new ArrayList<>();
        adapter = new BrewAdapter(getContext(), allBrews);
        rvBrews.setAdapter(adapter);
        rvBrews.setLayoutManager(new LinearLayoutManager(getContext()));
        queryBrews();
    }

    protected void queryBrews() {
        // Specify which class to query
        ParseQuery<Brew> query = ParseQuery.getQuery(Brew.class);
        query.include(Brew.USER);
        query.addDescendingOrder(Brew.CREATE_KEY);
        Log.i(TAG, "queryBrews");
        query.findInBackground(new FindCallback<Brew>() {
            @Override
            public void done(List<Brew> brews, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting brews", e);
                    return;
                }
                for (Brew brew: brews) {
                    Log.i(TAG, "Brew: " + brew.getDescription() + ", username: " + brew.getUser().getUsername());
                }
                Log.i(TAG, "queryBrews2");
                allBrews.addAll(brews);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
