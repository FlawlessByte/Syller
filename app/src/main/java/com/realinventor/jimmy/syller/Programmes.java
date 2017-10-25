package com.realinventor.jimmy.syller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class Programmes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programmes);

        getSupportActionBar().setTitle("Courses");

        final String[] prog = {"Architecture",
                "Automobile Engineering",
                "Bio-Medical Engineering",
                "Chemical Engineering",
                "Civil Engineering",
                "Commercial Practice",
                "Computer Application & Business Management",
                "Computer Engineering",
                "Computer Hardware Engineering",
                "Electrical & Electronics Engineering",
                "Electronics & Communication Engineering",
                "Electronics Engineering",
                "Information Technology",
                "Instrumentation Engineering",
                "Mechanical Engineering",
                "Polymer Technology",
                "Printing Technology",
                "Textile Technology",
                "Tool & Die Engineering",
                "Wood & Paper Technology"};

        ArrayList<String> words = new ArrayList<String>();
        for (int i=0; i<prog.length; i++){
            words.add(prog[i]);
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);

        ListView listView = (ListView) findViewById(R.id.list);

        /*AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(itemsAdapter);
        animationAdapter.setAbsListView(listView);*/

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast toast = Toast.makeText(getApplicationContext(), prog[i], Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(Programmes.this, SemesterActivity.class);
                Log.e("Logging i :", ""+i);
                intent.putExtra("fileindex", ""+i);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.warning)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Programmes.this, AdsActivity.class);
                        startActivity(intent);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
