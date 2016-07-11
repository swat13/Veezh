package rbt.mci.com.mci;

import android.app.Activity;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import rbt.mci.com.mci.Parser.RSSFeed;

public class AccessoryDetail extends Activity implements View.OnClickListener {

    TextView accTitle, accLocation, accDescription;
    ImageView accThumb;
    RSSFeed accDetailFeed;
    RSSFeed accImageFeed;
    NetworkInfo activeNetworkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessory_detail);

        accDetailFeed = (RSSFeed) getIntent().getSerializableExtra("accessoryDetail");
        accImageFeed = (RSSFeed) getIntent().getSerializableExtra("accessoryImage");
        activeNetworkInfo = Application.connectivityManager.getActiveNetworkInfo();

        accTitle = (TextView) findViewById(R.id.accTitle);
        accLocation = (TextView) findViewById(R.id.accLocation);
        accDescription = (TextView) findViewById(R.id.accDescription);
        accThumb = (ImageView) findViewById(R.id.accThumb);

        accTitle.setText(accDetailFeed.getItem(0).getTitle());
        accLocation.setText(accDetailFeed.getItem(0).getProvince() + "، " + accDetailFeed.getItem(0).getCity() + "، منطقه " + accDetailFeed.getItem(0).getRegion());
        accDescription.setText(accDetailFeed.getItem(0).getDesc());
        Picasso.with(getApplicationContext())
                .load("http://www.veezh.com/upload/accessory/" + accDetailFeed.getItem(0).getThumb() + ".jpg")
                .placeholder(R.drawable.benz_arm)
                .error(R.drawable.benz_arm)
                .into(accThumb);

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

            case R.id.btnPoshtiban:
                break;

            case R.id.btnKharabi:
                break;
        }
    }
}
