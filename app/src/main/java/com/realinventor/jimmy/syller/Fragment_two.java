package com.realinventor.jimmy.syller;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_two extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";

    private ArrayList<String> words;

    public Fragment_two() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_recycler_view, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new
                  MyRecyclerViewAdapter.MyClickListener() {
                      @Override
                      public void onItemClick(int position, View v) {
                          Log.i(LOG_TAG, " Clicked on Item " + position);
                          TextView codeView = (TextView) v.findViewById(R.id.code_textView);
                          String pass = ""+codeView.getText();
                          pass = pass.replaceFirst("code: ","");
                          Log.e("passvalue",pass);

                          Intent intent = new Intent(getActivity(), ContentActivity.class);
                          intent.putExtra("filename", pass);
                          startActivity(intent);

                      }
                  });

    }


    private ArrayList<DataObject> getDataSet() {

        words = new ArrayList<String>();


        String n = "-1";
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras == null) {
            n = "-1";
        } else {
            n = extras.getString("fileindex");
        }

        if(n.equals("-1")){
            Intent i = new Intent(getActivity(),Programmes.class);
            startActivity(i);
        }

        String filename = "course" + n + ".txt";

        String start_word = "Semester 2";
        String end_word = "Semester 3";

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getContext().getAssets().open(filename)));
            String mLine = reader.readLine();
            boolean reach = false;
            while (mLine != null) {
                if (mLine.equals(start_word)) {
                    reach = true;
                }
                if (mLine.equals(end_word)) {
                    reach = false;
                }
                if (reach) {
                    words.add(mLine); // process line
                }
                mLine = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        words.remove(0);



        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < words.size(); index++) {


            String str = words.get(index);
            str = str.trim();
            String[] parts = str.split("  ");
            String[] subParts = parts[0].split(" ");


            DataObject obj = new DataObject(subParts[0], "code: "+subParts[1],parts[1]);
            results.add(index, obj);
        }
        return results;
    }
}
