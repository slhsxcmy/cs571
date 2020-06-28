// https://developer.android.com/training/basics/fragments/communicating.html
// https://stackoverflow.com/questions/31832519/how-to-notify-an-adapter-in-a-fragment-from-activity
package pkg.hw9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        TextView handling = v.findViewById(R.id.handling);
        if (getArguments() != null) {
            mShippingInfo = getArguments().getString(BUNDLE_SHIPINFO);
        }
        handling.setText(mShippingInfo);

        return v;
    }
}