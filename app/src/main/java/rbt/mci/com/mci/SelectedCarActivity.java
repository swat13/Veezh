package rbt.mci.com.mci;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import rbt.mci.com.mci.Parser.RSSFeed;

public class SelectedCarActivity extends Activity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener {
    TextView price, manuYear, name, used, description, sukht, rang_shodegi, rang_dakheli, rang_badane, masraf, jabe_dande, bime, pelak, phone;
    RSSFeed carDetailFeed;
    RSSFeed carImageFeed;
    NetworkInfo activeNetworkInfo;
    SliderLayout mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_car_activity);

        carDetailFeed = (RSSFeed) getIntent().getSerializableExtra("carDetail");
        carImageFeed = (RSSFeed) getIntent().getSerializableExtra("carImage");
        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();

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

        mPager = (SliderLayout) findViewById(R.id.slidePager);
        HashMap<String, String> url_maps = new HashMap<>();
        for (int i = 0; i < carImageFeed.getItemCount(); i++) {
            url_maps.put("" + i, "http://www.veezh.com/upload/" + carImageFeed.getItem(i).getImage() + ".jpg");
            Log.e("image", carImageFeed.getItem(i).getImage());
        }

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            mPager.addSlider(textSliderView);
        }
        mPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mPager.setCustomAnimation(new DescriptionAnimation());
        mPager.setDuration(5000);
        mPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onStop() {
        mPager.stopAutoCycle();
        super.onStop();
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
