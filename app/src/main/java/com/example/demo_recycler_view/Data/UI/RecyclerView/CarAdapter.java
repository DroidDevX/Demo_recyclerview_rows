package com.example.demo_recycler_view.Data.UI.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_recycler_view.Data.Car;
import com.example.demo_recycler_view.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private static String TAG = CarAdapter.class.getSimpleName();


    int clickedItemPostion;
    ArrayList<Car> carsList;
    SparseBooleanArray itemIsSelected;

    //Item COLUMNS
    SparseBooleanArray visibleColumnsMap;
    public static int COLUMN_MAKE =0;
    public static int COLUMN_MODEL=1;
    public static int COLUMN_YEAR =2;
    public static int COLUMN_PRICE=3;


    public CarAdapter(ArrayList<Car> cars){
        this.carsList =cars;
        clickedItemPostion =-1;

        itemIsSelected = new SparseBooleanArray();
        visibleColumnsMap = new SparseBooleanArray();
        for(int i=0;i<carsList.size();i++) {
            itemIsSelected.append(i, false);
            visibleColumnsMap.append(i,true);
        }


    }


    public String getCarsToString(){
        StringBuilder builder = new StringBuilder();
        for(Car car: carsList)
            builder.append("\n").append(car.toString());
        return builder.toString();
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView =LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_car_item,parent,false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {


        viewHolder.itemRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"onClick()");
                clickedItemPostion =position;
                notifyDataSetChanged();
            }
        });

        if(clickedItemPostion ==position)
        {
            if(!itemIsSelected.get(position)) {
                highlightItem(viewHolder.itemRootView);
                itemIsSelected.append(position,true);
            }
            else {
                dehighlightItem(viewHolder.itemRootView);
                itemIsSelected.append(position,false);
            }
        }
        else
            dehighlightItem(viewHolder.itemRootView);


        //Init
        Car car = carsList.get(position);
        viewHolder.carMake.setText(car.getMake());
        viewHolder.carModel.setText(car.getModel());
        viewHolder.year.setText(car.getYear());
        viewHolder.price.setText(car.getPriceStr());

        viewHolder.showVisibleColumns(visibleColumnsMap);

    }





    @Override
    public int getItemCount() {
        return carsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public View itemRootView;
        public TextView carMake;
        public TextView carModel;
        public TextView year;
        public TextView price;


        ViewHolder(final View itemRootView) {
            super(itemRootView);
            this.itemRootView = itemRootView;
            carMake = itemRootView.findViewById(R.id.carMakeTV);
            carModel = itemRootView.findViewById(R.id.carModelTV);
            year = itemRootView.findViewById(R.id.carYearTV);
            price = itemRootView.findViewById(R.id.carPriceTV);


        }

        void showVisibleColumns(SparseBooleanArray visibleColumnsMap){
            for(int column=0;column< visibleColumnsMap.size();column++){
                boolean columnIsVisible= visibleColumnsMap.get(column);
                int visibility;
                if(columnIsVisible)
                    visibility = View.VISIBLE;
                else
                    visibility=View.GONE;

                if(column==COLUMN_MAKE)
                        carMake.setVisibility(visibility);

                else if(column==COLUMN_MODEL)
                        carModel.setVisibility(visibility);

                else if(column==COLUMN_YEAR)
                        year.setVisibility(visibility);

                else if(column==COLUMN_PRICE)
                        price.setVisibility(visibility);

            }
        }

    }

    public void swapCars(int firstCarIndex,int secondCarIndex){
        Collections.swap(carsList,firstCarIndex,secondCarIndex);
    }

    public void highlightItem(View recyclerViewItem){
        Log.i(TAG,"highlightItem");
        recyclerViewItem.setBackgroundColor(Color.BLUE);

        LinearLayout layout = (LinearLayout)recyclerViewItem;
        for(int childIndex=0;childIndex<layout.getChildCount();childIndex++)
            ((TextView)layout.getChildAt(childIndex)).setTextColor(Color.WHITE);

    }


    public void dehighlightItem(View recyclerViewItem)
    {
        Log.i(TAG,"dehighlightItem");
        recyclerViewItem.setBackgroundColor(Color.WHITE);

        LinearLayout layout = (LinearLayout)recyclerViewItem;
        for(int childIndex=0;childIndex<layout.getChildCount();childIndex++)
            ((TextView)layout.getChildAt(childIndex)).setTextColor(Color.GRAY);

    }

    public void setItemColumnsVisibility(SparseBooleanArray visibleColumnsMap){
        this.visibleColumnsMap.clear();
        this.visibleColumnsMap=visibleColumnsMap;
        notifyDataSetChanged();
    }


}

