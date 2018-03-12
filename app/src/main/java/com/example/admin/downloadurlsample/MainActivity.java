package com.example.admin.downloadurlsample;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class MainActivity extends AppCompatActivity {

    EditText urlBox;
    Button downloadButton;

    DownloadManager downloadManager;
    ProgressDialog progressBar;

    private static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private static final String TAG = "Http Connection Status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlBox = (EditText) findViewById(R.id.URL_box);
        downloadButton = (Button) findViewById(R.id.download_btn);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlBox.getText().toString();
                if(url != null){
                    startDownload(url);
                } else{
                    Toast.makeText(MainActivity.this, "Enter URL before download"
                            , Toast.LENGTH_LONG).show();

                    //TODO: chnage the toast context when exporting code
                }
            }
        });
    }

    private void startDownload(String url){
        new DownloadFileAsync().execute(url);
    }



    class DownloadFileAsync extends AsyncTask<String, String, String>{

        //ProgressBar progressBar;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar = new ProgressDialog(MainActivity.this);
            progressBar.setMessage("Downloading Song content");
            progressBar.setIndeterminate(true);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setMax(100);
            progressBar.setProgress(0);
            progressBar.show();
            //TODO: implement a progressBar/ProgressDialog

        }

        @Override
        protected  String doInBackground(String... aurl){
            InputStream input = null;
            OutputStream output = null;

            Uri music_uri = Uri.parse(aurl[0]);

            long Music_DownloadId = DownloadData(music_uri);
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            progressBar.dismiss();
        }
    }

    private long DownloadData (Uri uri) {

        long downloadReference;

        // Create request for android download manager
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("Data Download");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path within the application's external files directory
       // if(v.getId() == R.id.DownloadMusic)
        request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS,"doesn'twork.mp3");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

        /*
        Button DownloadStatus = (Button) findViewById(R.id.DownloadStatus);
        DownloadStatus.setEnabled(true);
        Button CancelDownload = (Button) findViewById(R.id.CancelDownload);
        CancelDownload.setEnabled(true);
        */
        return downloadReference;
    }
}