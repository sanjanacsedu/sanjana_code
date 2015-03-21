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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.ArrayList;


public class AvailableClassList extends ActionBarActivity implements AdapterView.OnItemClickListener{
    String user_password ;
    String user_data_ed1;
    String[] enrolled_class = {"soft eng" , "android"};
    ListView available_class_list ;
    String result = null;
    static String link;
    Button b;

    //variables declaration

    ListView listViewClass;

    private ProgressDialog pDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_class_list);
        available_class_list = (ListView)findViewById(R.id.listView4);
        b = (Button) findViewById(R.id.register_button);

        Bundle b = getIntent().getExtras();
        user_data_ed1 = b.getString("user_name");
        user_password = b.getString("user_password");
        Toast.makeText(getApplicationContext(), "username:" + user_data_ed1 + "pass:" + user_password, Toast.LENGTH_SHORT).show();

        new MyAsyncTask().execute(user_data_ed1,user_password);

        available_class_list.setOnItemClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_available_class_list, menu);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      //      TextView tv= (TextView) view;

      //     Toast.makeText(this, tv.getText() + " ", Toast.LENGTH_LONG)
      //             .show();


        Toast.makeText(getApplicationContext(),"clicked------", Toast.LENGTH_LONG)
                .show();


      //  Intent i = new Intent(this,QRCode_SignUpClass.class);
       // startActivity(i);


    }

    public void handleRegister(View v)
    {
        Toast.makeText(this, "Register clicked", Toast.LENGTH_SHORT).show();

    }

    class MyAsyncTask extends AsyncTask<String,String,String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(
                    AvailableClassList.this);
            pDialog.setMessage("Loading signedup courses for "+user_data_ed1);
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



                        ArrayList<SignedUpClass> arrayClass = new ArrayList<SignedUpClass>();

                        String[] TITLES;

                        TITLES = new String[num];

                        Log.v("--------ARRAY-------","len"+num);
                        for(int i=0;i<jsonArray.length();i++){


                            String title = jsonArray.getJSONObject(i).getString("ClassName");
                            String title2 = jsonArray.getJSONObject(i).getString("Professor");

                            SignedUpClass audi=new SignedUpClass(title);
                            arrayClass.add(audi);
                            TITLES[i] = title + "" + title2;

                            Log.v("---------course name-------------","course name"+audi.getCourse_name());
                            Log.v("---------professor name-------------","progessor name" +audi.getProfessor_name());



                        }

                        Log.d("--array size-----",""+arrayClass.size());
                        Log.d("--onPostExecute--Result-----",""+result);
                        //   ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SignedUpClassList.this,android.R.layout.simple_list_item_1,TITLES);
                        //   enrolled_class_list.setAdapter(arrayAdapter);

                        Log.v("AllOK", "fine");


                        ListAvailableClassAdapter adapter;
                        adapter = new ListAvailableClassAdapter(AvailableClassList.this, arrayClass);
                        available_class_list.setAdapter(adapter);

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

            //  Log.d("--onPostExecute--Result-----",""+result);
            //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EnrolledOnlyClassList.this,android.R.layout.simple_list_item_1,TITLES);
            //  enrolled_class_list.setAdapter(arrayAdapter);

            pDialog.dismiss();


        }



    }



}
