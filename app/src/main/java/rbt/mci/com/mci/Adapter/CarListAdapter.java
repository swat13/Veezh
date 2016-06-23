package rbt.mci.com.mci.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rbt.mci.com.mci.Parser.RSSFeed;
import rbt.mci.com.mci.Parser.RSSItem;
import rbt.mci.com.mci.R;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyViewHolder> {

    RSSFeed data;
    private Context context;

    public CarListAdapter(Context context, RSSFeed data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(data.getItem(position).getName());
        holder.price.setText(data.getItem(position).getPrice());
        holder.used.setText(data.getItem(position).getUsed());
        holder.city.setText(data.getItem(position).getCity());
        holder.gearbox.setText(data.getItem(position).getGearbox());
    }

    @Override
    public int getItemCount() {
        return data.getItemCount();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, used, city, gearbox;
        LinearLayout main_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.carName);
            price = (TextView) itemView.findViewById(R.id.carPrice);
            used = (TextView) itemView.findViewById(R.id.carUsed);
            city = (TextView) itemView.findViewById(R.id.carCity);
            gearbox = (TextView) itemView.findViewById(R.id.carGearBox);
            main_layout = (LinearLayout) itemView.findViewById(R.id.main_layout);
        }
    }

}
