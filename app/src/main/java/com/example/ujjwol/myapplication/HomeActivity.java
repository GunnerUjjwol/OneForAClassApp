package com.example.ujjwol.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ujjwol.myapplication.fragments.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeActivity extends AppCompatActivity{

    private String TAG="HomeActivity";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        android.app.FragmentManager fragmentManager=getFragmentManager();



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ClassRecordFragment classRecordFragment=ClassRecordFragment.newInstance();
                    FragmentTransaction transaction1=fragmentManager.beginTransaction();
                    transaction1.replace(R.id.fragment_view,classRecordFragment);
                    transaction1.addToBackStack(null);
                    transaction1.commit();

                   /* Intent intent_home = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent_home);*/
                    return true;
                case R.id.navigation_notifications:
                   NotificationFragment notificationFragment= NotificationFragment.newInstance();

                    FragmentTransaction transaction2=fragmentManager.beginTransaction();
                    transaction2.replace(R.id.fragment_view,notificationFragment);
                    transaction2.addToBackStack(null);
                    transaction2.commit();

                   /*Intent intent_notification = new Intent(HomeActivity.this, NotificationsActivity.class);
                   startActivity(intent_notification);*/
                    return true;
                case R.id.navigation_dashboard:

                    Toast.makeText(HomeActivity.this, "Dashboard Selected, Implementaion due",Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_coursefeedback:
                    Toast.makeText(HomeActivity.this, "Course Feedback Selected, Implementaion due",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_suggestions:
                    Toast.makeText(HomeActivity.this, "Suggestions Selected, Implementaion due",Toast.LENGTH_SHORT).show();
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       android.app.FragmentManager fragmentManager=getFragmentManager();
        ClassRecordFragment classRecordFragment=ClassRecordFragment.newInstance();
        FragmentTransaction transaction1=fragmentManager.beginTransaction();
        transaction1.add(R.id.fragment_view,classRecordFragment);
        transaction1.addToBackStack(null);
        transaction1.commit();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



/*    private void populateClassRecordList(JSONObject object) throws JSONException {
        clearList();
        classRecordList.add(new ClassRecordList(object.getString("subject1"),object.getString("s1_topic")));
        classRecordList.add(new ClassRecordList(object.getString("subject2"),object.getString("s2_topic")));
        classRecordList.add(new ClassRecordList(object.getString("subject3"),object.getString("s3_topic")));
        classRecordList.add(new ClassRecordList(object.getString("subject4"),object.getString("s4_topic")));
        classRecordList.add(new ClassRecordList(object.getString("subject5"),object.getString("s5_topic")));

    }

    private void clearList(){
        classRecordList.clear();
    }


    private void populateListView() {
        ArrayAdapter<ClassRecordList> adapter= new MyListAdapter();
        ListView list=(ListView) findViewById(R.id.classListView);
        list.setAdapter(adapter);
    }



    @Override
    public void onFragmentInteraction() {

    }


    private class MyListAdapter extends ArrayAdapter<ClassRecordList>{

        public MyListAdapter() {
            super(HomeActivity.this,R.layout.item_view,classRecordList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Make sure you have a view to work with
            View itemView =convertView;
            if (itemView==null){
                itemView=getLayoutInflater().inflate(R.layout.item_view,parent,false);
            }
            //fill the ClassRecord to work with
            ClassRecordList currrentList= classRecordList.get(position);


            //fill the view
            TextView period= (TextView) itemView.findViewById(R.id.item_period);
            period.setText(currrentList.getPeriod());
            TextView topics_taught= (TextView) itemView.findViewById(R.id.item_topic_taught);
            topics_taught.setText(currrentList.getTaught_topic());

            return itemView;
        }
    }


    class Background_classRecord_process extends AsyncTask<String,Void,JSONObject>{
        String date;
        String preDate;
        private AlertDialog alertDialog;
                @Override
        protected JSONObject doInBackground(String... strings) {
            date= strings[0];
            preDate=strings[1];
            OkHttpClient client= new OkHttpClient();
            Log.v("date_Sent",date);
            String urlString="http://192.168.0.106/myapp/classRecord_load.php?date="+date;
            Log.v("query_Sent",urlString);
            Request request= new Request.Builder().url(urlString).build();
            JSONObject object;
            try {
                Response response=client.newCall(request).execute();
                object= new JSONObject(response.body().string());
                Log.v("check_Data",object.toString());
                return object;
                               //object = new JSONObject(response.body().string());
                //Log.v("data_obtained",object.toString());



            } catch (IOException e) {
                e.printStackTrace();
                Log.v("error_occurred", "IOException");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("error_occurred", "JSONException");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            alertDialog= new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setIcon(R.drawable.ic_error_black_24dp);
            alertDialog.setTitle("Data Retrieval Failed");
        }

        @Override
        protected void onPostExecute(JSONObject object) {

            try {
                if("not found".equals(object.getString("date"))){
                    mDisplayDate.setText(preDate);

                   // alertDialog.setMessage("No Data Found for the Chosen Date! /n Choose a different date");
                  //  alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    //    @Override
                     //   public void onClick(DialogInterface dialog, int which) {
                            //return;
                     //   }
                   // });
                   // alertDialog.show();

                    Toast.makeText(getApplicationContext(),"Data not found for the date",Toast.LENGTH_SHORT).show();
                    return;
                }
                HomeActivity.prevDate=date;
                populateClassRecordList(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            populateListView();
        }
    }*/

}
