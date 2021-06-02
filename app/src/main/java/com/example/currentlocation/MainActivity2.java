package com.example.currentlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Example_item> exampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        exampleList = new ArrayList<>();
        exampleList.add(new Example_item(R.drawable.ic_android, "Line 1", "Line 2"));
        exampleList.add(new Example_item(R.drawable.ic_android, "Line 3", "Line 4"));
        exampleList.add(new Example_item(R.drawable.ic_android, "Line 5", "Line 6"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void insertItem(View view) {
        exampleList.add(new Example_item(R.drawable.ic_android, "Line 5", "Line 6"));
        mAdapter.notifyDataSetChanged();
    }
}