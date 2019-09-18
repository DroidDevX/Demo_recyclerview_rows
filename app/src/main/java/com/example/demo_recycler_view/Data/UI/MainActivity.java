package com.example.demo_recycler_view.Data.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.demo_recycler_view.Data.MockCarData;
import com.example.demo_recycler_view.Data.UI.RecyclerView.CarAdapter;
import com.example.demo_recycler_view.R;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    final CarAdapter adapter = new CarAdapter(MockCarData.getCars());
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TODO: Initialize Recyclerview adapter, layout manager and touch helper
        recyclerView = findViewById(R.id.carRecyclerView);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder draggedItem, @NonNull RecyclerView.ViewHolder targetItem) {
                Log.i(TAG,"onMove");

                adapter.swapCars(draggedItem.getAdapterPosition(),targetItem.getAdapterPosition());

                adapter.notifyItemMoved(draggedItem.getAdapterPosition(),targetItem.getAdapterPosition());

                Log.i(TAG,adapter.getCarsToString());

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
        });

        touchHelper.attachToRecyclerView(recyclerView);

    }

    public void checkBoxOnClick(View view){
        Log.i(TAG,"checkBoxOnClick()");

        SparseBooleanArray visibleColumnsMap = new SparseBooleanArray();

        LinearLayout checkBoxlayout= findViewById(R.id.checkBoxLayout);
        for(int checBoxIndex=0;checBoxIndex<checkBoxlayout.getChildCount();checBoxIndex++){

            CheckBox checkBox = (CheckBox) checkBoxlayout.getChildAt(checBoxIndex);
            visibleColumnsMap.append(checBoxIndex,checkBox.isChecked());
        }
        showRecyclerViewHeaderColumns(visibleColumnsMap);
        adapter.setItemColumnsVisibility(visibleColumnsMap);
    }

    public void showRecyclerViewHeaderColumns(SparseBooleanArray visibleColumnsMap){

        LinearLayout header =findViewById(R.id.recyclerViewHeader);
        for(int column=0;column<header.getChildCount();column++){
            boolean columnIsVisible =visibleColumnsMap.get(column);

            if(columnIsVisible)
                header.getChildAt(column).setVisibility(View.VISIBLE);
            else
                header.getChildAt(column).setVisibility(View.GONE);

        }
    }
}
