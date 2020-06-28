// https://www.youtube.com/watch?v=94rCjYxvzEE
package com.example.androidimagegallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout gallery =findViewById(R.id.gallery);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < 6; i++) {
            View view = inflater.inflate(R.layout.item,gallery,false);
            TextView textView = view.findViewById(R.id.text);
            textView.setText("Item " + i);

            ImageView imageView = view.findViewById(R.id.imageView);
//            imageView.setImageResource(R.drawable.ic_launcher_background);
            Picasso.get().load("https://i.ebayimg.com/images/g/5WUAAOSwy~JdukHR/s-l300.jpg").into(imageView);

           gallery.addView(view);
        }
    }
}