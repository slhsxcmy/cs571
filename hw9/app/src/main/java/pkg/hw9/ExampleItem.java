package pkg.hw9;

public class ExampleItem {
    private String mImageUrl;
    private String mCreator;
    private String mLikes;
    private String mCreator2;
    private String mLikes2;

    public ExampleItem(String imageUrl, String creator, String likes, String creator2, String likes2) {
        mImageUrl = imageUrl;
        mCreator = creator;
        mLikes = likes;
        mCreator2 = creator2;
        mLikes2 = likes2;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public String getLikeCount() {
        return mLikes;
    }

    public String getCreator2() {
        return mCreator2;
    }

    public String getLikeCount2() {
        return mLikes2;
    }
}