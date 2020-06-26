package pkg.hw9;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static pkg.hw9.MainActivity.EXTRA_KEYWORD;
import static pkg.hw9.MainActivity.EXTRA_REQUEST_URL;

public class CatalogActivity extends AppCompatActivity {
    public static final int MAX_RESULTS = 50;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    private TextView resultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mExampleList = new ArrayList<>();

        resultCount = findViewById(R.id.result_count);

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(EXTRA_REQUEST_URL);
        final String keyword = intent.getStringExtra(EXTRA_KEYWORD);
        Log.d("TAG", "parseJSON get url: " + url);

//        url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("TAG", "onResponse: GOT JSON");
//                            JSONArray jsonArray = response.getJSONArray("hits");
//                            JSONArray jsonArray = response.getJSONArray("searchResult");
                            int returnedResults = response.getInt("returnedResults");
                            resultCount.setText("Showing " + Math.min(returnedResults, MAX_RESULTS) + " results for " + keyword);
                            Log.d("TAG", "onResponse:returnedResults: " + returnedResults + " -- Showing " + Math.min(returnedResults, MAX_RESULTS) + " results for iphone");

                            JSONArray jsonArray = response.getJSONArray("searchResult");
                            Log.d("TAG", "onResponse: searchResult length: " + jsonArray.length());

//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject hit = jsonArray.getJSONObject(i);

//                                String imageUrl = hit.getString("galleryURL");
//
//                                String title = hit.getString("title");
//                                String shipping = hit.getString("shippingServiceCost");
//
//                                String condition = hit.getString("condition");
//                                String price = hit.getString("price");
//
//                                Log.d("TAG", "onResponse: " + imageUrl + " " + title + " " + shipping + "  " + condition + " " + price);

//                                mExampleList.add(new ExampleItem(imageUrl, title, shipping, condition, price));
//                            }

                            mExampleAdapter = new ExampleAdapter(CatalogActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }
}