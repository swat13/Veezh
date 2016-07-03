package rbt.mci.com.mci;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import rbt.mci.com.mci.Parser.DOMParser;
import rbt.mci.com.mci.Parser.RSSFeed;
import rbt.mci.com.mci.Parser.RSSItem;
import rbt.mci.com.mci.Utilities.NumberTextWatcher;

public class OtherSettingsActivity extends Activity implements View.OnClickListener {

    TextView fuelType, inColorType, bodyColorType;
    Spinner yearFrom, yearTo;
    EditText priceFrom, priceTo;
    RelativeLayout progress;
    ArrayAdapter<String> dataAdapter;
    ArrayList<String> year = new ArrayList<>();
    ArrayList<String> yearFromId = new ArrayList<>();
    ArrayList<String> yearToId = new ArrayList<>();
    NetworkInfo activeNetworkInfo;
    String fuelId = "", inColorId = "", bodyColorId = "", brandId = "", modelId = "", yearFId = "", yearTId = "", priceFId = "", priceTId = "", statusId = "", cityId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_settings);

        RSSFeed yearListFeed = (RSSFeed) getIntent().getSerializableExtra("YearList");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            brandId = extras.getString("brandId");
            modelId = extras.getString("modelId");
            statusId = extras.getString("statusId");
            cityId = extras.getString("cityId");
        }

        Log.e("statusId", statusId);
        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();

        fuelType = (TextView) findViewById(R.id.fuel_type);
        inColorType = (TextView) findViewById(R.id.in_color_type);
        bodyColorType = (TextView) findViewById(R.id.body_color_type);
        yearFrom = (Spinner) findViewById(R.id.yearFrom);
        yearTo = (Spinner) findViewById(R.id.yearTo);
        priceFrom = (EditText) findViewById(R.id.priceFrom);
        priceTo = (EditText) findViewById(R.id.priceTo);
        progress = (RelativeLayout) findViewById(R.id.progress_layout);

        year.clear();
        yearFromId.clear();
        yearToId.clear();
        year.add(0, "مهم نیست");
        yearFromId.add(0, "");
        yearToId.add(0, "");
        for (int i = 1; i < yearListFeed.getItemCount() + 1; i++) {
            year.add(i, yearListFeed.getItem(i - 1).getManufactured());
            yearFromId.add(i, yearListFeed.getItem(i - 1).getId());
            yearToId.add(i, yearListFeed.getItem(i - 1).getId());
        }

        dataAdapter = new ArrayAdapter<>(OtherSettingsActivity.this, android.R.layout.simple_spinner_item, year);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearTo.setAdapter(dataAdapter);
        yearFrom.setAdapter(dataAdapter);

        yearFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearFId = yearFromId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearTId = yearToId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        priceFrom.addTextChangedListener(new NumberTextWatcher(priceFrom));
        priceTo.addTextChangedListener(new NumberTextWatcher(priceTo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fuel:
                new AsyncShowList().execute("F");
                break;
            case R.id.in_color:
                new AsyncShowList().execute("I");
                break;
            case R.id.body_color:
                new AsyncShowList().execute("B");
                break;
            case R.id.btnSearch:
                priceFId = priceFrom.getText().toString();
                priceTId = priceTo.getText().toString();
                new AsyncShowList().execute("S");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("fuel")) {
                fuelId = ((RSSItem) data.getSerializableExtra("fuel")).getId();
                fuelType.setText(((RSSItem) data.getSerializableExtra("fuel")).getFuel());
                Log.e("fuelId", fuelId);
            } else if (data.hasExtra("inColor")) {
                inColorId = ((RSSItem) data.getSerializableExtra("inColor")).getId();
                inColorType.setText(((RSSItem) data.getSerializableExtra("inColor")).getInColor());
            } else if (data.hasExtra("bodyColor")) {
                bodyColorId = ((RSSItem) data.getSerializableExtra("bodyColor")).getId();
                bodyColorType.setText(((RSSItem) data.getSerializableExtra("bodyColor")).getBodyColor());
            }
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
                case "F":
                    return myParser.getFuelType();
                case "I":
                    return myParser.getInColorType();
                case "B":
                    return myParser.getBodyColorType();
                case "S":
                    return myParser.getCarList(brandId, modelId, priceFId, priceTId, yearFId, yearTId, cityId, bodyColorId, inColorId, fuelId, statusId);
            }
            return null;
        }

        @Override
        protected void onPostExecute(RSSFeed result) {
            progress.setVisibility(View.INVISIBLE);
            if (result != null) {
                if (result.getItemCount() > 0) {
                    switch (type) {
                        case "F":
                            startActivityForResult(new Intent(OtherSettingsActivity.this, FuelType.class).putExtra("FuelList", result), 1);
                            break;
                        case "I":
                            startActivityForResult(new Intent(OtherSettingsActivity.this, InColorType.class).putExtra("InColorList", result), 1);
                            break;
                        case "B":
                            startActivityForResult(new Intent(OtherSettingsActivity.this, BodyColorType.class).putExtra("BodyColorList", result), 1);
                            break;
                        case "S":
                            startActivity(new Intent(OtherSettingsActivity.this, CarList.class).putExtra("CarList", result));
                    }
                }
            } else if (activeNetworkInfo == null) {
                Toast.makeText(OtherSettingsActivity.this, "خطا در برقراری ارتباط!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(OtherSettingsActivity.this, "خودرویی با این مشخصات موجود نیست!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}