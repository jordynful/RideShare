package edu.uga.cs.rideshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Ride> mItems;

    private String mType;

    public MyRecyclerAdapter(Context context, List<Ride> items, String type) {
        mContext = context;
        mItems = items;
        mType = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ride item = mItems.get(position);
        System.out.print("We in the adapter nowwwww " + item.toString());
        holder.time.setText(item.getTime());

        holder.destination.setText(item.getDestination());
        holder.date.setText(item.getDate());

        if (mType.compareTo("request") == 0) {
            holder.driver.setText(item.getRider());
            holder.driverTag.setText("RIDER");
        }
        else {
            holder.driver.setText(item.getDriver());

        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform desired action when button is clicked
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView driver;
        public TextView destination;
        public TextView date;
        public TextView driverTag;
        public Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.textView16);
            driver = itemView.findViewById(R.id.textView14);
            destination = itemView.findViewById(R.id.textView12);
            date = itemView.findViewById(R.id.textView27);
            button = itemView.findViewById(R.id.item_button);
            driverTag = itemView.findViewById(R.id.textView13);
        }
    }
}
