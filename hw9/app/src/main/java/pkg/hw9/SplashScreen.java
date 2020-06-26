// https://www.youtube.com/watch?v=o6_IEF7Q19o
//
package pkg.hw9;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
        finish();
    }
}