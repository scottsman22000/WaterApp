package com.example.mike.waterapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Calendar;


/*
    Keep track of how much you drink during a 24 hour period (v1)

 */

public class MainActivity extends ActionBarActivity {
    //Only one bottle size (v1)
    //Want to drink only a gallon of water (v1)
    private final int bottleSize = 14;
    private final int gallonSize = 128;

    //savedInstanceState keys
    static final String DAY_OF_YEAR = "day_of_year";
    static final String NUMBER_OF_BOTTLES = "number_of_bottles";

    //Keep track of the day of the year, instead of using a 24 hour period (v1)
    //Only the waterPercent will be changed (v1)
    private int dayOfYear;
    private int numberOfBottles;
    private String waterPercentText;
    private TextView waterPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waterPercent = (TextView)findViewById(R.id.water_drunk_text_view);
        Calendar calendar = Calendar.getInstance();

        //get the data from SharedPreferences  and set dayOfYear, waterPercentText and
        //the number of bottles drunk
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        numberOfBottles = sharedPreferences.getInt(NUMBER_OF_BOTTLES, 0);
        dayOfYear = sharedPreferences.getInt(DAY_OF_YEAR, 0);
        waterPercentText = getWaterPercentage();

        //checks the day of the year, and sets it to current day, if it is not
        //today.
        //reset the waterPercentText to 0
        if(dayOfYear < calendar.get(Calendar.DAY_OF_YEAR)){

            dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
            waterPercentText = "0%";
            numberOfBottles = 0;

        }//end if

        //set text of the waterPercent textView
        waterPercent.setText(waterPercentText);


    }//end onCreate

    //When the system is paused, or about to be destroyed, save
    //numberOfBottles and dayOfYear as persistent data. Takes care
    //of both onDestroy and onPause()
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(DAY_OF_YEAR, dayOfYear);
        editor.putInt(NUMBER_OF_BOTTLES, numberOfBottles);

        editor.commit();

    }//end onPause


    public boolean onOptionsItemSelected(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }//end onOptionsItemSelected

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected

    /*
        onClick for the addWater button, this will increase the number of bottls by
        1, and change the text of waterPercent textView
     */
    public void increaseWater(View view) {

        numberOfBottles++;

        waterPercentText = getWaterPercentage();

        waterPercent.setText(waterPercentText);

    }//end increaseWater

    private String getWaterPercentage() {

        double waterPercentage = ((double)numberOfBottles * bottleSize)/gallonSize*100;

        int temp = (int) Math.ceil(waterPercentage);

        return (temp + "%");

    }//end getWaterPercentage
}//end class