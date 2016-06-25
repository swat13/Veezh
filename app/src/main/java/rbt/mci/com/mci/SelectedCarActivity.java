package rbt.mci.com.mci;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import rbt.mci.com.mci.Adapter.SliderAdapter;
import rbt.mci.com.mci.Parser.RSSFeed;

public class SelectedCarActivity extends Activity implements View.OnClickListener {

    TextView price, manuYear, name, used, description, sukht, rang_shodegi, rang_dakheli, rang_badane, masraf, jabe_dande, bime, pelak, phone;
    private static final Integer[] IMAGES = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<Integer> ImagesArray = new ArrayList<>();
    RSSFeed carDetailFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_car_activity);

        carDetailFeed = (RSSFeed) getIntent().getSerializableExtra("carDetail");

        price = (TextView) findViewById(R.id.price);
        manuYear = (TextView) findViewById(R.id.manuYear);
        name = (TextView) findViewById(R.id.name);
        used = (TextView) findViewById(R.id.used);
        description = (TextView) findViewById(R.id.description);
        sukht = (TextView) findViewById(R.id.sukht);
        rang_shodegi = (TextView) findViewById(R.id.rang_shodegi);
        rang_dakheli = (TextView) findViewById(R.id.rang_dakheli);
        rang_badane = (TextView) findViewById(R.id.rang_badane);
        masraf = (TextView) findViewById(R.id.masraf);
        jabe_dande = (TextView) findViewById(R.id.jabe_dande);
        bime = (TextView) findViewById(R.id.bime);
        pelak = (TextView) findViewById(R.id.pelak);
        phone = (TextView) findViewById(R.id.phone);

        init();

        price.setText(carDetailFeed.getItem(0).getPrice());
        manuYear.setText(carDetailFeed.getItem(0).getManufactured());
        name.setText(carDetailFeed.getItem(0).getName());
        used.setText(carDetailFeed.getItem(0).getUsed());
        description.setText(carDetailFeed.getItem(0).getDesc());
        sukht.setText(carDetailFeed.getItem(0).getFuel());
        rang_shodegi.setText(carDetailFeed.getItem(0).getInColor());
        rang_dakheli.setText(carDetailFeed.getItem(0).getBodyInColor());
        rang_badane.setText(carDetailFeed.getItem(0).getBodyColor());
        jabe_dande.setText(carDetailFeed.getItem(0).getGearbox());
        bime.setText(carDetailFeed.getItem(0).getInsurance());
        pelak.setText(carDetailFeed.getItem(0).getPlate());
        phone.setText(carDetailFeed.getItem(0).getPhone());
        masraf.setText("-");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.like:
                break;

            case R.id.share:
                break;

            case R.id.call:
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + carDetailFeed.getItem(0).getPhone())));
                break;

            case R.id.btnPoshtiban:
                break;

            case R.id.btnKharabi:
                break;
        }
    }

    private void init() {

        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        ViewPager mPager = (ViewPager) findViewById(R.id.slidePager);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        ((RadioButton)findViewById(R.id.radioButton)).setChecked(true);
                        break;
                    case 1:
                        ((RadioButton)findViewById(R.id.radioButton2)).setChecked(true);
                        break;
                    case 2:
                        ((RadioButton)findViewById(R.id.radioButton3)).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPager.setAdapter(new SliderAdapter(SelectedCarActivity.this, ImagesArray));

        NUM_PAGES = IMAGES.length;
    }
}
