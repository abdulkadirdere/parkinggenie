package com.example.parkinggenie

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import kotlinx.android.synthetic.main.activity_ocr_page.*
import kotlinx.android.synthetic.main.dialog_manual.view.*
import kotlin.properties.Delegates


class ocr_page : AppCompatActivity() {

    private var mCameraSource by Delegates.notNull<CameraSource>()
    private var textRecognizer by Delegates.notNull<TextRecognizer>()

    private val PERMISSION_REQUEST_CAMERA = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr_page)

        startCameraSource()

        capture.setOnClickListener {
            val license = tv_result.text
            showDialog(license.toString())
            print(license)
        }

        manualCapture()

    }

    private fun startCameraSource() {

        //  Create text Recognizer
        textRecognizer = TextRecognizer.Builder(this).build()


        //  Init camera source to use high resolution and auto focus
        mCameraSource = CameraSource.Builder(applicationContext, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1024,768)
                .setAutoFocusEnabled(true)
                .setRequestedFps(1.0f)
                .build()

        surface_camera_preview.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

            }

            override fun surfaceDestroyed(p0: SurfaceHolder?) {
                mCameraSource.stop()
            }

            @SuppressLint("MissingPermission")
            override fun surfaceCreated(p0: SurfaceHolder?) {
                try {
                    if (isCameraPermissionGranted()) {
                        mCameraSource.start(surface_camera_preview.holder)
                    } else {
                        requestForPermission()
                    }
                } catch (e: Exception) {

                }
            }
        })

        textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                val items = detections.detectedItems

                if (items.size() <= 0) {
                    return
                }

                tv_result.post {
                    val stringBuilder = StringBuilder()
                    for (i in 0 until items.size()) {
                        val item = items.valueAt(i)
                        stringBuilder.append(item.value)
//                        stringBuilder.append("\n")
                    }
                    tv_result.text = stringBuilder.toString()
                }
            }
        })
    }

    fun isCameraPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun requestForPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != PERMISSION_REQUEST_CAMERA) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (isCameraPermissionGranted()) {
                mCameraSource.start(surface_camera_preview.holder)
            } else {
                toast("Please grant permission to continue")
            }
        }
    }

    // Method to show an alert dialog with yes, no and cancel button
    private fun showDialog(license : String) {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Recognised Number Plate")

        builder.setMessage(license)

        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> toast("Verifying the number plate")
                DialogInterface.BUTTON_NEGATIVE -> toast("Recapture the number plate")
                DialogInterface.BUTTON_NEUTRAL -> toast("Action Cancelled")
            }
        }

        var name_field = "name"
        var surname_field = "surname"
        var contact_number = "number"
        var permit_type = "permit"
        var make = "make"
        var model = "model"
        var color = "color"



        // Set the alert dialog positive/yes button
        builder.setPositiveButton("Confirm") { _, _ ->
            val intent = Intent(this, verified_page::class.java)
            Log.d("TAG", license)
            intent.putExtra("license", license)

            if (license == "Hello World!") {
                name_field = "James"
                surname_field = "Doe"
                contact_number = "0721231234"
                permit_type = "Resident"
                make = "Volkswagen"
                model = "Golf"
                color = "White"

                intent.putExtra("name_field", name_field)
                intent.putExtra("surname_field", surname_field)
                intent.putExtra("contact_number", contact_number)
                intent.putExtra("permit_type", permit_type)
                intent.putExtra("make", make)
                intent.putExtra("model", model)
                intent.putExtra("color", color)
                startActivity(intent)
            } else if (license == "HM11GP"){
                name_field = "Jane"
                surname_field = "Smith"
                contact_number = "0812341234"
                permit_type = "Visitor"
                make = "Mercedes"
                model = "C200"
                color = "Red"
                intent.putExtra("license", license)
                intent.putExtra("name_field", name_field)
                intent.putExtra("surname_field", surname_field)
                intent.putExtra("contact_number", contact_number)
                intent.putExtra("permit_type", permit_type)
                intent.putExtra("make", make)
                intent.putExtra("model", model)
                intent.putExtra("color", color)
                startActivity(intent)
            } else {
                val intent2 = Intent(this, unverified_page::class.java)
                intent2.putExtra("license", license)
                Log.d("TAG2",license)
                startActivity(intent2)
            }

        }

        // Set the alert dialog negative/no button
        builder.setNegativeButton("Recapture", dialogClickListener)

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("Cancel", dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }


    private fun manualCapture(){
        manuallyCapture.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_manual, null)

            // On click listener for dialog buttons
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> toast("")
                    DialogInterface.BUTTON_NEUTRAL -> toast("")
                }
            }

            var name_field = "name"
            var surname_field = "surname"
            var contact_number = "number"
            var permit_type = "permit"
            var make = "make"
            var model = "model"
            var color = "color"

            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Manual Number Plate")
                .setPositiveButton("Confirm") { _, _ ->

                    var license = mDialogView.manual_license.text.toString()


                    val intent = Intent(this, verified_page::class.java)
                    Log.d("TAG", license)
                    intent.putExtra("license", license)

                    if (license == "HM11GP") {
                        name_field = "Jared"
                        surname_field = "Naidoo"
                        contact_number = "0796600458"
                        permit_type = "Resident"
                        make = "Volkswagen"
                        model = "Golf"
                        color = "White"

                        intent.putExtra("name_field", name_field)
                        intent.putExtra("surname_field", surname_field)
                        intent.putExtra("contact_number", contact_number)
                        intent.putExtra("permit_type", permit_type)
                        intent.putExtra("make", make)
                        intent.putExtra("model", model)
                        intent.putExtra("color", color)
                        startActivity(intent)
                    } else if (license == "Hello World!"){
                        name_field = "Jane"
                        surname_field = "Smith"
                        contact_number = "0812341234"
                        permit_type = "Visitor"
                        make = "Mercedes"
                        model = "C200"
                        color = "Red"
                        intent.putExtra("license", license)
                        intent.putExtra("name_field", name_field)
                        intent.putExtra("surname_field", surname_field)
                        intent.putExtra("contact_number", contact_number)
                        intent.putExtra("permit_type", permit_type)
                        intent.putExtra("make", make)
                        intent.putExtra("model", model)
                        intent.putExtra("color", color)
                        startActivity(intent)
                    } else {
                        val intent2 = Intent(this, unverified_page::class.java)
                        intent2.putExtra("license", license)
                        Log.d("TAG2",license)
                        startActivity(intent2)
                    }

                }
                .setNeutralButton("Cancel",dialogClickListener)
            val mAlertDialog = mBuilder.show()
        }
    }
}
