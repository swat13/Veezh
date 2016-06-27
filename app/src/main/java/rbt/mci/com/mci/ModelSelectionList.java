package rbt.mci.com.mci;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import rbt.mci.com.mci.Parser.RSSFeed;

public class ModelSelectionList extends Activity {

	RecyclerView recView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.model_selection_list);

		RSSFeed likeListFeed = (RSSFeed) getIntent().getSerializableExtra("ModelList");

		recView = (RecyclerView) findViewById(R.id.model_list);
		GridLayoutManager llm = new GridLayoutManager(ModelSelectionList.this, 2);
		recView.setLayoutManager(llm);
		recView.setHasFixedSize(true);
		recView.setItemAnimator(new DefaultItemAnimator());

		if (likeListFeed != null) {
			ListAdapter likeList_ad = new ListAdapter(likeListFeed);
			recView.setAdapter(likeList_ad);
		}
	}

	public class FeedViewHolder extends RecyclerView.ViewHolder {
		protected LinearLayout main_layout;
		protected TextView brandName;

		public FeedViewHolder(View v) {
			super(v);
			brandName = (TextView) v.findViewById(R.id.brand_name1);
			main_layout = (LinearLayout) v.findViewById(R.id.main_layout1);
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
		public void onBindViewHolder(final FeedViewHolder FeedViewHolder, final int position) {

			FeedViewHolder.brandName.setText(_feed.getItem(position).getName());

			FeedViewHolder.main_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent();
					i.putExtra("model", _feed.getItem(position));
					setResult(RESULT_OK, i);
					finish();
				}
			});

		}

		@Override
		public FeedViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
			View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.selection_list_item, viewGroup, false);

			GridLayoutManager.LayoutParams glp = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
			ViewGroup.LayoutParams glp1 = itemView.findViewById(R.id.main_layout1).getLayoutParams();
			int iDisplayWidth = ModelSelectionList.this.getResources().getDisplayMetrics().widthPixels ;
			int iImageWidth = (iDisplayWidth / 2) ;
			glp1.width = iImageWidth;
			glp1.height = iImageWidth;
			glp.setMargins(1,1,1,1);

			return new FeedViewHolder(itemView);
		}
	}
}
