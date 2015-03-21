package com.attendance.student.itu.studentattendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ClassListActivity extends Activity implements android.widget.AdapterView.OnItemClickListener {
    String [] array = {"Sun", "Mon", "Tuesday"};
    ListView list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list1);
        Toast.makeText(getApplicationContext(),"I am ClassListactivity", Toast.LENGTH_SHORT).show();

      //  list = (ListView)findViewById(R.id.listView);
        Bundle b = getIntent().getExtras();
        if(b!= null)
        {
            if(b.containsKey("user_name"))
            {
                String name = b.getString("user_name");
                Toast.makeText(this,  name, Toast.LENGTH_LONG)
                        .show();
            }




            if(b.containsKey("user_password"))
            {
                String user_password = b.getString("user_password");
                Toast.makeText(this,  user_password, Toast.LENGTH_LONG)
                        .show();
            }



        }

      //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
      //  list.setAdapter(arrayAdapter);
     // list.setOnItemClickListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_class_list, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(),parent.toString(),Toast.LENGTH_SHORT).show();

        TextView tv= (TextView) view;

        Toast.makeText(this, tv.getText()+" ", Toast.LENGTH_LONG)
                .show();

        Intent i = new Intent(this,ClassDetails.class);
        startActivity(i);
    }

    public void handleRegistered(View v)
    {
     //   Toast.makeText(getApplicationContext(),"handle enrolled clicked", Toast.LENGTH_SHORT).show();

        Bundle b = getIntent().getExtras();
        String user_data_ed1 = b.getString("user_name");
        String user_password = b.getString("user_password");



        Bundle b1 = new Bundle();
        b1.putString("user_name",user_data_ed1);
        b1.putString("user_password",user_password);

       // Intent i = new Intent(this,SignedUpClassList.class);
        Intent i = new Intent(this,AllSignedUpClassList.class);
        i.putExtras(b1);
        startActivity(i);

    }

    public void handleAvailable (View v)
    {
      //  Toast.makeText(getApplicationContext(),"handle signup clicked" , Toast.LENGTH_SHORT).show();

        Bundle b = getIntent().getExtras();
        String user_data_ed1 = b.getString("user_name");
        String user_password = b.getString("user_password");



        Bundle b1 = new Bundle();
        b1.putString("user_name",user_data_ed1);
        b1.putString("user_password",user_password);

        Toast.makeText(getApplicationContext(), "username:" + user_data_ed1+ "pass:" +user_password, Toast.LENGTH_SHORT).show();

       // Intent i = new Intent(this,AvailableClassList.class);
        Intent i = new Intent(this,AllAvailableClassList.class);
        i.putExtras(b1);
        startActivity(i);
    }


    public void handleDetail (View v)
    {

        Bundle b = getIntent().getExtras();
        String user_data_ed1 = b.getString("user_name");
        String user_password = b.getString("user_password");



        Bundle b1 = new Bundle();
        b1.putString("user_name",user_data_ed1);
        b1.putString("user_password",user_password);

        Toast.makeText(getApplicationContext(), "username:" + user_data_ed1+ "pass:" +user_password, Toast.LENGTH_SHORT).show();



        //  Toast.makeText(getApplicationContext(),"handle signup clicked" , Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this,AllClassList.class);
        i.putExtras(b1);
        startActivity(i);
    }
}
