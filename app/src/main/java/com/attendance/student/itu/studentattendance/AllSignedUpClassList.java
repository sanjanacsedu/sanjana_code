package com.attendance.student.itu.studentattendance;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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


public class AllSignedUpClassList extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView all_class_list ;
    String user_password ;
    String user_data_ed1;
    private ProgressDialog pDialog ;
    static String link;
    String result = null;
    String[] TITLES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_signed_up_class_list);
        all_class_list = (ListView)findViewById(R.id.listView5);
        Bundle b = getIntent().getExtras();
        user_data_ed1 = b.getString("user_name");
        user_password = b.getString("user_password");
        Toast.makeText(getApplicationContext(), "username:" + user_data_ed1 + "pass:" + user_password, Toast.LENGTH_SHORT).show();

        new MyAsyncTask().execute(user_data_ed1,user_password);

        //all_class_list.setOnItemClickListener(this);

        all_class_list.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_class_list, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        // TextView tv= (TextView) view;
        String label = parent.getItemAtPosition(position).toString();

        // Toast.makeText(this, tv.getText() + " ", Toast.LENGTH_LONG)
        //        .show();

        TextView tv = (TextView) view;

       // Toast.makeText(this, "row number:   " + position, Toast.LENGTH_SHORT)
        //        .show();

        // TODO Auto-generated method stub
        AlertDialog.Builder adb = new AlertDialog.Builder(
                AllSignedUpClassList.this);
        adb.setTitle("Are you sure want to drop this course?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                Toast.makeText(getApplicationContext(), "Current position "+position,
                        Toast.LENGTH_SHORT).show();



            }
        });

        adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();

            }
        });

        adb.show();

    }


    class MyAsyncTask extends AsyncTask<String,String,String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(
                    AllSignedUpClassList.this);
            pDialog.setMessage("Loading all courses ");
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
            Log.d("----Linkkkkk-----", link);
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

            JSONObject jsonObject;
            JSONArray jsonArray;
            try{
                Log.v("JSONParser RESULT",result);
                //jsonObject = new JSONObject(result);
                jsonArray = new JSONArray(result);
                Log.i("API result", result);


                int num = jsonArray.length();


                TITLES = new String[num];

                Log.v("--------ARRAY-------","len"+num);
                for(int i=0;i<jsonArray.length();i++){


                    String title = jsonArray.getJSONObject(i).getString("ClassName");

                    TITLES[i] = title ;

                    //   Log.v("---------course name-------------","course name"+audi.getCourse_name());
                    //   Log.v("---------professor name-------------","progessor name" +audi.getProfessor_name());



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

            return null;
        }






        /**
         * After completing background task Dismiss the progress dialog
         * **/

        @Override
        protected void onPostExecute(String result){

            Log.d("--onPostExecute--Result-----",""+result);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AllSignedUpClassList.this.getApplicationContext(),android.R.layout.simple_list_item_1,TITLES);

            // ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AllClassList.this.getApplicationContext(),android.R.layout.simple_list_item_2,TITLES);
            all_class_list.setAdapter(adapter1);

            pDialog.dismiss();


        }



    }



}