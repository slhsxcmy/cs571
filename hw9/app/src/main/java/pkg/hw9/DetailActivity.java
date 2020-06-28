// https://www.youtube.com/watch?v=bNpWGI_hGGg
package pkg.hw9;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import static pkg.hw9.CatalogActivity.EXTRA_SHIPINFO;
import static pkg.hw9.MainActivity.DEBUG;

public class DetailActivity extends AppCompatActivity {
    public static final String BUNDLE_SHIPINFO = "shippingInfoDetail";

    private RequestQueue mRequestQueue;
    private DetailAdapter mDetailAdapter;
    private ViewPager mViewPager;
    private TabLayout tabs;
    private RelativeLayout progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progress = findViewById(R.id.detail_progress);
        mViewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);

//        mDetailAdapter = new DetailAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(mDetailAdapter);  // set this after volley json is received? to initialize fragments with data
//
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // Get constants from intent
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        String id = intent.getStringExtra(EXTRA_ID);
        String shippingInfo = intent.getStringExtra(EXTRA_SHIPINFO);

        Log.d("TAG", "DetailActivity onCreate ID: " + id);

        // Volley Request
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON_single(id);


        // populate views
        ShippingFragment shippingFragment = new ShippingFragment();

        Bundle shippingBundle = new Bundle();
        shippingBundle.putString(BUNDLE_SHIPINFO, shippingInfo);
        shippingFragment.setArguments(shippingBundle);

        mDetailAdapter = new DetailAdapter(getSupportFragmentManager(), shippingBundle);
        mViewPager.setAdapter(mDetailAdapter);

        tabs.setupWithViewPager(mViewPager);



        tabs.getTabAt(0).setIcon(R.drawable.information_variant_selected);
        tabs.getTabAt(1).setIcon(R.drawable.ic_seller);
        tabs.getTabAt(2).setIcon(R.drawable.truck_delivery_selected);
        tabs.getTabAt(0).setText(getResources().getText(R.string.tab_text_1));
        tabs.getTabAt(1).setText(getResources().getText(R.string.tab_text_2));
        tabs.getTabAt(2).setText(getResources().getText(R.string.tab_text_3));
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.shipping_container, shippingFragment);
//        transaction.commit();


//        viewPager.get
    }

    private void parseJSON_single(String id) {
        progress.setVisibility(View.VISIBLE);

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
}