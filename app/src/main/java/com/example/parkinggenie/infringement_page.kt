package com.example.parkinggenie

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_infringement_page.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class infringement_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infringement_page)

        // get license number
        val license: String? = intent.getStringExtra("license")
        Log.d("TAG",license)
        license_plate3.text = license

        // get email address
        val email: String? = intent.getStringExtra("email_add")
        Log.d("TAG",email)
        email_address.text = email

        // set local date and time
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formatted = current.format(formatter)
        currentDate.text = formatted

        confirm_fine.setOnClickListener {
            showDialog(license.toString())
        }
    }

    // Method to show an alert dialog with yes, no and cancel button
    private fun showDialog(license : String) {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Issue Fine")

        builder.setMessage(license)

        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> toast("Fined issued")
//                DialogInterface.BUTTON_NEGATIVE -> toast("Recapture the number plate")
                DialogInterface.BUTTON_NEUTRAL -> toast("Action Cancelled")
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("Confirm"){ _, _ ->
            val intent = Intent(this, ocr_page::class.java)
            startActivity(intent)
        }

//        // Set the alert dialog negative/no button
//        builder.setNegativeButton("Recapture", dialogClickListener)

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("Cancel", dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }
}
