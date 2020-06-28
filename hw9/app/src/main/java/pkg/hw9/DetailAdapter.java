// https://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
package pkg.hw9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DetailAdapter extends FragmentPagerAdapter {
//

    //    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private DetailActivity mDetailActivity;
    private Bundle mBundle;

//    public DetailAdapter(FragmentManager fm) {
//        super(fm);
//        mContext = context;
//        mBundle = new Bundle();
//    }

    public DetailAdapter(DetailActivity detailActivity, FragmentManager fm, Bundle bundle) {
        super(fm);
        mDetailActivity = detailActivity;
        mBundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
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
        }
        Log.d("TAG", "getItem: fragment: " +fragment);
        fragment.setArguments(mBundle);
        return fragment;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mContext.getResources().getString(TAB_TITLES[position]);
//    }

    @Override
    public int getCount() {

        return 3;
    }
}