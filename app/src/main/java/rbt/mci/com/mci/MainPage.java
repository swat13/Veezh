package rbt.mci.com.mci;

import android.Manifest;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import rbt.mci.com.mci.Parser.DOMParser;
import rbt.mci.com.mci.Parser.RSSFeed;


public class MainPage extends AppCompatActivity {

    TextView title;
    RelativeLayout progress;
    ImageView search, vip_recommend;
    NetworkInfo activeNetworkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.pageTitle);
        search = (ImageView) findViewById(R.id.search);
        vip_recommend = (ImageView) findViewById(R.id.vip_recommend);
        progress = (RelativeLayout) findViewById(R.id.progress_layout);
        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();

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

        vip_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncShowList().execute("V");
            }
        });
    }

    private void checkPermissions() {
        ActivityCompat.requestPermissions(MainPage.this,
                new String[]{Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                0);
    }

    private class AsyncShowList extends AsyncTask<String, Void, RSSFeed> {

        String type = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected RSSFeed doInBackground(String... params) {
            DOMParser myParser = new DOMParser();
            type = params[0];
            if (params[0].equals("V")) {
                return myParser.getVipOffer();
            }
            return null;
        }

        @Override
        protected void onPostExecute(RSSFeed result) {
            progress.setVisibility(View.INVISIBLE);
            if (result != null) {
                if (result.getItemCount() > 0) {
                    if (type.equals("V")) {
                        startActivity(new Intent(MainPage.this, SpecialOfferActivity.class).putExtra("VipList", result));
                    }
                }
            } else if (activeNetworkInfo == null) {
                Toast.makeText(MainPage.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
