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
    private List<String> mItems;

    public MyRecyclerAdapter(Context context, List<String> items) {
        mContext = context;
        mItems = items;
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
        String item = mItems.get(position);

        holder.text.setText(item);

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
        public TextView text;
        public Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.item_text);
            button = itemView.findViewById(R.id.item_button);
        }
    }
}
