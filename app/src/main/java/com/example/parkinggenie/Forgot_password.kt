package com.example.parkinggenie

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_forgot_password.*

class Forgot_password : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        forgotPassword()
    }

    private fun forgotPassword() {
        Submit.setOnClickListener {
            var email = findViewById<EditText>(R.id.emailEditText)
            if (email.length() > 0) {
                showDialog(email.text.toString())
            } else {
                emailEditText.hint = "Please enter your email address"
                emailEditText.setHintTextColor(Color.RED)
            }
        }
    }

    // Method to show an alert dialog with yes, no and cancel button
    private fun showDialog(email: String?) {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Forgot Password?")

        builder.setMessage(email)

        // Set the alert dialog positive/yes button
        builder.setPositiveButton("Confirm") { _, _ ->
            toast("Submitted")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("Cancel") { _, _ ->
            toast("Action Cancelled")
        }


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }


}
