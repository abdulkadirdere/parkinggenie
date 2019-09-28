package com.example.parkinggenie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_unverified.*


class unverified_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unverified)

        val license: String? = intent.getStringExtra("license")
//        Log.d("TAG",license)
        unverified_license.text = license

        okay()
    }

    private fun okay() {
        ok_button.setOnClickListener {
            val intent = Intent(this, ocr_page::class.java)
            startActivity(intent)
        }
    }
}