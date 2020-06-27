package pkg.hw9;

import java.util.ArrayList;

public class ExampleItem {
    private String mImageUrl;
    private String mTitle;
    private String mShipping;
    private String mTop;
    private String mCondition;
    private String mPrice;
    private String mID;
    private ArrayList<String> mShippingInfo;

    public ExampleItem(String imageUrl, String title, String shipping, String top, String condition, String price, String ID, ArrayList<String> shippingInfo) {
        mImageUrl = imageUrl;
        mTitle = title;
        mShipping = shipping;
        mTop = top;
        mCondition = condition;
        mPrice = price;
        mID = ID;
        ArrayList<String> mShippingInfo = shippingInfo;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getShipping() {
        return mShipping;
    }

    public String getTop() {
        return mTop;
    }

    public String getCondition() {
        return mCondition;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getID() {
        return mID;
    }

    public ArrayList<String> getShippingInfo() {
        return mShippingInfo;
    }


}