package com.example.parkinggenie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ocr_page.*
import kotlinx.android.synthetic.main.activity_verified_page.*
import android.net.Uri


class verified_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verified_page)

        issue_fine.setOnClickListener {
            val intent = Intent(this, infringement_page::class.java)
            startActivity(intent)
        }


        val phoneNumber = "072 123 1234";
        call.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL);
            callIntent.data = Uri.parse("tel:" + phoneNumber)
            startActivity(callIntent)
        }

    }
}
