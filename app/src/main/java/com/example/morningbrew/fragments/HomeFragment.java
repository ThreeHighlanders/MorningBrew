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

import com.example.morningbrew.Brew;
import com.example.morningbrew.BrewAdapter;
import com.example.morningbrew.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

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

        allBrews = new ArrayList<>();
        adapter = new BrewAdapter(getContext(), allBrews);
        rvBrews.setAdapter(adapter);
        rvBrews.setLayoutManager(new LinearLayoutManager(getContext()));
        queryBrews();
    }

    private void queryBrews() {
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
