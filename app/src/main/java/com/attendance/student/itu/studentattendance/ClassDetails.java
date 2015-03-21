package com.attendance.student.itu.studentattendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ClassDetails extends ActionBarActivity {

    String course;

    String result = null;
    static String link;

    TextView tx1 , tx2 , tx3;

    //variables declaration

    // ListView listViewClass;

    private ProgressDialog pDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        tx1 = (TextView) findViewById(R.id.textView5);
        tx2 = (TextView) findViewById(R.id.textView6);
        tx3 = (TextView) findViewById(R.id.textView7);

        Bundle b = getIntent().getExtras();
        course = b.getString("course");

        Toast.makeText(getApplicationContext(), "---coursename:" + course , Toast.LENGTH_SHORT).show();

        new MyAsyncTask().execute(course);






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signed_up_class_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.sign_out){

            Intent i = new Intent(this, SignInActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    public void handleDrop(View v)
    {
        Toast.makeText(this, "Drop clicked", Toast.LENGTH_SHORT).show();

    }

    class MyAsyncTask extends AsyncTask<String,String,String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(
                    ClassDetails.this);
            pDialog.setMessage("Loading signedup courses for "+course);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }



        /**
         * getting all stored website from SQLite
         * */
        @Override
        protected String doInBackground(String... args) {



            link = "http://aas.ca3rau.com/api/classes";
            Log.d("----Linkkkkk-----",link);
            AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");
            HttpGet request = new HttpGet(link);
            //added by Khoa
            request.addHeader("Content-Type", "application/json");

            InputStream inputStream = null;
            //String result = null;

            try{
                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();
                inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),8);
                StringBuilder theStringBuilder = new StringBuilder();
                String line = null;

                while((line =reader.readLine())!=null){
                    theStringBuilder.append(line + "\n");
                }
                result = theStringBuilder.toString();
            }
            catch(Exception e){
                e.printStackTrace();
            }

            finally{
                try{
                    Log.d("----Result-----",result);
                    if(inputStream!=null) inputStream.close();
                }
                catch(Exception e){

                }
            }
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    try{
                        Log.v("JSONParser RESULT",result);
                        //jsonObject = new JSONObject(result);
                        jsonArray = new JSONArray(result);
                        Log.i("API result", result);


                        int num = jsonArray.length();





                        String[] TITLES;
                        int flag = 0;

                        TITLES = new String[num];

                        Log.v("--------ARRAY-------","len"+num);
                        for(int i=0;i<jsonArray.length();i++){


                            String class_name = jsonArray.getJSONObject(i).getString("ClassName");


                            String professor_mame = jsonArray.getJSONObject(i).getString("Professor");

                            String start_time  = jsonArray.getJSONObject(i).getString("StartTime");

                            if(class_name.contains(course))
                            {
                                tx1.setText("Course name: "+ class_name);
                                tx2.setText("Professor name: "+professor_mame);
                                tx3.setText("Start Time: "+start_time);
                                 flag = 1;
                            }
                            if(flag == 1) break;

                            Log.v("---------course name-------------","course name"+class_name);
                            Log.v("---------professor name-------------","progessor name" +professor_mame);



                        }


                        Log.d("--onPostExecute--Result-----",""+result);
                        //   ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SignedUpClassList.this,android.R.layout.simple_list_item_1,TITLES);
                        //   enrolled_class_list.setAdapter(arrayAdapter);

                        Log.v("AllOK", "fine");







                    }
                    catch(JSONException e){
                        e.printStackTrace();
                    }

                    // Log.v("------tttttt------", TITLES.toString());


                    //registerForContextMenu(lv);
                } //run
            });
            return null;
        }






        /**
         * After completing background task Dismiss the progress dialog
         * **/

        @Override
        protected void onPostExecute(String result){

            pDialog.dismiss();



        }



    }



}
