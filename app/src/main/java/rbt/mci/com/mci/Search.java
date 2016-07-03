package rbt.mci.com.mci;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import rbt.mci.com.mci.Parser.DOMParser;
import rbt.mci.com.mci.Parser.RSSFeed;
import rbt.mci.com.mci.Parser.RSSItem;

public class Search extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout progress;
    TextView brandName_tx, modelName_tx, sefr, karkarde, cityName_tx;
    NetworkInfo activeNetworkInfo;
    String brandId = "";
    String modelId = "";
    String statusId = "";
    String cityId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        progress = (RelativeLayout) findViewById(R.id.progress_layout);
        modelName_tx = (TextView) findViewById(R.id.modelName);
        brandName_tx = (TextView) findViewById(R.id.brandName);
        cityName_tx = (TextView) findViewById(R.id.cityName);
        sefr = (TextView) findViewById(R.id.sefr);
        karkarde = (TextView) findViewById(R.id.karkarde);

        sefr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sefr.setTypeface(Application.myFont, Typeface.BOLD);
                karkarde.setTypeface(Application.myFont, Typeface.NORMAL);
                statusId = "1";
            }
        });

        karkarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                karkarde.setTypeface(Application.myFont, Typeface.BOLD);
                sefr.setTypeface(Application.myFont, Typeface.NORMAL);
                statusId = "2";
            }
        });

        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.brand:
                new AsyncShowList().execute("B");
                break;
            case R.id.model:
                new AsyncShowList().execute("M");
                break;
            case R.id.location:
                startActivityForResult(new Intent(Search.this, LocationActivity.class), 1);
                break;
            case R.id.other:
                new AsyncShowList().execute("Y");
                break;
            case R.id.btnSearch:
                new AsyncShowList().execute("S");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("model")) {
                modelId = ((RSSItem) data.getSerializableExtra("model")).getId();
                modelName_tx.setText(((RSSItem) data.getSerializableExtra("model")).getName());
                Log.e("modelID", modelId);
            }
            if (data.hasExtra("brand")) {
                if (!modelId.equals("")) {
                    modelId = "";
                    modelName_tx.setText("همه");
                }
                brandId = ((RSSItem) data.getSerializableExtra("brand")).getId();
                brandName_tx.setText(((RSSItem) data.getSerializableExtra("brand")).getName());
                Log.e("brandID", brandId);
            }
            if (data.hasExtra("cityId") && data.hasExtra("cityName")) {
                cityId = data.getStringExtra("cityId");
                cityName_tx.setText(data.getStringExtra("cityName"));
            }
        }
    }

    private class AsyncShowList extends AsyncTask<String, Void, RSSFeed> {

        boolean error = false;
        boolean error2 = false;
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
            if (params[0].equals("M") && brandId.equals("")) {
                error = true;
            } else if (params[0].equals("Y") && modelId.equals("")) {
                error2 = true;
            }
            switch (params[0]) {
                case "B":
                    return myParser.getAllBrands();
                case "M":
                    return myParser.getAllModels(brandId);
                case "S":
                    return myParser.getCarList(brandId, modelId, "", "", "", "", cityId, "", "", "", statusId);
                case "Y":
                    return myParser.getProductionYear(modelId);
            }
            return null;
        }

        @Override
        protected void onPostExecute(RSSFeed result) {
            progress.setVisibility(View.INVISIBLE);
            if (result != null) {
                if (result.getItemCount() > 0) {
                    switch (type) {
                        case "B":
                            startActivityForResult(new Intent(Search.this, BrandSelectionList.class).putExtra("BrandList", result), 1);
                            break;
                        case "M":
                            startActivityForResult(new Intent(Search.this, ModelSelectionList.class).putExtra("ModelList", result), 1);
                            break;
                        case "S":
                            startActivityForResult(new Intent(Search.this, CarList.class).putExtra("CarList", result), 1);
                            break;
                        case "Y":
                            startActivityForResult(new Intent(Search.this, OtherSettingsActivity.class).putExtra("YearList", result).putExtra("brandId", brandId).putExtra("modelId", modelId).putExtra("statusId", statusId).putExtra("cityId", cityId), 1);
                            break;
                    }
                }
            } else if (error) {
                Toast.makeText(Search.this, "لطفا ابتدا برند را انتخاب نمایید!", Toast.LENGTH_SHORT).show();
            } else if (error2) {
                Toast.makeText(Search.this, "لطفا ابتدا مدل را انتخاب نمایید!", Toast.LENGTH_SHORT).show();
            } else if (activeNetworkInfo == null) {
                Toast.makeText(Search.this, "خطا در برقراری ارتباط!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Search.this, "خودرویی با این مشخصات موجود نیست!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
