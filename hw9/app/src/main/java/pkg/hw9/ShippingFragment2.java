package pkg.hw9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static pkg.hw9.DetailActivity.BUNDLE_RETURN;
import static pkg.hw9.DetailActivity.BUNDLE_SELLER;
import static pkg.hw9.DetailActivity.BUNDLE_SHIPINFO;


public class ShippingFragment2 extends Fragment {
    private View mView;
    private String mShip;

    public ShippingFragment2() {
        // Required empty public constructor
    }

    public void updateSeller(Bundle args) {
        TextView shipinfo2 = mView.findViewById(R.id.shipinfo2);

        mShip = args.getString(BUNDLE_SHIPINFO);

        StringBuilder sb = new StringBuilder();

// set seller
        try {
            JSONObject json = new JSONObject(mShip);

            Iterator keys = json.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = json.getString(key);

                sb.append("&#8226; <b>" + key.replaceAll("([A-Z])", " $1") + "</b> : " + value + "<br>");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        shipinfo2.setText(Html.fromHtml(sb.toString()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_shipping2, container, false);
        return mView;
    }
}