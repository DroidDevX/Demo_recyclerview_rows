package com.example.demo_recycler_view.Data;

import java.util.ArrayList;

/**
 * Provides Fake car data, because there is current no databases used for
 * this project
 */
public class MockCarData {



    public static ArrayList<Car> getCars(){
        int carlistSize=10;
        ArrayList<Car> carlist = new ArrayList<>();

        int carIndex=0;
        while(carIndex< carlistSize){

            Car car = new Car(String.format("Make #%d",carIndex)
                              ,String.format("Model #%d",carIndex)
                              ,String.format("Year #%d",carIndex),
                              carIndex
            );
            carlist.add(car);
            carIndex++;
        }

        return carlist;
    }


}
