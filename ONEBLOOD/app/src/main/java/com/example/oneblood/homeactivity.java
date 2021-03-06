package com.example.oneblood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class homeactivity extends AppCompatActivity implements View.OnClickListener {

    String JSON_STRING="";
    String json_string;

    CardView cardView1,cardView2,cardView3;

    EditText ET_NAME,ET_PASS;
    String login_name,login_pass;
    private EditText userName,Password;
    private TextView login;
    private ImageView imageView;
    private RelativeLayout loginButton,registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        ET_NAME = (EditText)findViewById(R.id.userID);
        ET_PASS = (EditText)findViewById(R.id.passID);

        loginButton = (RelativeLayout) findViewById(R.id.cd1);
        registerButton = (RelativeLayout) findViewById(R.id.cd2);
        userName = (EditText) findViewById(R.id.userID);
        Password = (EditText) findViewById(R.id.passID);
        login = (TextView) findViewById(R.id.loginID);
        cardView1 = (CardView) findViewById(R.id.cardview1);
        cardView2 = (CardView) findViewById(R.id.cardview2);
        cardView3 = (CardView) findViewById(R.id.cardview3);
        imageView = (ImageView) findViewById(R.id.imageView);


        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        //animation
        cardView1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein));
        cardView2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein));
        cardView3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein));
        imageView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein));

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.cd1 ){
                /*Toast toast = Toast.makeText(MainActivity.this,"Login Attempt Succesful",Toast.LENGTH_SHORT);
                toast.show();*/


        }

        else if(v.getId()==R.id.cd1){
            /*Toast toast2 = Toast.makeText(MainActivity.this,"Login Attemp Failed",Toast.LENGTH_SHORT);
            toast2.show();*/
        }

        else if(v.getId()==R.id.cd2){
            Toast.makeText(this,"Create account",Toast.LENGTH_SHORT).show();
            openRegister();
        }
    }

    public void openActivity2(){
        Intent intent = new Intent(this,Activity2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadein);
    }

    public void openRegister(){
        Intent intent2 = new Intent(this,Register.class);
        startActivity(intent2);
        overridePendingTransition(R.anim.fadein,R.anim.fadein);
    }
    public void userReg(View view){
        startActivity(new Intent(this,Register.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadein);
    }
    public void userLogin(View view){

        login_name = ET_NAME.getText().toString();
        login_pass = ET_PASS.getText().toString();
        String method = "login";
        Database backgroundTask = new Database(this);
        backgroundTask.execute(method,login_name,login_pass);
        ET_NAME.setText("");
        ET_PASS.setText("");
        new BackgroundTask().execute();
    }

    public void guestlogin(View view){


        String method = "login";
        Database backgroundTask1 = new Database(this);
        backgroundTask1.execute(method,"guest","guest");
        new BackgroundTask().execute();
    }


    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url;
        @Override
        protected  void onPreExecute(){
            json_url = "https://oneblood1.000webhostapp.com/oneblood/json.php";
        }

        @Override
        protected String  doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING= bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();


                String result=stringBuilder.toString();
                JSONArray ja=new JSONArray(result);

                Dbcontract.setUSER_INFO_JSONarry(ja);

                /*for(int i=0;i<ja.length();i++){
                    jo=ja.getJSONObject(i);
                    String name=jo.getString("name");
                    String userName=jo.getString("user_name");
                    System.out.println(name);
                }*/




                return  stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected  void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        protected void onPostExecute(String result) {
            /*TextView textView = (TextView) findViewById(R.id.textview);
            textView.setText("");*/
            System.out.println(result);
            json_string = result;

        }
    }

    public void parseJSON()
    {
        //new BackgroundTask().execute();
        if(json_string==null)
        {
            Toast.makeText(getApplicationContext(),"First get the json",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(this,Acitivity3.class);
            intent.putExtra("json_data",json_string);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein,R.anim.fadein);
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        homeactivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
