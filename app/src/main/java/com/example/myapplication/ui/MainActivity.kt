package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.util.Network
import com.example.myapplication.adapter.EditTextAdapter
import com.example.myapplication.model.Repo
import com.example.myapplication.util.Constant.IsGet
import com.example.myapplication.model.entity.InstanceHeader.header


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
        btnSet()
        clearAdapter()
        displayAdapter()


    }

    private fun btnSet() {
        btnGet.setOnClickListener {
            if (Network.isOnline(this)) {
                IsGet = true
                layout.visibility = View.VISIBLE
                recycleView.visibility = View.VISIBLE
                etBody.visibility = View.GONE
              //  btnParameter.text = getString(R.string.parameter)
                btnParameter.visibility=View.GONE

            } else {
                Toast.makeText(this, "PLEASE CHECK Your Internet Connection", Toast.LENGTH_LONG)
                    .show()
            }
        }

        btnPost.setOnClickListener {
            if (Network.isOnline(this)) {
                IsGet = false
                layout.visibility = View.VISIBLE
                recycleView.visibility = View.GONE
                etBody.visibility = View.VISIBLE
                btnParameter.visibility=View.VISIBLE
                btnParameter.text = getString(R.string.body)
            } else {
                Toast.makeText(this, "PLEASE CHECK Your Internet Connection", Toast.LENGTH_LONG)
                    .show()
            }
        }
        btnParameter.setOnClickListener {
            if (btnParameter.text==getString(R.string.body)){
                recycleView.visibility = View.GONE
                etBody.visibility = View.VISIBLE
            }
            else{
                recycleView.visibility = View.VISIBLE
                etBody.visibility = View.GONE
            }
        }

        btnHead.setOnClickListener {
                recycleView.visibility = View.VISIBLE
                etBody.visibility = View.GONE
        }

        btnConfirm.setOnClickListener {
            confirm()
        }
    }

    private fun confirm() {
        if (Network.isOnline(this)) {
            if (IsGet) {
                if (etUrl.text.isEmpty()) {
                    Toast.makeText(this, "PLEASE ENTER URL", Toast.LENGTH_LONG).show()
                } else {
                    val edUrl = etUrl.text.toString()
                    val intent = Intent(this, MainActivity2::class.java)
                    intent.putExtra("URL", edUrl)
                    startActivity(intent)
                }
            } else {
                when {
                    etUrl.text.isEmpty() -> {
                        Toast.makeText(this, "PLEASE ENTER URL", Toast.LENGTH_LONG).show()
                    }
                    etBody.text.isEmpty() -> {
                        Toast.makeText(this, "PLEASE ENTER Body", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        val edUrl = etUrl.text.toString()
                        val body=etBody.text.toString()
                        val intent = Intent(this, MainActivity2::class.java)
                        intent.putExtra("URL", edUrl)
                        intent.putExtra("body", body)
                        startActivity(intent)
                    }
                }

            }
        } else {
            Toast.makeText(this, "PLEASE CHECK Your Internet Connection", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun displayAdapter() {
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = editAdapter
    }

    fun clearAdapter() {
        editAdapter.updateEditText()
        header.keys.add(0, "")
        header.value.add(0, "")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("url", etUrl.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        etUrl.setText(savedInstanceState.getString("url"), TextView.BufferType.EDITABLE)
    }


    private fun initComponent() {
        btnPost = findViewById(R.id.btn_post)
        btnGet = findViewById(R.id.btn_get)
        btnHead = findViewById(R.id.btn_head)
        btnParameter = findViewById(R.id.btn_pa)
        btnConfirm = findViewById(R.id.btn_confirm)
        etUrl = findViewById(R.id.editTextTextMultiLine)
        etBody = findViewById(R.id.et_body)
        layout = findViewById(R.id.constrain_layout)
        recycleView = findViewById(R.id.rec)
        repo = Repo(this)
        handler = Handler(Looper.getMainLooper())
        editAdapter = EditTextAdapter(this, header.keys, header.value)
    }

    private lateinit var btnPost: Button
    private lateinit var btnGet: Button
    private lateinit var btnHead: Button
    private lateinit var btnParameter: Button
    private lateinit var btnConfirm: Button
    private lateinit var layout: ConstraintLayout
    private lateinit var etUrl: EditText
    private lateinit var etBody: EditText
    private lateinit var repo: Repo
    private lateinit var editAdapter: EditTextAdapter
    private lateinit var recycleView: RecyclerView
   // private var IsGet = true
    lateinit var handler: Handler


}