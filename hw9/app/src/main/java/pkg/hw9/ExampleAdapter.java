package pkg.hw9;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

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
        String top = currentItem.getTop();
        String condition = currentItem.getCondition();
        String price = currentItem.getPrice();

//        Log.d("TAG", "onBindViewHolder: likes: " + shipping);
        if (imageUrl.equals("https://thumbs1.ebaystatic.com/pict/04040_0.jpg")) {
            Picasso.with(mContext).load(R.drawable.ebay_default).into(holder.mImageView);
        } else {
            Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        }

        holder.mTextViewTitle.setText(title);

        if (Double.valueOf(shipping) > 0) {
            holder.mTextViewShipping.setText(Html.fromHtml("Ships for <b>$" + shipping + "</b>"));
        } else {
            holder.mTextViewShipping.setText(Html.fromHtml("<b>Free</b> Shipping"));
        }

        if (top.equals("true")) {
            holder.mTextViewTop.setText("Top Rated Listing");
        } else {
            holder.mTextViewTop.setText("");
        }
        if (!condition.isEmpty()) {
            holder.mTextViewCondition.setText(condition);
        } else {
            holder.mTextViewCondition.setText("N/A");
        }
        holder.mTextViewPrice.setText("$" + price);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewShipping;
        public TextView mTextViewTop;
        public TextView mTextViewCondition;
        public TextView mTextViewPrice;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title);
            mTextViewShipping = itemView.findViewById(R.id.text_view_shipping);

            mTextViewTop = itemView.findViewById(R.id.text_view_top);

            mTextViewCondition = itemView.findViewById(R.id.text_view_condition);
            mTextViewPrice = itemView.findViewById(R.id.text_view_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}