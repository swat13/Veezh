package rbt.mci.com.mci;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import rbt.mci.com.mci.Parser.DOMParser;
import rbt.mci.com.mci.Parser.RSSFeed;
import rbt.mci.com.mci.Parser.RSSItem;

public class Accessory extends Activity implements View.OnClickListener {

    RelativeLayout progress;
    NetworkInfo activeNetworkInfo;
    TextView provinceName;
    EditText accessoryName, accessoryField, accessoryCity, accessoryRegion;
    String title = "", region = "", province = "", city = "", category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessory_activity);

        progress = (RelativeLayout) findViewById(R.id.progress_layout);
        provinceName = (TextView) findViewById(R.id.provinceName);
        accessoryName = (EditText) findViewById(R.id.accessoryName);
        accessoryField = (EditText) findViewById(R.id.accessoryField);
        accessoryCity = (EditText) findViewById(R.id.accessoryCity);
        accessoryRegion = (EditText) findViewById(R.id.accessoryRegion);
        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("city")) {
                province = ((RSSItem) data.getSerializableExtra("city")).getId();
                provinceName.setText(((RSSItem) data.getSerializableExtra("city")).getCity());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.accessoryProvince:
                new AsyncShowList().execute("P");
                break;
            case R.id.btnSearch:
                title = accessoryName.getText().toString();
                region = accessoryRegion.getText().toString();
                city = accessoryCity.getText().toString();
                category = accessoryField.getText().toString();
                new AsyncShowList().execute("S");
                break;
        }
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
            switch (params[0]) {
                case "P":
                    return myParser.getCityList();
                case "S":
                    return myParser.getAccessoriesCenter(title, province, city, region, category);
            }
            return null;
        }

        @Override
        protected void onPostExecute(RSSFeed result) {
            progress.setVisibility(View.INVISIBLE);
            if (result != null) {
                if (result.getItemCount() > 0) {
                    switch (type) {
                        case "P":
                            startActivityForResult(new Intent(Accessory.this, CitySelectionList.class).putExtra("CityList", result), 1);
                            break;
                        case "S":
                            startActivityForResult(new Intent(Accessory.this, AccessoryList.class).putExtra("AccessoryList", result), 1);
                            break;
                    }
                }
            } else if (activeNetworkInfo == null) {
                Toast.makeText(Accessory.this, "خطا در برقراری ارتباط!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Accessory.this, "مرکزی با این مشخصات موجود نیست !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
