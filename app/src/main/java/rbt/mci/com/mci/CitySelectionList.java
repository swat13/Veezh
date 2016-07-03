package rbt.mci.com.mci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import rbt.mci.com.mci.Parser.RSSFeed;

public class CitySelectionList extends Activity {

    RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_select);

        RSSFeed cityListFeed = (RSSFeed) getIntent().getSerializableExtra("CityList");

        recView = (RecyclerView) findViewById(R.id.city_list);
        LinearLayoutManager llm = new LinearLayoutManager(CitySelectionList.this);
        recView.setLayoutManager(llm);
        recView.setItemAnimator(new DefaultItemAnimator());

        if (cityListFeed != null) {
            ListAdapter cityList_ad = new ListAdapter(cityListFeed);
            recView.setAdapter(cityList_ad);
        }
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        LinearLayout main_layout;

        public FeedViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.type_name);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_list_item, parent, false);
            return new FeedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FeedViewHolder holder, final int position) {
            holder.name.setText(_feed.getItem(position).getCity());

            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.putExtra("city", _feed.getItem(position));
                    setResult(RESULT_OK, i);
                    finish();
                }
            });
        }
    }
}
