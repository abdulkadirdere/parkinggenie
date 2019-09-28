package com.example.parkinggenie

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_verified_page.*
import android.net.Uri
import android.util.Log
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

        val name: String? = intent.getStringExtra("name_field")
        val surname: String? = intent.getStringExtra("surname_field")
        val contact: String? = intent.getStringExtra("contact_number")
        val permit: String? = intent.getStringExtra("permit_type")
        val carMake: String? = intent.getStringExtra("make")
        val carModel: String? = intent.getStringExtra("model")
        val carColor: String? = intent.getStringExtra("color")

        name_field.text = name
        surname_field.text = surname
        contact_field.text = contact
        permit_field.text = permit
        make_field.text = carMake
        model_field.text = carModel
        color_field.text = carColor

        var email_add = "email"


        if (license == "1988"){
            email_add = "james.doe@gmail.com"
        } else {
            email_add = "jane.smith@gmail.com"
        }


        issue_fine.setOnClickListener {
            val intent = Intent(this, infringement_page::class.java)
            intent.putExtra("license", license)
            intent.putExtra("email_add", email_add)
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
