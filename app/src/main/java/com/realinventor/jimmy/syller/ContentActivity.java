package com.realinventor.jimmy.syller;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    public String sub_code = "";
    private ArrayList<String> text;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        String filename = "null";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                filename = "null";
            } else {
                filename = extras.getString("filename");
            }
        } else {
            filename = savedInstanceState.getString("filename");
        }

        Toast toast = Toast.makeText(getApplicationContext(), filename, Toast.LENGTH_SHORT);
        toast.show();

        filename = filename.trim();
        Log.e("filename :", filename);

        sub_code = filename;


        //download section

        final TextView mTextView = (TextView) findViewById(R.id.myTextView);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url_get ="https://polytechnicsyllabus.firebaseio.com/.json";

        //exp
        String url_get = "https://polytechnicsyllabus.firebaseio.com/syllabus/";
        url_get += filename;
        url_get += ".json";

        Log.e("url_get", url_get);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_get,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //mTextView.setText("Response is: "+ jsons);

                        //Parsing JSON
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //JSONObject syllabus = jsonObject.getJSONObject("syllabus");
                            //JSONObject jsonObject1 = syllabus.getJSONObject("1001");
                            String subject = jsonObject.getString("subject");
                            String url = jsonObject.getString("url");
                            Log.e("Data", subject);
                            Log.e("Url", url);
                            mTextView.setText("Please wait while the download is complete.");

                            startDownload(url);


                            //Go to ViewActivity


                        } catch (JSONException e) {
                            e.printStackTrace();
                            mTextView.setText("Something went wrong! Check your internet connection.");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("Something went wrong! Check your internet connection.");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    private void startDownload(String url) {
        //String url = "http://farm1.static.flickr.com/114/298125983_0e4bf66782_b.jpg";
        new DownloadFileAsync().execute(url);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);


                //create a directory for Polytalk
                File direct = new File(Environment.getExternalStorageDirectory() + "/Polytalk");

                if (!direct.exists()) {
                    if (direct.mkdir()) ; //directory is created;
                }


                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory() + "/Polytalk"
                        + "/" + sub_code + ".pdf");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.d("Error :", "" + e);
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);

            TextView myText = (TextView)findViewById(R.id.myTextView);
            myText.setText("Download Complete!");
            //Go to ViewActivity
            Intent intent = new Intent(ContentActivity.this, ViewActivity.class);
            intent.putExtra("sub_code_user", sub_code);
            startActivity(intent);
            ContentActivity.this.finish();

        }
    }


}
