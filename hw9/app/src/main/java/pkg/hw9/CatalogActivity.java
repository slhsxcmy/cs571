// https://guides.codepath.com/android/using-the-recyclerview
package pkg.hw9;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import static pkg.hw9.MainActivity.DEBUG;
import static pkg.hw9.MainActivity.EXTRA_KEYWORD;
import static pkg.hw9.MainActivity.EXTRA_REQUEST_URL;

public class CatalogActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {
    public static final int MAX_RESULTS = 50;

    public static final String EXTRA_IMAGE_URL = "imageUrl";
    public static final String EXTRA_ID = "itemID";
    public static final String EXTRA_TITLE = "itemTitle";
    public static final String EXTRA_PRICE = "itemPrice";
    public static final String EXTRA_SHIPPING = "shippingCost";
    public static final String EXTRA_SHIPINFO = "shippingInfo";
    public static final String EXTRA_LINK_URL = "linkURL";

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    private TextView resultCount;
    private RelativeLayout progress;

    private SwipeRefreshLayout mSwipeRefreshLayout;

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
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));  // set in xml

// moved to parseJSON() to refresh on pull
//        mExampleList = new ArrayList<>();
//        mExampleAdapter = new ExampleAdapter(CatalogActivity.this, mExampleList);
//        mRecyclerView.setAdapter(mExampleAdapter);
//        mExampleAdapter.setOnItemClickListener(CatalogActivity.this);

        resultCount = findViewById(R.id.result_count);
        progress = findViewById(R.id.catalog_progress);
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh_items);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Note: pull to refresh on no records screen not possible(easy)
//                SwipeRefreshLayout can only have 1 single Recycler View as child
                parseJSON();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        // added to delete previous results on refresh

        mExampleList = new ArrayList<>();
        mExampleAdapter = new ExampleAdapter(CatalogActivity.this, mExampleList);
        mRecyclerView.setAdapter(mExampleAdapter);
        mExampleAdapter.setOnItemClickListener(CatalogActivity.this);

//        Log.d("TAG", "parseJSON: START!!!!");

//        progress.setVisibility(View.VISIBLE);  // removed to hide progress spinner on pull

        String url;
        final String keyword;
//        if (!DEBUG) {
        Intent intent = getIntent();
        url = intent.getStringExtra(EXTRA_REQUEST_URL);
        keyword = intent.getStringExtra(EXTRA_KEYWORD);
//        } else {
//            url = "http://192.168.1.220:3000/query?keywords=iphone&sortOrder=BestMatch";
//            keyword = "DEBUG_KEYWORD";
//        }
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

                            //  if no results, toast.
                            if (returnedResults == 0) {
                                findViewById(R.id.no_records).setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "No Records", Toast.LENGTH_SHORT).show();
                            }

                            resultCount.setText(Html.fromHtml("<b>Showing <font color='#187bcd'>" + Math.min(returnedResults, MAX_RESULTS) + "</font> results for <font color='#187bcd'>" + keyword + "</font></b>"));
                            Log.d("TAG", "onResponse:returnedResults: " + returnedResults + " -- Showing " + Math.min(returnedResults, MAX_RESULTS) + " results for iphone");

                            JSONArray jsonArray = response.getJSONArray("searchResult");
                            Log.d("TAG", "onResponse: searchResult length: " + jsonArray.length());

                            for (int i = 0; i < Math.min(jsonArray.length(), MAX_RESULTS); i++) {
//                                Log.d("TAG", "onResponse: " + i);
                                JSONObject item = jsonArray.getJSONObject(i);
//                                Log.d("TAG", "onResponse: item: "+ item.toString(2));

                                String imageUrl = item.getString("galleryURL");

//                                Log.d("TAG------------------", "onResponse: imageUrl: " + imageUrl);
//
                                String title = item.getString("title");
                                String shipping = item.getString("shippingServiceCost");
                                String top = item.getString("topRatedListing");
                                String condition = item.getString("condition");
                                String price = item.getString("price");
                                String id = item.getString("itemId");
                                String shippingInfo = item.getJSONObject("shippingInfo").toString();
                                String link = item.getString("viewItemURL");

                                mExampleList.add(new ExampleItem(imageUrl, title, shipping, top, condition, price, id, shippingInfo, link));

                                // notify adapter
                                mExampleAdapter.notifyItemInserted(i);
                            }

                            // add divider
                            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(),
                                    DividerItemDecoration.HORIZONTAL));
                            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(),
                                    DividerItemDecoration.VERTICAL));

                            // finished parsing JSON, hide progress bar
                            progress.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            Log.d("TAG", "onResponse: JSONException!");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: VolleyError!A");
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);

//        detailIntent.putExtra(EXTRA_IMAGE_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_ID, clickedItem.getID());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_PRICE, clickedItem.getPrice());
        detailIntent.putExtra(EXTRA_SHIPPING, clickedItem.getShipping());
        detailIntent.putExtra(EXTRA_SHIPINFO, clickedItem.getShippingInfo());
        detailIntent.putExtra(EXTRA_LINK_URL, clickedItem.getLink());


        startActivity(detailIntent);
    }
}
