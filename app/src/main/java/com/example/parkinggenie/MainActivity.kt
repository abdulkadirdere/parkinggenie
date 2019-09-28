package com.example.parkinggenie

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login()
        forgotPassword()
    }

    private fun login() {
        login.setOnClickListener {

            if (username.length() > 0 && password.length() > 0) {
                val intent = Intent(this, ocr_page::class.java)
                val username = findViewById<EditText>(R.id.username).text
                val password = findViewById<EditText>(R.id.password).text
                intent.putExtra("Username", username.toString())
                intent.putExtra("Password", password.toString())
                startActivity(intent)
            } else if (username.length() <= 0){
                Toast.makeText(applicationContext,"Please enter username",Toast.LENGTH_SHORT).show()
                showHint()
            } else{
                Toast.makeText(applicationContext,"Please enter password",Toast.LENGTH_SHORT).show()
                showHint()
            }
        }
    }

    private fun forgotPassword(){

        val forgot = findViewById<TextView>(R.id.forgot_password)
        forgot.setOnClickListener {
            val intent = Intent(this, Forgot_password::class.java)
            startActivity(intent)
        }
    }

    private fun showHint(){
        username.hint="Please enter username"
        password.hint="Please enter password"
        username.setHintTextColor(Color.RED)
        password.setHintTextColor(Color.RED)
    }
}

// Extension function to show toast message
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
