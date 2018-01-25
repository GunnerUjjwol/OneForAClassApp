package com.example.ujjwol.myapplication.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ujjwol.myapplication.ClassRecordList;
import com.example.ujjwol.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ClassRecordFragment extends Fragment {
    private TextView mDisplayDate;
    private ListView listView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private List<ClassRecordList> classRecordList = new ArrayList<ClassRecordList>();
    public static String prevDate;
    public static String currentDate;


    private OnFragmentInteractionListener mListener;

    public ClassRecordFragment() {

    }


    public static ClassRecordFragment newInstance() {
        ClassRecordFragment fragment = new ClassRecordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_class_record, container, false);
        //FOR DATEPICKER SELECTION USING DATE PICKER DIALOG


        Calendar cal= Calendar.getInstance();
        final int year= cal.get(Calendar.YEAR);
        final int month= cal.get(Calendar.MONTH);
        final int day=cal.get(Calendar.DAY_OF_MONTH);
        mDisplayDate=view.findViewById(R.id.tv_date);
        listView=view.findViewById(R.id.classListView);
        currentDate=year + "-" + month+1 + "-" + day;//month+1 because jan is 0
        prevDate=currentDate;
        mDisplayDate.setText(currentDate);
        Background_classRecord_process task=new Background_classRecord_process();
        task.execute(currentDate,prevDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog dialog= new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth,
                        mDateSetListener,
                        year, month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener=new DatePickerDialog.OnDateSetListener() {



            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month+=1;
                Log.d("Class Record","onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                currentDate=year + "-" +month + "-" + dayOfMonth;
                if(month<10) {
                    currentDate = year + "-" + "0"+month + "-" + dayOfMonth;
                }
                mDisplayDate.setText(currentDate);
                //error coming here
                Background_classRecord_process task=new Background_classRecord_process();
                task.execute(currentDate,prevDate);

            }
        };


        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    private void populateClassRecordList(JSONObject object) throws JSONException {
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
        /*ListView list=(ListView) findViewById(R.id.classListView);*/
        listView.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<ClassRecordList>{

        MyListAdapter(){
            super(getActivity(),R.layout.item_view,classRecordList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Make sure you have a view to work with
            View itemView =convertView;
            if (itemView==null){
                LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
                itemView=layoutInflater.inflate(R.layout.item_view,parent,false);
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


    //error coming when these executed
    class Background_classRecord_process extends AsyncTask<String,Void,JSONObject> {
        String date;
        String preDate;

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
                Response response = client.newCall(request).execute();
                object = new JSONObject(response.body().string());
                //Log.v("check_Data", object.toString());//erroneous statement, never uncomment
                //Log.v("check_Data_date", object.getString(date));

                return object;
                //object = new JSONObject(response.body().string());
                //Log.v("data_obtained",object.toString());


            }catch (JSONException e) {
                e.printStackTrace();
                Log.v("error_occurred", "JSONException");
            } catch (IOException e) {
                e.printStackTrace();
                Log.v("error_occurred", "IOException");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onPostExecute(JSONObject object) {

            try {
                if ("not found".equals(object.getString("date"))) {
                    mDisplayDate.setText(preDate);
                    Toast.makeText(getActivity(),"Data not found for the date",Toast.LENGTH_SHORT).show();
                    return;
                }
                prevDate=date;
                populateClassRecordList(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            populateListView();


        }

    }
}
