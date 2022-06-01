package com.example.myapplication;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
/*
 private class MyHttpRequestTask :
       // AsyncTask<String?, Int?, String?>() {
        private  val TAG = "MainActivity"
        override fun doInBackground(vararg params: String?): String? {
            val my_url = params[0]
            val my_data = params[1]
            try {
                val url = URL(my_url)
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                // setting the  Request Method Type
                httpURLConnection.setRequestMethod("POST")
                // adding the headers for request
                httpURLConnection.setRequestProperty("Content-Type", "application/json")
                try {
                    //to tell the connection object that we will be wrting some data on the server and then will fetch the output result
                    httpURLConnection.setDoOutput(true)
                    // this is used for just in case we don't know about the data size associated with our request
                    httpURLConnection.setChunkedStreamingMode(0)

                    // to write tha data in our request
                    val outputStream: OutputStream =
                        BufferedOutputStream(httpURLConnection.getOutputStream())
                    val outputStreamWriter = OutputStreamWriter(outputStream)
                    outputStreamWriter.write(my_data)
                    outputStreamWriter.flush()
                    outputStreamWriter.close()

                    // to log the response code of your request
                    Log.w(TAG, "MyHttpRequestTask doInBackground : " + httpURLConnection.responseCode
                    )
                    // to log the response message from your server after you have tried the request.
                    Log.w(TAG, "MyHttpRequestTask doInBackground : " + httpURLConnection.responseMessage
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    // this is done so that there are no open connections left when this task is going to complete
                    httpURLConnection.disconnect()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }


/*
private class MyHttpRequestTask extends AsyncTask {

@Override
protected String doInBackground(String… params) {
String my_url = params[0];
String my_data = params[1];
String final_response=””;
try {
URL url = new URL(my_url);
HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
// setting the Request Method Type
httpURLConnection.setRequestMethod(“POST”);
// adding the headers for request
httpURLConnection.setRequestProperty(“Content-Type”, “application/json”);
try {
//to tell the connection object that we will be wrting some data on the server and then will fetch the output result
httpURLConnection.setDoOutput(true);
// this is used for just in case we don’t know about the data size associated with our request
httpURLConnection.setChunkedStreamingMode(0);

// to write the data in our request
OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
outputStreamWriter.write(my_data);
outputStreamWriter.flush();
outputStreamWriter.close();

// to read the response data from our request
InputStream inputStream;
if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
}else {
inputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
}

BufferedReader reader = new BufferedReader(new InputStreamReader(
inputStream, “UTF-8”), 8);
StringBuilder sb = new StringBuilder();
String line = null;
while ((line = reader.readLine()) != null) {
sb.append(line + “\n”);
}
inputStream.close();
final_response = sb.toString();

// to log the response code of your request
Log.d(ApplicationConstant.TAG, “MyHttpRequestTask doInBackground : ” + httpURLConnection.getResponseCode());
// to log the response message from your server after you have tried the request.
Log.d(ApplicationConstant.TAG, “MyHttpRequestTask doInBackground : ” + httpURLConnection.getResponseMessage());

} catch (Exception e) {
e.printStackTrace();
} finally {
// this is done so that there are no open connections left when this task is going to complete
httpURLConnection.disconnect();
}

} catch (Exception e) {
e.printStackTrace();
}

return final_response;
}*/