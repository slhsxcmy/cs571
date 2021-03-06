// https://www.youtube.com/watch?v=bNpWGI_hGGg
package pkg.hw9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static pkg.hw9.CatalogActivity.EXTRA_ID;
import static pkg.hw9.CatalogActivity.EXTRA_LINK_URL;
import static pkg.hw9.CatalogActivity.EXTRA_PRICE;
import static pkg.hw9.CatalogActivity.EXTRA_SHIPINFO;
import static pkg.hw9.CatalogActivity.EXTRA_SHIPPING;
import static pkg.hw9.CatalogActivity.EXTRA_TITLE;
import static pkg.hw9.MainActivity.DEBUG;

public class DetailActivity extends AppCompatActivity {
    public static final String BUNDLE_TITLE = "titleDetail";
    public static final String BUNDLE_PRICE = "priceDetail";
    public static final String BUNDLE_SHIPPING = "shippingCostDetail";
    public static final String BUNDLE_SHIPINFO = "shippingInfoDetail";
    public static final String BUNDLE_SUBTITLE = "subtitleDetail";
    public static final String BUNDLE_BRAND = "brandDetail";
    public static final String BUNDLE_ITEMSPEC = "specsDetail";
    public static final String BUNDLE_SELLER = "sellerDetail";
    public static final String BUNDLE_RETURN = "returnDetail";
    public static final String BUNDLE_PICS = "picturesDetail";

    private RequestQueue mRequestQueue;
    private DetailAdapter mDetailAdapter;
    private ViewPager mViewPager;
    private TabLayout tabs;
    private RelativeLayout progress;

    private Bundle mBundle;

    private SummaryFragment mSummaryFragment;
    private SellerFragment mSellerFragment;
    private ShippingFragment mShippingFragment;

    private Button redirect_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progress = findViewById(R.id.detail_progress);
        mViewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);

//        mDetailAdapter = new DetailAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(mDetailAdapter);  // set this after volley json is received? to initialize fragments with data

        // Get constants from intent
        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_ID);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String price = intent.getStringExtra(EXTRA_PRICE);
        String shipping = intent.getStringExtra(EXTRA_SHIPPING);
        String shippingInfo = intent.getStringExtra(EXTRA_SHIPINFO);
        final String link;
        if (intent.getStringExtra(EXTRA_LINK_URL) == null || intent.getStringExtra(EXTRA_LINK_URL).isEmpty()) {
            link = "https://ebay.com/";
        } else {
            link = intent.getStringExtra(EXTRA_LINK_URL);
        }

        redirect_button = findViewById(R.id.redirect_button);
        redirect_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        });

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        Log.d("TAG", "DetailActivity onCreate ID: " + id);

        // Volley Request
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON_single(id);

        // populate views
        mBundle = new Bundle();
        mBundle.putString(BUNDLE_TITLE, title);
        mBundle.putString(BUNDLE_PRICE, price);
        mBundle.putString(BUNDLE_SHIPPING, shipping);
        mBundle.putString(BUNDLE_SHIPINFO, shippingInfo);


        //https://stackoverflow.com/questions/20958733/load-all-fragments-on-app-opening
        // use a number higher than half your fragments.
        mViewPager.setOffscreenPageLimit(3);
        mDetailAdapter = new DetailAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mDetailAdapter);

        tabs.setupWithViewPager(mViewPager);  // overwrites icon and text in TabItem XML
        tabs.getTabAt(0).setIcon(R.drawable.information_variant_selected);
        tabs.getTabAt(1).setIcon(R.drawable.ic_seller);
        tabs.getTabAt(2).setIcon(R.drawable.truck_delivery_selected);
        tabs.getTabAt(0).setText(getResources().getText(R.string.tab_text_1));
        tabs.getTabAt(1).setText(getResources().getText(R.string.tab_text_2));
        tabs.getTabAt(2).setText(getResources().getText(R.string.tab_text_3));


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
//                           parse json and add to bundle

                            if (response.has("Subtitle")) {
                                mBundle.putString(BUNDLE_SUBTITLE, response.getString("Subtitle"));
                            }

                            if (response.has("Brand")) {
                                mBundle.putString(BUNDLE_BRAND, response.getString("Brand"));
                            }

                            mBundle.putString(BUNDLE_ITEMSPEC, response.getJSONArray("ItemSpecifics").toString());
                            mBundle.putString(BUNDLE_SELLER, response.getJSONObject("Seller").toString());
                            mBundle.putString(BUNDLE_RETURN, response.getJSONObject("ReturnPolicy").toString());
                            mBundle.putString(BUNDLE_PICS, response.getJSONArray("PictureURL").toString());

                            Log.d("TAG", "onResponse: mSummaryFragment: " + mSummaryFragment);
                            Log.d("TAG", "onResponse: mSellerFragment: " + mSellerFragment);
                            Log.d("TAG", "onResponse: mShippingFragment: " + mShippingFragment);

                            mSummaryFragment.updateSummary(mBundle);
                            mSellerFragment.updateSeller(mBundle);
                            mShippingFragment.updateShipping(mBundle);

//                            https://stackoverflow.com/questions/44960380/send-data-from-activity-to-fragment-already-created
//                            mDetailAdapter.notifyDataSetChanged();
                            Log.d("TAG", "onResponse: Seller: " + mBundle.getString(BUNDLE_SELLER));

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


    public void setSummaryFragment(SummaryFragment summaryFragment) {
        mSummaryFragment = summaryFragment;
    }

    public void setSellerFragment(SellerFragment sellerFragment) {
        mSellerFragment = sellerFragment;
    }

    public void setShippingFragment(ShippingFragment shippingFragment) {
        mShippingFragment = shippingFragment;
    }

}