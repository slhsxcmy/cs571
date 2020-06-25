package pkg.hw9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    // https://developer.android.com/training/basics/firstapp/starting-activity
                    Intent intent = new Intent(CatalogActivity.this, DetailActivity.class);
//                    intent.putExtra("EXTRA_MESSAGE", url);
                    startActivity(intent);


            }
        });
    }
}