package rbt.mci.com.mci;

import android.content.Intent;
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
    TextView brandName_tx, modelName_tx;
    Button btnSearch;
    NetworkInfo activeNetworkInfo;
    String brandId = "";
    String modelId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        progress = (RelativeLayout) findViewById(R.id.progress_layout);
        modelName_tx = (TextView) findViewById(R.id.modelName);
        brandName_tx = (TextView) findViewById(R.id.brandName);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelId.equals("") || brandId.equals("")) {
                    if (brandId.equals("")) {
                        Toast.makeText(Search.this, "لطفا ابتدا برند را انتخاب نمایید!", Toast.LENGTH_SHORT).show();
                    } else if (modelId.equals("")) {
                        Toast.makeText(Search.this, "لطفا ابتدا مدل را انتخاب نمایید!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    new AsyncShowList().execute("S");
                }
            }
        });
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
                break;

            case R.id.other:
                break;

            case R.id.btnSearch:
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
                    modelName_tx.setText("RS7");
                }
                brandId = ((RSSItem) data.getSerializableExtra("brand")).getId();
                brandName_tx.setText(((RSSItem) data.getSerializableExtra("brand")).getName());
                Log.e("brandID", brandId);
            }
        }
    }

    private class AsyncShowList extends AsyncTask<String, Void, RSSFeed> {

        boolean error = false;
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
            if (params[0].equals("B")) {
                return myParser.getAllBrands();
            } else if (params[0].equals("M") && brandId != null) {
                return myParser.getAllModels(brandId);
            } else if (params[0].equals("S")) {
                return myParser.getCarList(brandId, modelId, "500000000", "550000000", "130", "133", "8");
            } else {
                error = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(RSSFeed result) {
            progress.setVisibility(View.INVISIBLE);
            if (result != null) {
                if (result.getItemCount() > 0) {
                    if (type.equals("B")) {
                        startActivityForResult(new Intent(Search.this, BrandSelectionList.class).putExtra("BrandList", result), 1);
                    } else if (type.equals("M")) {
                        startActivityForResult(new Intent(Search.this, ModelSelectionList.class).putExtra("ModelList", result), 1);
                    } else if (type.equals("S")) {
                        startActivityForResult(new Intent(Search.this, CarList.class).putExtra("CarList", result), 1);
                    }
                }
            } else if (error) {
                Toast.makeText(Search.this, "لطفا ابتدا برند را انتخاب نمایید!", Toast.LENGTH_SHORT).show();
            } else if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                Toast.makeText(Search.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Search.this, "محصولی موجود نیست", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
