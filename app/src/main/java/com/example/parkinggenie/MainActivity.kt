package com.example.parkinggenie

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.red
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login()
    }

    private fun login() {
        login.setOnClickListener {

            if (username.length() > 0 && password.length() > 0) {
                val intent = Intent(this, ocr_page::class.java)
                startActivity(intent)
            } else {
                username.hint="Please enter username"
                password.hint="Please enter password"
                username.setHintTextColor(Color.RED)
                password.setHintTextColor(Color.RED)
            }
        }
    }

}
