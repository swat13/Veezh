package rbt.mci.com.mci;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import rbt.mci.com.mci.Adapter.CarListAdapter;
import rbt.mci.com.mci.Adapter.SliderAdapter;
import rbt.mci.com.mci.Parser.RSSFeed;

public class CarList extends Activity implements View.OnClickListener {

    private RecyclerView recView;
    private static final Integer[] IMAGES = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<Integer> ImagesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carlist);

        RSSFeed carListFeed = (RSSFeed) getIntent().getSerializableExtra("CarList");

        init();

        recView = (RecyclerView) findViewById(R.id.car_list);
        recView.setLayoutManager(new LinearLayoutManager(CarList.this));
        recView.setItemAnimator(new DefaultItemAnimator());

        if (carListFeed != null) {
            CarListAdapter likeList_ad = new CarListAdapter(this, carListFeed);
            recView.setAdapter(likeList_ad);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void init() {

        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        ViewPager mPager = (ViewPager) findViewById(R.id.slidePager);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        mPager.setAdapter(new SliderAdapter(CarList.this, ImagesArray));
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);

        NUM_PAGES = IMAGES.length;
    }
}
