package rbt.mci.com.mci;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainPage extends AppCompatActivity {

    TextView title;
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.pageTitle);
        search = (ImageView) findViewById(R.id.search);

        title.setTypeface(Application.myFont);
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, Search.class));
            }
        });
    }

    private void checkPermissions() {
        ActivityCompat.requestPermissions(MainPage.this,
                new String[]{Manifest.permission.CALL_PHONE}, 0);
    }

}
