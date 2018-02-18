package com.example.ujjwol.myapplication.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ujjwol.myapplication.CustomAdapter;
import com.example.ujjwol.myapplication.Notification_data;
import com.example.ujjwol.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapter adapter;
    private List<Notification_data> data_list;


    private OnFragmentInteractionListener mListener;

    public NotificationFragment() {

    }


    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
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
        View view =inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        data_list= new ArrayList<>();

        load_data_from_server(0);
        linearLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter =new CustomAdapter(getActivity(), data_list);
        recyclerView.setAdapter(adapter);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                Log.d("last visible item", lastVisibleItem+" "+data_list.size());
                if(lastVisibleItem+1==data_list.size()){
                    load_data_from_server(lastVisibleItem+1);
                }
                else{
                    return;
                }

            }
        });
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

    private void load_data_from_server(int id) {

        Background_notification_process task=new Background_notification_process();

        task.execute(id);
    }

    class Background_notification_process extends AsyncTask<Integer,Void,Void> {

        @Override
        protected Void doInBackground(Integer... integers) {

            int id = integers[0];
            OkHttpClient client= new OkHttpClient();
            Log.d("Notififragment debug", "okkhttp client establishged");
            String urlString=getString(R.string.webUrl)+"notification_load.php?id="+id;
            //String urlString="http://192.168.0.106/myapp/notification_load.php?id="+id;
            Request request= new Request.Builder().url(urlString).build();
            Log.d("Notififragment debug", "request string generated");
            try {
                Log.d("Notififragment debug", "attempting newcall to get response");
                Response response = client.newCall(request).execute();
                Log.d("Notififragment debug", "response received");
                JSONArray array= new JSONArray(response.body().string());
                Log.d("Notififragment", array.toString());
                for (int i=0; i<array.length();i++){
                    JSONObject object= array.getJSONObject(i);
                    Notification_data data=new Notification_data(object.getInt("id"),object.getString("title"),object.getString("content"));
                    data_list.add(data);
                    Log.d("Notififragment debug", "parsing json and storing to list view");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                Log.v("endofContent","End of Content");

            }
            Log.d("Notififragment debug", "doinbackground finished");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            /*adapter =new CustomAdapter(getActivity(), data_list);
            recyclerView.setAdapter(adapter);*/
            Log.d("Notififragment debug", "reached onpostexecute");
            adapter.notifyDataSetChanged();


        }
    }

}
