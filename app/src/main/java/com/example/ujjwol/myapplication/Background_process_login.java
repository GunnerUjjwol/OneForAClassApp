package com.example.ujjwol.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ujjwol on 2/11/2018.
 */

public class Background_process_login extends AsyncTask<String,Void,String> {
    Context context;
    private AlertDialog alertDialog;
    private String type;
    private ProgressDialog progressDialog;

    public Background_process_login(Context ctx){
        context=ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        type=params[0];

        String login_url=params[3];
        Log.d("login_url_string",login_url);
        if(type.equals("login")) {
            try {
                String rollno = params[1];
                String password = params[2];
                if("".equals(rollno)||"".equals(password)){
                    Log.d("user&pwd","USERNAME/PASSWORD EMPTY.");


                    return "emptyuserpwd";
                }
                else {

                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("rollno", rollno)
                            .add("password", password)
                            .build();
                    Request request = new Request.Builder()
                            .url(login_url)
                            .post(body)
                            .build();
                    Log.d("OKHTTP login", "Request created");
                    Response response = client.newCall(request).execute();
                    Log.d("OKHTTP login", "Response Received");
                    //return response.body().string();
                    String responseObj= response.body().string();
                    Log.d("Response in Json form",responseObj);
                    JSONObject obj=new JSONObject(responseObj);
                    String message=obj.getString("message");
                    //Log.d("JSON obj message",message);
                    if("success".equals(message)){
                        Log.d("JSON obj message",message);

                        SharedPrefManager.getInstance(context)
                                .userLogin(
                                        obj.getInt("sn"),
                                        obj.getString("name"),
                                        obj.getString("rollno"),
                                        obj.getString("email")
                                );

                    }
                    return message;

                }

            } catch (IOException e) {
                Log.d("OKHTTP login","Error occured during FormBody Request");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.d("JSONOBJ error", "JSON OBject parsing failed for response string");
                e.printStackTrace();
            }

        }
        return null;

    }
    @Override
    protected void onPreExecute() {
        progressDialog= new ProgressDialog(context);
        progressDialog.setMessage("Logging In...");
        progressDialog.show();
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setIcon(R.drawable.ic_error_black_24dp);
        alertDialog.setTitle("Login Status");

    }
    protected void onPostExecute(String result) {
        Log.d("OKHTTP response",result);
        progressDialog.dismiss();
        if("success".equals(result)){
            Intent intent=new Intent(context,HomeActivity.class);
            context.startActivity(intent);
            ((LoginActivity)context).finish();

        }
        else if ("emptyuserpwd".equals(result)){
            final String text = "USERNAME/PASSWORD CANNOT BE EMPTY.";
            alertDialog.setMessage(text);
            alertDialog.show();
        }

        else{
            Log.d("OKHTTP login failed","password/username not matched");

            alertDialog.setMessage("Incorrect Username/Password");
            alertDialog.show();
        }
    }

}
