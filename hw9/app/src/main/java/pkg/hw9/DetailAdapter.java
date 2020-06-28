// https://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
package pkg.hw9;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DetailAdapter extends FragmentPagerAdapter {

    private DetailActivity mDetailActivity;

    public DetailAdapter(DetailActivity detailActivity, FragmentManager fm) {
        super(fm);
        mDetailActivity = detailActivity;

    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SummaryFragment();
                mDetailActivity.setSummaryFragment((SummaryFragment) fragment);
                break;
            case 1:
                fragment = new SellerFragment();
                mDetailActivity.setSellerFragment((SellerFragment) fragment);
                break;
            case 2:
                fragment = new ShippingFragment();
                mDetailActivity.setShippingFragment((ShippingFragment) fragment);
                break;
            default:
                fragment = null;
                break;
        }
        Log.d("TAG", "getItem: fragment: " + fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}