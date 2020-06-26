package pkg.hw9;

public class ExampleItem {
    private String mImageUrl;
    private String mCreator;
    private int mLikes;
    private String mCreator2;
    private int mLikes2;

    public ExampleItem(String imageUrl, String creator, int likes, String creator2, int likes2) {
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

    public int getLikeCount() {
        return mLikes;
    }

    public String getCreator2() {
        return mCreator2;
    }

    public int getLikeCount2() {
        return mLikes2;
    }
}