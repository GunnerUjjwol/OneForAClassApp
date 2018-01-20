package com.example.ujjwol.myapplication;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapter adapter;
    private List<Notification_data> data_list;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent_home = new Intent(NotificationsActivity.this, HomeActivity.class);
                    startActivity(intent_home);
                    return true;

                case R.id.navigation_notifications:
                    Intent intent_notification = new Intent(NotificationsActivity.this, NotificationsActivity.class);
                    startActivity(intent_notification);
                    return true;
                case R.id.navigation_dashboard:
                    Toast.makeText(NotificationsActivity.this, "Dashboard Selected, Implementaion due",Toast.LENGTH_LONG).show();
                    return true;

                case R.id.navigation_coursefeedback:
                    Toast.makeText(NotificationsActivity.this, "Course Feedback Selected, Implementaion due",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_suggestions:
                    Toast.makeText(NotificationsActivity.this, "Suggestions Selected, Implementaion due",Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        data_list= new ArrayList<>();
        load_data_from_server(0);

        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter =new CustomAdapter(this, data_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(linearLayoutManager.findLastVisibleItemPosition()==data_list.size()-1){
                    load_data_from_server(data_list.get(data_list.size()-2).getId());
                }

            }
        });
    }

    private void load_data_from_server(int id) {

         Background_notification_process task=new Background_notification_process();

         task.execute(id);
    }
        class Background_notification_process extends AsyncTask<Integer,Void,Void>{
    @Override
        protected Void doInBackground(Integer... integers) {
        int id=integers[0];
        OkHttpClient client= new OkHttpClient();
        String urlString="http://192.168.0.106/myapp/notification_load.php?id="+id;
        Request request= new Request.Builder().url(urlString).build();
        try {
            Response response = client.newCall(request).execute();
            JSONArray array= new JSONArray(response.body().string());
            for (int i=0; i<array.length();i++){
                JSONObject object= array.getJSONObject(i);
                Notification_data data=new Notification_data(object.getInt("id"),object.getString("title"),object.getString("content"));
                data_list.add(data);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Log.v("endofContent","End of Content");

        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter.notifyDataSetChanged();
    }
}
}
