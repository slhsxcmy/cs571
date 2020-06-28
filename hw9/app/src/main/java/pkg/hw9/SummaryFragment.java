package pkg.hw9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import static pkg.hw9.DetailActivity.BUNDLE_BRAND;
import static pkg.hw9.DetailActivity.BUNDLE_ITEMSPEC;
import static pkg.hw9.DetailActivity.BUNDLE_PICS;
import static pkg.hw9.DetailActivity.BUNDLE_PRICE;
import static pkg.hw9.DetailActivity.BUNDLE_SHIPPING;
import static pkg.hw9.DetailActivity.BUNDLE_SUBTITLE;
import static pkg.hw9.DetailActivity.BUNDLE_TITLE;

public class SummaryFragment extends Fragment {
    private View mView;
    private String mTitle;
    private String mPrice;
    private String mShipping;
    private String mPicturesURL;
    private String mSubtitle = "";
    private String mBrand = "";
    private String mSpecs;

    public SummaryFragment() {
        // Required empty public constructor
    }

    public void updateSummary(Bundle args) {
        TextView titleView = mView.findViewById(R.id.text_view_title);
        TextView priceView = mView.findViewById(R.id.text_view_price);
        TextView shippingView = mView.findViewById(R.id.text_view_shipping);
        TextView subtitleView = mView.findViewById(R.id.subtitle);
        TextView brandView = mView.findViewById(R.id.brand);
        TextView specView = mView.findViewById(R.id.specs_string);

        mTitle = args.getString(BUNDLE_TITLE);
        mPrice = args.getString(BUNDLE_PRICE);
        mShipping = args.getString(BUNDLE_SHIPPING);
        mPicturesURL = args.getString(BUNDLE_PICS);
        if (args.containsKey(BUNDLE_SUBTITLE)) mSubtitle = args.getString(BUNDLE_SUBTITLE);
        if (args.containsKey(BUNDLE_BRAND)) mBrand = args.getString(BUNDLE_BRAND);
        mSpecs = args.getString(BUNDLE_ITEMSPEC);

        titleView.setText(mTitle);
        priceView.setText(mPrice);

        if (Double.valueOf(mShipping) > 0) {
            shippingView.setText("Ships for " + mShipping);
        } else {
            shippingView.setText("Free Shipping");
        }

        if (mSubtitle.isEmpty() && mBrand.isEmpty()) {
            mView.findViewById(R.id.features_layout).setVisibility(View.GONE);
        } else {
            mView.findViewById(R.id.features_layout).setVisibility(View.VISIBLE);
        }

        if (mSubtitle.isEmpty()) {
            mView.findViewById(R.id.subtitle_layout).setVisibility(View.GONE);
        } else {
            mView.findViewById(R.id.subtitle_layout).setVisibility(View.VISIBLE);
            subtitleView.setText(mSubtitle);
        }

        if (mBrand.isEmpty()) {
            mView.findViewById(R.id.brand_layout).setVisibility(View.GONE);
        } else {
            mView.findViewById(R.id.brand_layout).setVisibility(View.VISIBLE);
            brandView.setText(mBrand);
        }

        try {
            StringBuilder sb = new StringBuilder();
            JSONArray jsonArray = new JSONArray(mSpecs);
            Log.d("TAG", "updateSummary: jsonArray: " + jsonArray.toString(2));
            if (jsonArray.length() == 0) {
                mView.findViewById(R.id.specs_layout).setVisibility(View.GONE);
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                String value = jsonArray.getString(i);
                Log.d("TAG", "updateSummary: val: " + value);
                sb.append("&#8226; " + value + "<br>");
            }
            specView.setText(Html.fromHtml(sb.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            LinearLayout gallery_layout = mView.findViewById(R.id.gallery_layout);
            JSONArray jsonArray = new JSONArray(mPicturesURL);

            for (int i = 0; i < 10; i++) {
                ImageView imageView =  new ImageView(getContext());
//                imageView.setImageResource(R.drawable.ebay_default);

                Picasso.with(getContext()).load("https://i.ebayimg.com/images/g/wSIAAOSwOFFduejd/s-l300.jpg").fit().centerInside().into(imageView);
                Log.d("TAG", "updateSummary: PICASSO LOADED");
                gallery_layout.addView(imageView);

            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//                String imageUrl = jsonArray.getString(i);
//                Log.d("TAG", "updateSummary: imageUrl: " + imageUrl);
//                ImageView imageView = mView.findViewById(R.id.pic1);// new ImageView(getContext());
////                imageView.setImageResource(R.drawable.shopping_icon_launcher);
////                gallery_layout.addView(imageView);
//                Picasso.with(getContext()).load(imageUrl).fit().centerInside().into(imageView);
//
////                TextView textView = new TextView(getContext());
////                textView.setText("123456");
////                gallery_layout.addView(textView);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_summary, container, false);
        return mView;
    }
}