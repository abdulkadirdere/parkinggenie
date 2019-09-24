package com.example.parkinggenie

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ocr_page.*
import kotlinx.android.synthetic.main.activity_verified_page.*
import android.net.Uri
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class verified_page : AppCompatActivity() {

    private val PERMISSION_REQUEST_PHONE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verified_page)

        val license: String? = intent.getStringExtra("license")
        Log.d("TAG",license)
        license_plate.text = license

        issue_fine.setOnClickListener {
            val intent = Intent(this, infringement_page::class.java)
            intent.putExtra("license", license)
            startActivity(intent)
        }


        val phoneNumber = "072 123 1234"
        call.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // request for permission

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        PERMISSION_REQUEST_PHONE)
            } else {
                // Permission has already been granted
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                startActivity(callIntent)
            }
        }
    }


}
