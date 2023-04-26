package edu.uga.cs.rideshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyRecyclerAdapter2 extends RecyclerView.Adapter<MyRecyclerAdapter2.MyViewHolder> {

    private Context mContext;
    private List<Ride> mItems;

    private String mType;

    public MyRecyclerAdapter2(Context context, List<Ride> items, String type) {
        mContext = context;
        mItems = items;
        mType = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item2_layout, parent, false);
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
        Date now = new Date();
        String dateString = "2023/04/26 12:00:00";
        String dateString2 = item.getDate() + " " + item.getTime() + ":00";// replace with your date string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

        try {
            Date dateToCompare = dateFormat.parse(dateString2);
            System.out.println(dateToCompare);
            int comparison = dateToCompare.compareTo(now);
            if (comparison < 0 && item.isSecured() == true) {
                holder.button.setEnabled(false);
            }
                else {
                // dateToCompare is the same as now
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        System.out.print("We in the adapter nowwwww " + item.toString());
        holder.time.setText(item.getTime());

        holder.destination.setText(item.getDestination());
        holder.date.setText(item.getDate());

        holder.secured.setText(item.isSecured() ? "TRUE" : "FALSE");

        if (mType == "ride") {
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //set riderconfirm to true

                    // Perform desired action when button is clicked
                }
            });
        }
        else {
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //set driverConfirm to true

                    // Perform desired action when button is clicked
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView secured;
        public TextView destination;
        public TextView date;

        public Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.textView24);
            secured = itemView.findViewById(R.id.textView25);
            destination = itemView.findViewById(R.id.textView23);
            date = itemView.findViewById(R.id.textView32);
            button = itemView.findViewById(R.id.button7);

        }
    }
}
