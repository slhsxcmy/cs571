package pkg.hw9;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_REQUEST_URL = "requestUrl";
    public static final String EXTRA_KEYWORD = "searchKeyword";

    public static final boolean DEBUG = true;

    private EditText keywordField;
    private EditText minField;
    private EditText maxField;
    private TextView keywordError;
    private TextView rangeError;
    private CheckBox checkboxNew;
    private CheckBox checkboxUsed;
    private CheckBox checkboxUnspecified;
    private Spinner sortSpinner;
    private Button searchButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        keywordField = findViewById(R.id.keywordField);
        minField = findViewById(R.id.minField);
        maxField = findViewById(R.id.maxField);
        keywordError = findViewById(R.id.keywordError);
        rangeError = findViewById(R.id.rangeError);

        sortSpinner = findViewById(R.id.sortSpinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_options, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        checkboxNew = findViewById(R.id.checkboxNew);
        checkboxUsed = findViewById(R.id.checkboxUsed);
        checkboxUnspecified = findViewById(R.id.checkboxUnspecified);

        searchButton = findViewById(R.id.searchButton);
        clearButton = findViewById(R.id.clearButton);

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                keywordField.setText("");
                minField.setText("");
                maxField.setText("");
                checkboxNew.setChecked(false);
                checkboxUsed.setChecked(false);
                checkboxUnspecified.setChecked(false);
                sortSpinner.setSelection(0);  // index 0
                keywordError.setVisibility(View.GONE);
                rangeError.setVisibility(View.GONE);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean valid = true;
                valid &= validateKeyword();
                valid &= validateRange();

                if (!valid) {
                    Toast.makeText(getApplicationContext(), "Please fix all fields with errors", Toast.LENGTH_SHORT).show();
                } else {
                    String url = genURL();
                    Log.d("TAG", "genURL: " + url);

                    // https://developer.android.com/training/basics/firstapp/starting-activity
                    Intent intent = new Intent(MainActivity.this, CatalogActivity.class);
                    intent.putExtra(EXTRA_REQUEST_URL, url);
                    intent.putExtra(EXTRA_KEYWORD, keywordField.getText().toString());
                    startActivity(intent);
                }

            }
        });

    }

    private String genURL() {

        String baseurl = "https://hw8-server-cs571su2020.wl.r.appspot.com/query?";
        if (DEBUG) {
            baseurl = "http://192.168.1.220:3000/query?";
        }


        int filter_index = 0;
        String query_string = "";

//        String kw = keywordField.getText().toString();
        query_string += "keywords=" + keywordField.getText().toString();

        switch (sortSpinner.getSelectedItemPosition()) {
            case 0:
                query_string += "&sortOrder=BestMatch";
                break;
            case 1:
                query_string += "&sortOrder=CurrentPriceHighest";
                break;
            case 2:
                query_string += "&sortOrder=PricePlusShippingHighest";
                break;
            case 3:
                query_string += "&sortOrder=PricePlusShippingLowest";
                break;
        }

        if (!minField.getText().toString().trim().isEmpty()) {
            query_string += "&itemFilter(" + filter_index + ").name=MinPrice&itemFilter(" + filter_index + ").value=" + minField.getText().toString().trim() + "&itemFilter(" + filter_index + ").paramName=Currency&itemFilter(" + filter_index + ").paramValue=USD";
            ++filter_index;
        }

        if (!maxField.getText().toString().trim().isEmpty()) {
            query_string += "&itemFilter(" + filter_index + ").name=MaxPrice&itemFilter(" + filter_index + ").value=" + maxField.getText().toString().trim() + "&itemFilter(" + filter_index + ").paramName=Currency&itemFilter(" + filter_index + ").paramValue=USD";
            ++filter_index;
        }

        List<String> checked = new ArrayList<>();
        if (checkboxNew.isChecked()) checked.add("New");
        if (checkboxUsed.isChecked()) checked.add("Used");
        if (checkboxUnspecified.isChecked()) checked.add("Unspecified");

        // console.log("count: " + checked.length);
        if (checked.size() == 0) {
        } else if (checked.size() == 1) {
            query_string += "&itemFilter(" + filter_index + ").name=Condition&itemFilter(" + filter_index + ").value=" + checked.get(0);
            ++filter_index;
        } else {
            query_string += "&itemFilter(" + filter_index + ").name=Condition";
            for (int i = 0; i < checked.size(); i++) {
                query_string += "&itemFilter(" + filter_index + ").value(" + i + ")=" + checked.get(i);
            }
            ++filter_index;
        }


        return baseurl + query_string;
    }

    private boolean validateKeyword() {
        String keyword = keywordField.getText().toString().trim();
        if (keyword.isEmpty()) {
            keywordError.setVisibility(View.VISIBLE);
            return false;
        } else {
            keywordError.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateRange() {
        String fromS = minField.getText().toString().trim();
        String toS = maxField.getText().toString().trim();

//        Log.d("TAG", "validateRange: " + fromS + " " + toS);

        double from = 0, to = Double.MAX_VALUE;
        if (!fromS.isEmpty()) {
            try {
                from = Double.parseDouble(fromS);
                if (from < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                rangeError.setVisibility(View.VISIBLE);
                return false;
            }
        }
        if (!toS.isEmpty()) {
            try {
                to = Double.parseDouble(toS);
                if (to < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                rangeError.setVisibility(View.VISIBLE);
                return false;
            }
        }

        if (!fromS.isEmpty() && !toS.isEmpty() && from > to) {
            rangeError.setVisibility(View.VISIBLE);
            return false;
        } else {
            rangeError.setVisibility(View.GONE);
            return true;
        }

    }


}