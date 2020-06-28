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
    private String mShippingInfo;
    private String mLink;

    public ExampleItem(String imageUrl, String title, String shipping, String top, String condition, String price, String ID, String shippingInfo, String link) {
        mImageUrl = imageUrl;
        mTitle = title;
        mShipping = shipping;
        mTop = top;
        mCondition = condition;
        mPrice = price;
        mID = ID;
        mShippingInfo = shippingInfo;
        mLink = link;
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

    public String getShippingInfo() {
        return mShippingInfo;
    }

    public String getLink() {
        return mLink;
    }


}