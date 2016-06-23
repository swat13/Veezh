package rbt.mci.com.mci;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import rbt.mci.com.mci.Parser.DOMParser;
import rbt.mci.com.mci.Parser.RSSFeed;
import rbt.mci.com.mci.Parser.RSSItem;

public class BrandSelectionList extends Activity {

	RecyclerView recView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brand_selection_list);

		RSSFeed likeListFeed = (RSSFeed) getIntent().getSerializableExtra("BrandList");

		recView = (RecyclerView) findViewById(R.id.brand_list);
		GridLayoutManager glm = new GridLayoutManager(BrandSelectionList.this, 2);
		recView.setLayoutManager(glm);
		recView.setHasFixedSize(true);
		recView.setItemAnimator(new DefaultItemAnimator());

		if (likeListFeed != null) {
			ListAdapter likeList_ad = new ListAdapter(likeListFeed);
			recView.setAdapter(likeList_ad);
		}
	}

	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Intent i = new Intent();
			i.putExtra("brand", brandItem);
			i.putExtra("model", data.getSerializableExtra("model"));
			setResult(RESULT_OK, i);
			finish();
		}

	}*/

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
					i.putExtra("brand", _feed.getItem(position));
					setResult(RESULT_OK, i);
					finish();
					/*brandItem = _feed.getItem(position);
					new AsyncShowList().execute(brandItem.getId());*/
				}
			});

		}

		@Override
		public FeedViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
			View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.selection_list_item, viewGroup, false);

			GridLayoutManager.LayoutParams glp = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
			ViewGroup.LayoutParams glp1 = itemView.findViewById(R.id.main_layout1).getLayoutParams();
			int iDisplayWidth = BrandSelectionList.this.getResources().getDisplayMetrics().widthPixels ;
			int iImageWidth = (iDisplayWidth / 2) ;
			glp1.width = iImageWidth;
			glp1.height = iImageWidth;
			glp.setMargins(1,1,1,1);

			return new FeedViewHolder(itemView);
		}

	}

	/*private class AsyncShowList extends AsyncTask<String, Void, RSSFeed> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected RSSFeed doInBackground(String... params) {
			DOMParser myParser = new DOMParser();
			return myParser.getAllModels(params[0]);
		}

		@Override
		protected void onPostExecute(RSSFeed result) {
			progress.setVisibility(View.INVISIBLE);
			if (result != null) {
				if (result.getItemCount() > 0) {
					startActivityForResult(new Intent(BrandSelectionList.this, ModelSelectionList.class).putExtra("ModelList", result),1);
				}
			} else {
				Toast.makeText(BrandSelectionList.this, "Error In Connection", Toast.LENGTH_SHORT).show();
			}
		}
	}*/

	/*public class RtlGridLayoutManager extends GridLayoutManager {

		private int mParentWidth;
		private int mItemWidth;

		public RtlGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int parentWidth, int itemWidth) {
			super(context, attrs, defStyleAttr, defStyleRes);
			mParentWidth = parentWidth;
			mItemWidth = itemWidth;
		}

		public RtlGridLayoutManager(Context context, int spanCount) {
			super(context, spanCount);
		}

		public RtlGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
			super(context, spanCount, orientation, reverseLayout);
		}

		@Override
		public int getPaddingLeft() {
			return Math.round(mParentWidth / 2f - mItemWidth / 2f);
		}

		@Override
		public int getPaddingRight() {
			return getPaddingLeft();
		}

		@Override
		protected boolean isLayoutRTL(){
			return true;
		}
	}
*/
}
