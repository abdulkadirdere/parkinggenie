package com.example.parkinggenie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.unverified_page.*


class unverified_page : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unverified_page)

        // get license number
        val license: String? = intent.getStringExtra("license")
        Log.d("TAG",license)
        unverified_license.text = license

        reason.text = "Unrecognised number plate. Please inform the security."

        val intent = Intent(this, ocr_page::class.java)
        startActivity(intent)
    }
}