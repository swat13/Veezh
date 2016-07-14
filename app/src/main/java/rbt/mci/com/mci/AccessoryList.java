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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import rbt.mci.com.mci.Adapter.SliderAdapter;
import rbt.mci.com.mci.Parser.DOMParser;
import rbt.mci.com.mci.Parser.RSSFeed;

public class AccessoryList extends Activity implements View.OnClickListener {

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
        setContentView(R.layout.activity_accessorylist);

        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();

        final RSSFeed accListFeed = (RSSFeed) getIntent().getSerializableExtra("AccessoryList");

        init();

        recView = (RecyclerView) findViewById(R.id.accessory_list);
        progress = (RelativeLayout) findViewById(R.id.progress_layout);
        recView.setLayoutManager(new LinearLayoutManager(AccessoryList.this));
        recView.setItemAnimator(new DefaultItemAnimator());

        if (accListFeed != null) {
            ListAdapter accList_ad = new ListAdapter(accListFeed);
            recView.setAdapter(accList_ad);
        }
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView title, cat, city, region;
        ImageView thumb;
        LinearLayout main_layout;

        public FeedViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.accTitle);
            cat = (TextView) itemView.findViewById(R.id.accCategory);
            city = (TextView) itemView.findViewById(R.id.accCity);
            region = (TextView) itemView.findViewById(R.id.accRegion);
            thumb = (ImageView) itemView.findViewById(R.id.thumb);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accessory_list_item, parent, false);
            return new FeedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FeedViewHolder holder, final int position) {
            holder.title.setText(_feed.getItem(position).getTitle());
            holder.cat.setText(_feed.getItem(position).getCategory());
            holder.city.setText(_feed.getItem(position).getProvince() + "، " + _feed.getItem(position).getCity());
            if (_feed.getItem(position).getRegion().equals("")) {
                holder.region.setText("منطقه: " + "-");
            } else {
                holder.region.setText("منطقه: " + _feed.getItem(position).getRegion());
            }
            String image = _feed.getItem(position).getThumb();
            Picasso.with(getApplicationContext())
                    .load("http://www.veezh.com/upload/accessory/" + image + ".jpg")
                    .placeholder(R.drawable.benz_arm)
                    .error(R.drawable.benz_arm)
                    .into(holder.thumb);

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

        Collections.addAll(ImagesArray, IMAGES);

        ViewPager mPager = (ViewPager) findViewById(R.id.slidePager);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPager.setAdapter(new SliderAdapter(AccessoryList.this, ImagesArray));
        NUM_PAGES = IMAGES.length;
    }

    private class AsyncShowList extends AsyncTask<String, Void, RSSFeed> {

        String type = "";
        RSSFeed result1 = null, result2 = null;

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
                result1 = myParser.getAccessoryDetail(id);
                result2 = myParser.getAdImages(id);
            }
            return null;
        }

        @Override
        protected void onPostExecute(RSSFeed result) {
            progress.setVisibility(View.INVISIBLE);
            if (result1 != null) {
                if (result1.getItemCount() > 0) {
                    if (type.equals("D")) {
                        startActivity(new Intent(AccessoryList.this, AccessoryDetail.class).putExtra("accessoryDetail", result1).putExtra("accessoryImage", result2));
                    }
                }
            } else if (activeNetworkInfo == null) {
                Toast.makeText(AccessoryList.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
