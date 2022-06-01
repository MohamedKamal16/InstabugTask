package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.Repo
import com.example.myapplication.util.Constant.IsGet

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initComponent()
        btnSet()

        if (IsGet){
           repo.getResToView(getUrl(),tvShow,handler, savedInstanceState?.getBoolean("body") ?: true)

        }else{
            repo.post(getUrl(),gePostData(),handler,tvShow, savedInstanceState?.getBoolean("body") ?: true,this)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun btnSet() {
        btnBody.setOnClickListener {
            if (IsGet){
                IsBody = true
                repo.getResToView(getUrl(),tvShow,handler,IsBody)
            }else{
                IsBody = true
                repo.post(getUrl(),gePostData(),handler,tvShow,IsBody,this)
            }
        }

        btnHeader.setOnClickListener {
            if (IsGet){
                IsBody = false
                repo.getResToView(getUrl(),tvShow,handler,IsBody)
            }else{
                IsBody = false
                repo.post(getUrl(),gePostData(),handler,tvShow,IsBody,this)
            }
        }
    }

    private fun getUrl(): String {
        val intent = intent
        val edUrl = intent.getStringExtra("URL")
        return edUrl.toString()
    }
    private fun gePostData(): String {
        val intent = intent
        val body = intent.getStringExtra("body")
        return body.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("body", IsBody)
        outState.putString("GET", tvShow.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        IsBody = savedInstanceState.getBoolean("body")
        tvShow.text = savedInstanceState.getString("GET")
    }

    private fun initComponent() {
        btnBody = findViewById(R.id.btn_body)
        btnHeader = findViewById(R.id.btn_header)
        tvShow = findViewById(R.id.textView)
        handler = Handler(Looper.getMainLooper())
        repo=Repo(this)
    }


    private lateinit var btnBody: Button
    private lateinit var btnHeader: Button
    private lateinit var tvShow: TextView
    lateinit var handler: Handler
    lateinit var repo: Repo
    var IsBody = true


}