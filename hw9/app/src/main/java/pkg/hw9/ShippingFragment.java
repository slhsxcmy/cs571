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
//
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_SHIPINFO = "shippingInfoFrag";

    private String mShippingInfo;


    public ShippingFragment() {
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     */
//    public static ShippingFragment newInstance(String shippingInfo) {
//        ShippingFragment fragment = new ShippingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_SHIPINFO, shippingInfo);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shipping, container, false);
        TextView shipinfo = v.findViewById(R.id.shipinfo);
        if (getArguments() != null) {
            // check containsKey
            mShippingInfo = getArguments().getString(BUNDLE_SHIPINFO);
        }

//        string.replaceAll("([A-Z])", " $1");
        StringBuilder sb = new StringBuilder();
        JSONObject ship_json = new JSONObject();
        try {
            ship_json = new JSONObject(mShippingInfo);

            Iterator keys = ship_json.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = ship_json.getString(key);

                sb.append("&#8226; <b>" + key.replaceAll("([A-Z])", " $1") + "</b> : " + value + "<br>");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

//        sb.append("&#8226; <b>AAAA</b> : 3<br>");
//        sb.append("&#8226; <b>HandlingTime</b> : 3<br>");

        shipinfo.setText(Html.fromHtml(sb.toString()));

        return v;
    }
}