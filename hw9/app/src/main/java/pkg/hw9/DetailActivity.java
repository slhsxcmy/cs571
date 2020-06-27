// https://www.youtube.com/watch?v=bNpWGI_hGGg
package pkg.hw9;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static pkg.hw9.CatalogActivity.EXTRA_ID;
import static pkg.hw9.CatalogActivity.EXTRA_IMAGE_URL;
import static pkg.hw9.MainActivity.DEBUG;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DetailAdapter detailAdapter = new DetailAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(detailAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // Get constants from intent
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        String id = intent.getStringExtra(EXTRA_ID);

        Log.d("TAG", "DetailActivity onCreate ID: " + id);

        // Volley Request
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON_single(id);


        // populate views
//        ImageView imageView = findViewById(R.id.image_view_detail);

//        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);

    }

    private void parseJSON_single(String id) {
        String url;
        if (!DEBUG) {
            url = "https://hw8-server-cs571su2020.wl.r.appspot.com/single/" + id;
        } else {
            url = "http://192.168.1.220:3000/single/" + id;
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            // pass whole JSON to 3 fragments to parse separately
//
//                            String subtitle = response.getString("Subtitle");
                            JSONArray itemSpecifics = response.getJSONArray("ItemSpecifics");


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
}