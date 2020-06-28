// https://developer.android.com/training/basics/fragments/communicating.html
// https://stackoverflow.com/questions/31832519/how-to-notify-an-adapter-in-a-fragment-from-activity
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

import static pkg.hw9.DetailActivity.BUNDLE_SHIPINFO;

public class ShippingFragment extends Fragment {
    private View mView;

    private String mShippingInfo;


    public ShippingFragment() {
        // Required empty public constructor
    }

    public void updateShipping(Bundle args) {
        TextView shipinfo = mView.findViewById(R.id.shipinfo);

        mShippingInfo = args.getString(BUNDLE_SHIPINFO);

        StringBuilder sb = new StringBuilder();

        try {
            JSONObject ship_json = new JSONObject(mShippingInfo);

            Iterator keys = ship_json.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = ship_json.getString(key);

                sb.append("&#8226; <b>" + key.replaceAll("([A-Z])", " $1") + "</b> : " + value + "<br>");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        shipinfo.setText(Html.fromHtml(sb.toString()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_shipping, container, false);
        return mView;
    }
}