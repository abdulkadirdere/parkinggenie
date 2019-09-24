package com.example.parkinggenie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_infringement_page.*
import kotlinx.android.synthetic.main.activity_ocr_page.*
import kotlinx.android.synthetic.main.activity_verified_page.*

class infringement_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infringement_page)

        val license: String? = intent.getStringExtra("license")
        Log.d("TAG",license)
        license_plate3.text = license


    }
}
