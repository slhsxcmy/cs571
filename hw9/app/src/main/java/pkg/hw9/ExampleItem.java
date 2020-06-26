package pkg.hw9;

public class ExampleItem {
    private String mImageUrl;
    private String mTitle;
    private String mShipping;
    private String mCondition;
    private String mPrice;

    public ExampleItem(String imageUrl, String title, String shipping, String condition, String price) {
        mImageUrl = imageUrl;
        mTitle = title;
        mShipping = shipping;
        mCondition = condition;
        mPrice = price;
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

    public String getCondition() {
        return mCondition;
    }

    public String getPrice() {
        return mPrice;
    }
}