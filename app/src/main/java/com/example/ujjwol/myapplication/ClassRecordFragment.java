package com.example.ujjwol.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassRecordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassRecordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private List<ClassRecordList> classRecordList = new ArrayList<ClassRecordList>();
    Context cont;
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClassRecordFragment() {
        // Required empty public constructor
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassRecordFragment newInstance(String param1, String param2) {
        ClassRecordFragment fragment = new ClassRecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view= inflater.inflate(R.layout.fragment_class_record, container, false);
        cont=getContext();

        //FOR DATEPICKER SELECTION USING DATE PICKER DIALOG


        Calendar cal= Calendar.getInstance();
        final int year= cal.get(Calendar.YEAR);
        final int month= cal.get(Calendar.MONTH);
        final int day=cal.get(Calendar.DAY_OF_MONTH);
        mDisplayDate=view.findViewById(R.id.tv_date);
        mDisplayDate.setText(month+1 + "/" + day + "/" + year);//month+1 because jan is 0
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog dialog= new DatePickerDialog(
                        getContext(),
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
                //Because jan is 0 and dec is 11
                month+=1;
                Log.d("Class Record","onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date=month + "/" + dayOfMonth + "/"+year;
                mDisplayDate.setText(date);

            }
        };

        populateClassRecordList();
        populateListView();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context,"Class Record List Attached",Toast.LENGTH_SHORT).show();
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
        void onFragmentInteraction(Uri uri);
    }

    private void populateClassRecordList(){
        classRecordList.add(new ClassRecordList("Computer Network and Securtiy","Digital Signature"));
        classRecordList.add(new ClassRecordList("UML and Design Patterns","State Chart Diagrams"));
        classRecordList.add(new ClassRecordList("Software Testing Methodologies","Black-Box Testing Methodologies"));
        classRecordList.add(new ClassRecordList("Mobile Computing","Push-based Message Brodcasting"));

        classRecordList.add(new ClassRecordList("Computer Network and Securtiy","Digital Signature"));
        classRecordList.add(new ClassRecordList("UML and Design Patterns","State Chart Diagrams"));
        classRecordList.add(new ClassRecordList("Software Testing Methodologies","Black-Box Testing Methodologies"));
        classRecordList.add(new ClassRecordList("Mobile Computing","Push-based Message Brodcasting"));classRecordList.add(new ClassRecordList("Computer Network and Securtiy","Digital Signature"));
        classRecordList.add(new ClassRecordList("UML and Design Patterns","State Chart Diagrams"));
        classRecordList.add(new ClassRecordList("Software Testing Methodologies","Black-Box Testing Methodologies"));
        classRecordList.add(new ClassRecordList("Mobile Computing","Push-based Message Brodcasting"));classRecordList.add(new ClassRecordList("Computer Network and Securtiy","Digital Signature"));
        classRecordList.add(new ClassRecordList("UML and Design Patterns","State Chart Diagrams"));
        classRecordList.add(new ClassRecordList("Software Testing Methodologies","Black-Box Testing Methodologies"));
        classRecordList.add(new ClassRecordList("Mobile Computing","Push-based Message Brodcasting"));classRecordList.add(new ClassRecordList("Computer Network and Securtiy","Digital Signature"));
        classRecordList.add(new ClassRecordList("UML and Design Patterns","State Chart Diagrams"));
        classRecordList.add(new ClassRecordList("Software Testing Methodologies","Black-Box Testing Methodologies"));
        classRecordList.add(new ClassRecordList("Mobile Computing","Push-based Message Brodcasting"));classRecordList.add(new ClassRecordList("Computer Network and Securtiy","Digital Signature"));
        classRecordList.add(new ClassRecordList("UML and Design Patterns","State Chart Diagrams"));
        classRecordList.add(new ClassRecordList("Software Testing Methodologies","Black-Box Testing Methodologies"));
        classRecordList.add(new ClassRecordList("Mobile Computing","Push-based Message Brodcasting"));
    }


    private void populateListView() {
        ArrayAdapter<ClassRecordList> adapter= new MyListAdapter();
        ListView list=(ListView) view.findViewById(R.id.classListView);
        list.setAdapter(adapter);
    }



    private class MyListAdapter extends ArrayAdapter<ClassRecordList>{

        public MyListAdapter() {
            super(cont,R.layout.item_view,classRecordList);
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

}
