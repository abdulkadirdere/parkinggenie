package com.example.parkinggenie

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import kotlinx.android.synthetic.main.activity_ocr_page.*
import kotlin.properties.Delegates


class ocr_page : AppCompatActivity() {

    private var mCameraSource by Delegates.notNull<CameraSource>()
    private var textRecognizer by Delegates.notNull<TextRecognizer>()

    private val PERMISSION_REQUEST_CAMERA = 100




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr_page)

        startCameraSource()

        confirm.setOnClickListener {
            val intent = Intent(this, verified_page::class.java)
            startActivity(intent)
        }


        capture.setOnClickListener {
            val license = tv_result.text
            showDialog(license.toString())
            print(license)
        }

    }

    private fun startCameraSource() {

        //  Create text Recognizer
        textRecognizer = TextRecognizer.Builder(this).build()


        //  Init camera source to use high resolution and auto focus
        mCameraSource = CameraSource.Builder(applicationContext, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1920, 1080)
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
                        stringBuilder.append("\n")
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

            }
        }
    }

    // Method to show an alert dialog with yes, no and cancel button
    private fun showDialog(license : String) {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Recognised License Number Plate")

        builder.setMessage(license)

        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> toast("Verifying the license plate")
                DialogInterface.BUTTON_NEGATIVE -> toast("Recapture the number plate")
                DialogInterface.BUTTON_NEUTRAL -> toast("Action Cancelled")
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("Confirm"){ _, _ ->
            val intent = Intent(this, verified_page::class.java)
            intent.putExtra("license", license)
            startActivity(intent)
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
}

// Extension function to show toast message
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
