package com.example.ujjwol.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    public EditText usernamelogin,passwordlogin;
    Context ctx=LoginActivity.this;
    public String loginurlpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        usernamelogin=findViewById(R.id.rollno);
        passwordlogin=findViewById(R.id.password);
        loginurlpath=getString(R.string.webUrl)+"login1.php";





    }
    public void OnLogin(View view){

        String user_name=usernamelogin.getText().toString();
        String password=passwordlogin.getText().toString();
        Log.d("user&pwd","user"+user_name+"&"+"pwd"+password);
        /*if("".equals(user_name)||"".equals(password)){
            Log.d("user&pwd","USERNAME/PASSWORD EMPTY.");
            final String text = "USERNAME/PASSWORD CANNOT BE EMPTY.";
            Toast.makeText(ctx, text,Toast.LENGTH_SHORT).show();
            return;
        }
        else {*/
            String type="login";
            Background_process_login background_process=new Background_process_login(this);
            background_process.execute(type,user_name,password,loginurlpath);}


    //}
}









