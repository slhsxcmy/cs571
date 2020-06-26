package pkg.hw9;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;

    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String title = currentItem.getTitle();
        String shipping = currentItem.getShipping();
        String condition = currentItem.getCondition();
        String price = currentItem.getPrice();

        Log.d("TAG", "onBindViewHolder: likes: " + shipping);

        holder.mTextViewTitle.setText(title);
        holder.mTextViewShipping.setText("likes : "+shipping);
        holder.mTextViewCondition.setText(condition);
        holder.mTextViewPrice.setText("likes 2 : "+price);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewShipping;
        public TextView mTextViewCondition;
        public TextView mTextViewPrice;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title);
            mTextViewShipping = itemView.findViewById(R.id.text_view_shipping);
            mTextViewCondition = itemView.findViewById(R.id.text_view_condition);
            mTextViewPrice = itemView.findViewById(R.id.text_view_price);
        }
    }
}