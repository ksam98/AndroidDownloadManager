package com.example.admin.downloadurlsample;

import android.app.Dialog;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class MainActivity extends AppCompatActivity {

    EditText urlBox;
    Button downloadButton;

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
            //TODO: implement a progressBar/ProgressDialog
        }

        @Override
        protected  String doInBackground(String... aurl){
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(aurl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();

                System.out.println("Before opening output stream");

                output = new FileOutputStream("/root/sdcard/song1.mp3");

                System.out.println("After opening the output stream");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button

                    System.out.println("Entered the mighty while loop");
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        //publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }


    }
}


 /*
                //Attempting to open a connection
                URL url = new URL(aurl[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                //If connection response is not OK
                if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                    Log.e(TAG, "Server returned: " + connection.getResponseMessage());
                    //TODO: add toast message prompting change in action
                }

                */


                /*
                System.out.println("Jamendo url bois: " + aurl[0]);
                URL connection =  new URL(aurl[0]);
                ReadableByteChannel rbc = Channels.newChannel(connection.openStream());
                FileOutputStream fos = new FileOutputStream("songDwnldAttempt1.mp3");
                fos.getChannel().transferFrom(rbc, 0 , Long.MAX_VALUE);

                System.out.println("download procedure completed ijaskvsbkhcfsbjufcjhjjbudm");
                */


                /*
               System.out.println("Entering doInBackground");

            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            FileOutputStream outputStream = null;

            try{

                System.out.println("Entering doInBackground -> try catch block");
                System.out.println("URL: " + aurl[0]);
                URL url = new URL(aurl[0]);
                inputStream = url.openStream();
                urlConnection = (HttpURLConnection) url.openConnection();

                int size = urlConnection.getContentLength();

                if(urlConnection  != null){

                    System.out.println("Entering doInBackground -> try catch -> urlConnection not null");

                    String filename = "SongDwnldAttempt1.mp3";
                    System.out.println("Pre inputStream null check 0");
                    String storagePath = Environment.DIRECTORY_DOWNLOADS;
                    System.out.println("Pre inputStream null check 1");
                    File file = new File(storagePath, filename);

                    System.out.println("Pre inputStream null check 2");

                    outputStream = new FileOutputStream(file);

                    System.out.println("Pre inputStream null check 3");

                    byte[] buffer = new byte[1024];
                    long total = 0;
                    int length = 0;

                    System.out.println("Pre inputStream null check");

                    if(inputStream != null){

                        System.out.println("Entering doInBackground -> try catch -> url connection not null -> input Stream not null");
                        while((length = inputStream.read(buffer)) > 0){
                            total += length;
                            //TODO: publish progress through progress dialog box
                            System.out.println("Current progress: " + (int)((total*100)/size));
                            outputStream.write(buffer, 0, length);
                        }
                    }

                    if(outputStream != null){
                        outputStream.close();
                    }
                }


            } catch (Exception e){
                //TODO: handle exception
            } finally{
                try{
                    if(inputStream != null)
                        inputStream.close();

                    if(outputStream != null)
                        outputStream.close();
                } catch (IOException e){
                    //TODO: handle exception
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String file_url) {
            //TODO: close progress bar/dialog box
        }
                 */