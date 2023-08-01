package com.example.uas202102266;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MainActivity extends AppCompatActivity {
    private RecyclerView _RecyclerView;
    private SwipeRefreshLayout _SwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _RecyclerView = findViewById(R.id.recyclerView);
        _SwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        initRecyclerView();
        initSwipeRefreshLayout();
    }
    private void initRecyclerView() {
        _SwipeRefreshLayout.setRefreshing(true);
        String url = "https://api.openweathermap.org/data/2.5/forecast?id=1630789&appid=e72ea467a7ed89b95c85923726893d13";
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // Log.d("*tw*", new String(responseBody));
                Gson gson = new Gson();
                CuacaRootModel rootModelCuaca = gson.fromJson(new String(responseBody), CuacaRootModel.class);
                // Log.d("*tw*", rootModel.getListModelList().get(0).getDt_text());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                CuacaAdapter cuacaAdapter = new CuacaAdapter(MainActivity.this, rootModelCuaca);

                _RecyclerView.setLayoutManager(layoutManager);
                _RecyclerView.setAdapter(cuacaAdapter);

                _SwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                _SwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initSwipeRefreshLayout() {
        _SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
            }
        });
    }
}