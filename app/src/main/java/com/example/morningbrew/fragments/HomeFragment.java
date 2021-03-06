package com.example.morningbrew.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView rvBrews;
    protected BrewAdapter adapter;
    protected List<Brew> allBrews;

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

        allBrews = new ArrayList<>();
        adapter = new BrewAdapter(getContext(), allBrews);
        rvBrews.setAdapter(adapter);
        rvBrews.setLayoutManager(new LinearLayoutManager(getContext()));
        queryBrews();
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
                    //query brews for only the current user, not all users
                    ParseUser user = getCurrentUser();
                    if ( brew.getUser().getUsername().equals(user.getUsername()) ) {
                        allBrews.add(brew);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public ParseUser getCurrentUser() {
        // After login, Parse will cache it on disk, so
        // we don't need to login every time we open this
        // application
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        else {
            return currentUser;
        }
    }

}
