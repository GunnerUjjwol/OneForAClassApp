package com.example.ujjwol.myapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ujjwol.myapplication.fragments.ClassRecordFragment;
import com.example.ujjwol.myapplication.fragments.NotificationFragment;
import com.example.ujjwol.myapplication.fragments.ProfileFragment;
import com.example.ujjwol.myapplication.fragments.SuggestionFragment;


public class HomeActivity extends AppCompatActivity{

    private String TAG="HomeActivity";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        android.app.FragmentManager fragmentManager=getFragmentManager();



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction1=fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ClassRecordFragment classRecordFragment=ClassRecordFragment.newInstance();
                    /*FragmentTransaction transaction1=fragmentManager.beginTransaction();*/
                    transaction1.replace(R.id.fragment_view,classRecordFragment);
                    transaction1.addToBackStack(null);
                    transaction1.commit();

                    return true;
                case R.id.navigation_notifications:
                   NotificationFragment notificationFragment= NotificationFragment.newInstance();

                    /*FragmentTransaction transaction2=fragmentManager.beginTransaction();*/
                    transaction1.replace(R.id.fragment_view,notificationFragment);
                    transaction1.addToBackStack(null);
                    transaction1.commit();

                    return true;
                case R.id.navigation_suggestions:
                    SuggestionFragment suggestionFragment= SuggestionFragment.newInstance();

                    /*FragmentTransaction transaction2=fragmentManager.beginTransaction();*/
                    transaction1.replace(R.id.fragment_view,suggestionFragment);
                    transaction1.addToBackStack(null);
                    transaction1.commit();

                    return true;
                case R.id.navigation_profile:
                    ProfileFragment profileFragment= ProfileFragment.newInstance();
                    transaction1.replace(R.id.fragment_view,profileFragment);
                    transaction1.addToBackStack(null);
                    transaction1.commit();


                    return true;

                case R.id.navigation_coursefeedback:
                    Toast.makeText(HomeActivity.this, "Course Feedback Selected, Implementaion due",Toast.LENGTH_SHORT).show();
                    return true;

            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }


       android.app.FragmentManager fragmentManager=getFragmentManager();
        ProfileFragment profileFragment=ProfileFragment.newInstance();
        FragmentTransaction transaction1=fragmentManager.beginTransaction();
        transaction1.add(R.id.fragment_view,profileFragment);
        transaction1.addToBackStack(null);
        transaction1.commit();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
