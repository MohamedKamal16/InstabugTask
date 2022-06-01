package com.example.myapplication.model

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.model.db.DbAdabter
import com.example.myapplication.model.db.RequestData
import com.example.myapplication.model.entity.Header
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class Repo(context: Context) {
    val dpAdapter=DbAdabter(context)
    var header = Header()
    private val TAG = "Request"
    private var response = ""
    var postResult = " "

    fun insertInTable(edUrl: String?,json:String?){
        dpAdapter.insert(RequestData(
            "GET",
            edUrl,
            json
        ))
    }

    fun getResToView(edUrl: String?, res: TextView, handler: Handler, IsBody: Boolean) {
        Thread {
            kotlin.run {
                //get responce
                val json = getResponse(edUrl, IsBody)
                //will insert more than one time //ToDO solve
                insertInTable(edUrl,json)

                handler.post {
                    //put view
                    res.text = json
                }
            }
        }.start()

    }


    private fun getResponse(edUrl: String?, IsBody: Boolean): String? {
        try {
            val url = URL(edUrl)
            val conn = url.openConnection() as HttpURLConnection
            try {
                //request type
                conn.requestMethod = "GET"
                //header
                setHeader(conn, header.keys, header.value)

                val header = conn.headerFields
                Log.w(TAG, "The header is $header")
                val responseCode = conn.responseCode
                Log.w(TAG, "The result is $responseCode")
                //check if http link is correct
                if (responseCode == 200) {
                    return if (IsBody) { // if i want to show body
                        val inputStream = BufferedInputStream(conn.inputStream)
                        response = convertStreamToString(inputStream).toString()
                        Log.w(TAG, "The response is $response")
                        response
                    } else {
                        var string = " "
                        var i = 1
                        for ((key, value) in header) {
                            string += (" $i : $key  :  $value  \n")
                            i++
                        }
                        string
                    }
                } else {
                    return responseCode.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                // this is done so that there are no open connections left when this task is going to complete
                conn.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun convertStreamToString(inputStream: InputStream): String? {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var i: String?
        try {
            while (bufferedReader.readLine().also { i = it } != null) {
                sb.append(i).append('\n')
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }


    private fun setHeader(
        connection: HttpURLConnection,
        keys: MutableList<String>,
        value: MutableList<String>
    ) {
        var i = 0
        while (i < keys.size) {
            connection.setRequestProperty(keys[i], value[i])
            i++
        }
    }


    fun splitString(item: String?, index: Int): String? {
        val arr = item?.split("+")?.toTypedArray()
        return arr?.get(index)
    }



    fun post(edUrl: String?, et: String, handler: Handler, res: String, IsBody: Boolean, context: Context) {
        Thread {
            kotlin.run {
                //post response
                val text=postReq(edUrl, et)
                //split response
                val code = splitString(text, 0)
                val json = splitString(text, 1)

                //will insert more than one time //ToDO solve also
                insertInTable(edUrl,json)

                handler.post {
                    Toast.makeText(context, code, Toast.LENGTH_SHORT).show()
                    text
                }
            }
        }.start()
    }

    private fun postReq(vararg string: String?): String? {
        val myURL = string[0]
        val myData = string[1]
        try {
            val url = URL(myURL)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            setHeader(conn, header.keys, header.value)

            try {
                //to tell the connection object that we will be wrting some data on the server and then will fetch the output result
                conn.doOutput = true
                // this is used for just in case we don't know about the data size associated with our request
                conn.setChunkedStreamingMode(0)
                // to write tha data in our request
                val outputStream: OutputStream =
                    BufferedOutputStream(conn.outputStream)
                val outputStreamWriter = OutputStreamWriter(outputStream)
                outputStreamWriter.write(myData)
                outputStreamWriter.flush()
                outputStreamWriter.close()
                // to get header
                val header = conn.headerFields
                // to log the response code of your request
              //  Log.e(TAG, "MyHttpRequestTask doInBackground : " + conn.responseCode)
                // to log the response message from your server after you have tried the request.
             //   Log.e(TAG, "MyHttpRequestTask doInBackground : " + conn.responseMessage)

                if (conn.responseCode == 201) {
                        val inputStream = BufferedInputStream(conn.inputStream)
                        response = convertStreamToString(inputStream).toString()
                        postResult = "${conn.responseCode}:${conn.responseMessage} + $response"

                        var string = " "
                        var i = 1
                        for ((key, value) in header) {
                            string += (" $i : $key  :  $value  \n")
                            i++
                        }
                         return "${conn.responseCode}:${conn.responseMessage} + $response + $string"

                } else {
                  return  "${conn.responseCode}:${conn.responseMessage} + ${" "}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                // this is done so that there are no open connections left when this task is going to complete
                conn.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}
