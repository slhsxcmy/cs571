package pkg.hw9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_options, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        checkboxNew = findViewById(R.id.checkboxNew);
        checkboxUsed = findViewById(R.id.checkboxUsed);
        checkboxUnspecified = findViewById(R.id.checkboxUnspecified);

        searchButton = findViewById(R.id.searchButton);
        clearButton = findViewById(R.id.clearButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validateKeyword() && validateRange()) {
                    String url = genURL();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fix all fields with errors", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                keywordField.setText("");
                minField.setText("");
                maxField.setText("");
                checkboxNew.setChecked(false);
                checkboxUsed.setChecked(false);
                checkboxUnspecified.setChecked(false);
                sortSpinner.setSelection(0);

            }
        });

    }

    private String genURL() {

        String baseurl = "https://hw8-server-cs571su2020.wl.r.appspot.com/query?";

        int filter_index = 0;
        String query_string = "";

        String kw = keywordField.getText().toString();
        query_string += "keywords=" + keywordField.getText().toString();

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