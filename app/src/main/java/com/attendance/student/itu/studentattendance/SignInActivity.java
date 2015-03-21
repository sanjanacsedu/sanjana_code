package com.attendance.student.itu.studentattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class SignInActivity extends ActionBarActivity {
    EditText ed1;
    EditText ed2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    ed1 = (EditText) findViewById(R.id.txtUsername);
        ed2 = (EditText) findViewById(R.id.txtPassword);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void handleStudent (View v)
        {

            String user_data_ed1 = ed1.getText().toString();
            String user_password = ed2.getText().toString();
            Bundle b = new Bundle();
            b.putString("user_name",user_data_ed1);
            b.putString("user_password",user_password);

          //  Toast.makeText(getApplicationContext(), "I am clicked", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this,ClassListActivity.class);

            Intent intent = new Intent(this,ClassListActivity.class);
            intent.putExtras(b);
            startActivity(intent);

        }

    public void handleAdmin (View v)
    {

        String user_data_ed1 = ed1.getText().toString();
        String user_password = ed2.getText().toString();
        Bundle b = new Bundle();
        b.putString("user_name",user_data_ed1);
        b.putString("user_password",user_password);

        //  Toast.makeText(getApplicationContext(), "I am clicked", Toast.LENGTH_SHORT).show();
       // Intent intent = new Intent(this,ClassListActivity.class);
        //Intent intent = new Intent(this,CameraActivity.class);
        //Intent intent = new Intent(this,DoneActivity.class);
        Intent intent = new Intent(this,NotDoneActivity.class);


        intent.putExtras(b);
        startActivity(intent);

    }
}
