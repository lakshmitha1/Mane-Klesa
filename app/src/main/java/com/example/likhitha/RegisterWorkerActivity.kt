package com.example.likhitha

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class RegisterWorkerActivity : AppCompatActivity() {

    private lateinit var imageProfile: ImageView

    private var imageUri: Uri? = null

    companion object {

        const val PICK_IMAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register_worker)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }

        val editName =
            findViewById<EditText>(R.id.editName)

        val editWork =
            findViewById<EditText>(R.id.editWork)

        val editPhone =
            findViewById<EditText>(R.id.editPhone)

        val editLocation =
            findViewById<EditText>(R.id.editLocation)

        val editRate =
            findViewById<EditText>(R.id.editRate)

        val switchAvailability =
            findViewById<Switch>(R.id.switchAvailability)

        val btnSave =
            findViewById<Button>(R.id.btnSave)

        val btnChooseImage =
            findViewById<Button>(R.id.btnChooseImage)

        imageProfile =
            findViewById(R.id.imageProfile)

        // CHOOSE IMAGE

        btnChooseImage.setOnClickListener {

            val intent = Intent()

            intent.type = "image/*"

            intent.action =
                Intent.ACTION_OPEN_DOCUMENT

            intent.addFlags(
                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            )

            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            startActivityForResult(
                intent,
                PICK_IMAGE
            )
        }

        // SAVE PROFILE

        btnSave.setOnClickListener {

            val name =
                editName.text.toString().trim()

            val work =
                editWork.text.toString().trim()

            val phone =
                editPhone.text.toString().trim()

            val location =
                editLocation.text.toString().trim()

            val rate =
                editRate.text.toString().trim()

            val status =
                if (switchAvailability.isChecked)
                    "Online"
                else
                    "Offline"

            val image =
                imageUri?.toString() ?: ""

            // VALIDATION

            if (
                name.isEmpty() ||
                work.isEmpty() ||
                phone.isEmpty() ||
                location.isEmpty() ||
                rate.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please Fill All Fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // FIREBASE

            val workersRef =
                FirebaseDatabase.getInstance()
                    .getReference("Workers")

            // CHECK DUPLICATE PHONE

            workersRef.child(phone)
                .get()
                .addOnSuccessListener { snapshot ->

                    if (snapshot.exists()) {

                        Toast.makeText(
                            this,
                            "Profile Already Exists With This Phone Number",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {

                        val workerData =
                            hashMapOf(
                                "name" to name,
                                "work" to work,
                                "phone" to phone,
                                "location" to location,
                                "rate" to rate,
                                "status" to status,
                                "image" to image,
                                "rating" to "0",
                                "totalRating" to "0",
                                "ratingCount" to "0"
                            )

                        // SAVE TO FIREBASE

                        workersRef.child(phone)
                            .setValue(workerData)
                            .addOnSuccessListener {

                                Toast.makeText(
                                    this,
                                    "Profile Saved Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // CLEAR FIELDS

                                editName.text.clear()

                                editWork.text.clear()

                                editPhone.text.clear()

                                editLocation.text.clear()

                                editRate.text.clear()

                                switchAvailability.isChecked = false

                                imageProfile.setImageDrawable(null)

                                imageUri = null
                            }

                            .addOnFailureListener {

                                Toast.makeText(
                                    this,
                                    "Registration Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
        }
    }

    // GET SELECTED IMAGE

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (
            requestCode == PICK_IMAGE &&
            resultCode == Activity.RESULT_OK &&
            data != null &&
            data.data != null
        ) {

            try {

                imageUri = data.data

                contentResolver.takePersistableUriPermission(
                    imageUri!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                imageProfile.setImageURI(
                    imageUri
                )

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Image Load Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}