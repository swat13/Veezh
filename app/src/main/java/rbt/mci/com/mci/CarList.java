package rbt.mci.com.mci;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import rbt.mci.com.mci.Adapter.SliderAdapter;
import rbt.mci.com.mci.Parser.DOMParser;
import rbt.mci.com.mci.Parser.RSSFeed;

public class CarList extends Activity implements View.OnClickListener {

    private RecyclerView recView;
    RelativeLayout progress;
    NetworkInfo activeNetworkInfo;
    String id = null;
    private static final Integer[] IMAGES = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<Integer> ImagesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carlist);

        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();

        final RSSFeed carListFeed = (RSSFeed) getIntent().getSerializableExtra("CarList");

        init();

        recView = (RecyclerView) findViewById(R.id.car_list);
        progress = (RelativeLayout) findViewById(R.id.progress_layout);
        recView.setLayoutManager(new LinearLayoutManager(CarList.this));
        recView.setItemAnimator(new DefaultItemAnimator());

        if (carListFeed != null) {
            ListAdapter carList_ad = new ListAdapter(carListFeed);
            recView.setAdapter(carList_ad);
        }
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, used, city, gearbox;
        LinearLayout main_layout;

        public FeedViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.carName);
            price = (TextView) itemView.findViewById(R.id.carPrice);
            used = (TextView) itemView.findViewById(R.id.carUsed);
            city = (TextView) itemView.findViewById(R.id.carCity);
            gearbox = (TextView) itemView.findViewById(R.id.carGearBox);
            main_layout = (LinearLayout) itemView.findViewById(R.id.main_layout);
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<FeedViewHolder> {

        private RSSFeed _feed;

        public ListAdapter(RSSFeed feed) {
            this._feed = feed;
        }

        @Override
        public int getItemCount() {
            return _feed.getItemCount();
        }

        @Override
        public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_items, parent, false);
            return new FeedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FeedViewHolder holder, final int position) {
            holder.name.setText(_feed.getItem(position).getName());
            holder.price.setText(_feed.getItem(position).getPrice());
            holder.used.setText(_feed.getItem(position).getUsed());
            holder.city.setText(_feed.getItem(position).getCity());
            holder.gearbox.setText(_feed.getItem(position).getGearbox());

            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = _feed.getItem(position).getId();
                    new AsyncShowList().execute("D");
                }
            });
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

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ((RadioButton) findViewById(R.id.radioButton)).setChecked(true);
                        break;
                    case 1:
                        ((RadioButton) findViewById(R.id.radioButton2)).setChecked(true);
                        break;
                    case 2:
                        ((RadioButton) findViewById(R.id.radioButton3)).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPager.setAdapter(new SliderAdapter(CarList.this, ImagesArray));
        NUM_PAGES = IMAGES.length;
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
            if (params[0].equals("D")) {
                return myParser.getCarDetail(id);
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
                    if (type.equals("D")) {
                        startActivity(new Intent(CarList.this, SelectedCarActivity.class).putExtra("carDetail", result));
                    }
                }
            } else if (error) {
                Toast.makeText(CarList.this, "لطفا ابتدا برند را انتخاب نمایید!", Toast.LENGTH_SHORT).show();
            } else if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                Toast.makeText(CarList.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
