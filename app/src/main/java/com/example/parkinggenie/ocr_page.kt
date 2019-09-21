package com.example.parkinggenie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ocr_page.*
import android.content.Intent

class ocr_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr_page)


        confirm.setOnClickListener {
            val intent = Intent(this, verified_page::class.java)
            startActivity(intent)
        }

    }
}
