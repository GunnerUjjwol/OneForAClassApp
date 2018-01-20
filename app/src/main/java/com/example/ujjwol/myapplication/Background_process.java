package com.example.ujjwol.myapplication;

/**
 * Created by ujjwol on 12/21/2017.
 */



        import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AlertDialog;
        import android.util.Log;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.ProtocolException;
        import java.net.URL;
        import java.net.URLEncoder;


public class Background_process extends AsyncTask <String,Void,String>{
    Context context;
    private AlertDialog alertDialog;
    private String type;
    Background_process(Context ctx){
        context=ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        type=params[0];
        String login_url="http://192.168.0.106/myapp/login.php";
        Log.d("ujjwoldebugok0",":okayresult");
        if(type.equals("login")){
            try{
                String rollno=params[1];
                String password=params[2];
                URL url=new URL(login_url);
                Log.d("ujjwoldebugok00",":okayresult");
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                Log.d("ujjwoldebugok000",":okayresult");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                Log.d("ujjwoldebugok40",":okayresult");
                OutputStream outputStream=httpURLConnection.getOutputStream();
                Log.d("ujjwoldebugok1",":okayresult");
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                Log.d("ujjwoldebugok2",":okayresult");
                String post_data= URLEncoder.encode("rollno","UTF-8")+"="+URLEncoder.encode(rollno,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                Log.d("ujjwoldebugok3",post_data);
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                Log.d("ujjwoldebugok4",":okayresult");
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                Log.d("ujjwoldebugok5",":okayresult");
                String result="";
                String line="";

                while ((line=bufferedReader.readLine())!=null){
                    Log.d("ujjwoldebugok6",":okayresult");
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d("ujjwoldebugue",":result");
            } catch (ProtocolException e) {
                e.printStackTrace();
                Log.d("ujjwoldebugpe",":result");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("ujjwoldebugmu",":result");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ujjwoldebugie",":result");
            }
            Log.d("ujjwoldebug2",":returning null");
        }return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

    }

    @Override
    protected void onPostExecute(String result) {
        if("success".equals(result)){
            Intent intent=new Intent(context,HomeActivity.class);
            context.startActivity(intent);
        }
        else if(result==null){
            Log.d("ujjwoldebug","result: "+result);
            alertDialog.setMessage("Cannot LOGIN");
            alertDialog.show();
        }
        else{
            alertDialog.setMessage("CANNOT LOGIN");
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
