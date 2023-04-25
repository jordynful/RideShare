package edu.uga.cs.rideshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CurrentRequestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_requests);

        RecyclerView recyclerView = findViewById(R.id.recycler);

// create a new list to hold the items
        List<String> items = new ArrayList<>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");

// create a new adapter and pass in the list of items
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, items);

// set the adapter on the RecyclerView
        recyclerView.setAdapter(adapter);

    }
}