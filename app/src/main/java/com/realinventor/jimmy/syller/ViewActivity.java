package com.realinventor.jimmy.syller;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;

public class ViewActivity extends AppCompatActivity {

    public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";

    String sub_code_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Log.e("Attention ", "Im here");


        sub_code_user = getIntent().getStringExtra("sub_code_user");


        if (savedInstanceState == null) {

            PdfRendererBasicFragment pdfFragment = new PdfRendererBasicFragment();
            Bundle args = new Bundle();
            args.putString("sub_code_user", sub_code_user);
            pdfFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .add(R.id.container, pdfFragment,
                            FRAGMENT_PDF_RENDERER_BASIC)
                    .commit();
        }
    }


    // Menu handling
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_semester, menu);
        // Return true to display menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                File outputFile = new File(Environment.getExternalStorageDirectory
                        ()+"/Polytalk/"+ sub_code_user+".pdf");
                Uri uri = Uri.fromFile(outputFile);
                Log.e("Uri ", ""+uri);

                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                //share.setPackage("com.whatsapp");

                startActivity(Intent.createChooser(share, "Share this file"));
                break;
            case R.id.action_info:
                new AlertDialog.Builder(this)
                        .setMessage("This App was developed by JIMMY JOSE.\n\nSend your feedback to jimmyjose009@gmail.com.\n\nIcon credits to Flaticon.com")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
        }
        return super.onOptionsItemSelected(item);
    }

}
