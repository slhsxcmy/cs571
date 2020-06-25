package pkg.hw9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText keywordField;
    private EditText minField;
    private EditText maxField;
    private TextView keywordError;
    private TextView rangeError;
    private Spinner sortSpinner;

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
//        sortSpinner.setOnItemClickListener(this);

    }

    private boolean validateKeyword() {
        String keyword = keywordField.getText().toString().trim();
        if (keyword.isEmpty()) {
            keywordField.setError("Please enter mandatory field");
            return false;
        } else {
            keywordField.setError("null");
            return true;
        }
    }

    private boolean validateRange() {
        String fromS = minField.getText().toString().trim();
        String toS = maxField.getText().toString().trim();
        double from = 0, to = Double.MAX_VALUE;
        try {
            from = Double.parseDouble(fromS);
            if (from < 0) throw new NumberFormatException();

        } catch (NumberFormatException e) {
            System.out.println("String " + from + "is not a number");
        }

        return true;

    }


}