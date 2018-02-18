package com.example.ujjwol.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ujjwol.myapplication.SuggestionCustomAdapter;
import com.example.ujjwol.myapplication.Suggestion_data;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuggestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuggestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestionFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SuggestionCustomAdapter adapter;
    private List<Suggestion_data> data_list;


    private OnFragmentInteractionListener mListener;

    public SuggestionFragment() {
        // Required empty public constructor
    }

    public static SuggestionFragment newInstance() {
        SuggestionFragment fragment = new SuggestionFragment();

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
        View view= inflater.inflate(R.layout.fragment_suggestion, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.suggestion_recycler_view);
        data_list= new ArrayList<>();
        load_data_from_server(0);
        linearLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter =new SuggestionCustomAdapter(getActivity(),data_list);
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

    private void load_data_from_server(int id) {

        Background_suggestion_process task=new Background_suggestion_process();

        task.execute(id);
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

    class Background_suggestion_process extends AsyncTask<Integer,Void,Void> {

        @Override
        protected Void doInBackground(Integer... integers) {

            int id = integers[0];
            OkHttpClient client= new OkHttpClient();
            String urlString=getString(R.string.webUrl)+"suggestion_load.php?id="+id;
            //String urlString="http://192.168.0.106/myapp/suggestion_load.php?id="+id;
            Request request= new Request.Builder().url(urlString).build();
            try {
                Response response = client.newCall(request).execute();
                JSONArray array= new JSONArray(response.body().string());
                for (int i=0; i<array.length();i++){
                    JSONObject object= array.getJSONObject(i);
                    Log.d("JSON ERROR DETECTIOn","1:No error up to here");
                    Suggestion_data data=new Suggestion_data(object.getInt("id"),object.getString("suggester"),object.getString("suggestion_title"),object.getString("suggestion_content"));

                    Log.d("JSON ERROR DETECTIOn","2:No error up to here");
                    data_list.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                Log.v("endofContent","End of Content");
                return null;

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            /*adapter =new SuggestionCustomAdapter(getActivity(),data_list);
            recyclerView.setAdapter(adapter);*/
            adapter.notifyDataSetChanged();


        }
    }
}
