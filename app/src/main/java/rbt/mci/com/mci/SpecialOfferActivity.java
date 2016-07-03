package rbt.mci.com.mci;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import rbt.mci.com.mci.Adapter.SliderAdapter;
import rbt.mci.com.mci.Parser.DOMParser;
import rbt.mci.com.mci.Parser.RSSFeed;

public class SpecialOfferActivity extends Activity implements View.OnClickListener {

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
        setContentView(R.layout.special_offer_activity);

        final RSSFeed vipListFeed = (RSSFeed) getIntent().getSerializableExtra("VipList");
        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();

        init();

        recView = (RecyclerView) findViewById(R.id.car_list);
        progress = (RelativeLayout) findViewById(R.id.progress_layout);
        recView.setLayoutManager(new LinearLayoutManager(SpecialOfferActivity.this));
        recView.setItemAnimator(new DefaultItemAnimator());

        if (vipListFeed != null) {
            ListAdapter vipList_ad = new ListAdapter(vipListFeed);
            recView.setAdapter(vipList_ad);
        }
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, used, city, gearbox;
        ImageView thumb;
        LinearLayout main_layout;
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options;

        public FeedViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.carName);
            price = (TextView) itemView.findViewById(R.id.carPrice);
            used = (TextView) itemView.findViewById(R.id.carUsed);
            city = (TextView) itemView.findViewById(R.id.carCity);
            gearbox = (TextView) itemView.findViewById(R.id.carGearBox);
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
            String image = _feed.getItem(position).getThumb();
            Picasso.with(getApplicationContext())
                    .load("http://www.veezh.com/upload/" + image + ".jpg")
                    .placeholder(R.drawable.benz_arm)
                    .error(R.drawable.benz_arm)
                    .into(holder.thumb);
            /*ImageLoaderConfiguration defaultConfiguration = new ImageLoaderConfiguration.Builder(SpecialOfferActivity.this)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .memoryCache(new WeakMemoryCache())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .build();
            ImageLoader.getInstance().init(defaultConfiguration);

            holder.options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.benz_arm)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

            holder.imageLoader.displayImage("http://www.veezh.com/upload/" + image + ".jpg", holder.thumb, holder.options);*/

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

        mPager.setAdapter(new SliderAdapter(SpecialOfferActivity.this, ImagesArray));
        NUM_PAGES = IMAGES.length;
    }

    private class AsyncShowList extends AsyncTask<String, Void, RSSFeed> {

        boolean error = false;
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
                result1 = myParser.getCarDetail(id);
                result2 = myParser.getAdImages(id);
            } else {
                error = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(RSSFeed result) {
            progress.setVisibility(View.INVISIBLE);
            if (result1 != null) {
                if (result1.getItemCount() > 0) {
                    if (type.equals("D")) {
                        startActivity(new Intent(SpecialOfferActivity.this, SelectedCarActivity.class).putExtra("carDetail", result1).putExtra("carImage", result2));
                    }
                }
            } else if (error) {
                Toast.makeText(SpecialOfferActivity.this, "لطفا ابتدا برند را انتخاب نمایید!", Toast.LENGTH_SHORT).show();
            } else if (activeNetworkInfo == null) {
                Toast.makeText(SpecialOfferActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
