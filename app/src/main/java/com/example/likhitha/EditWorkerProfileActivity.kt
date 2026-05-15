package com.example.likhitha

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class EditWorkerProfileActivity : AppCompatActivity() {

    private lateinit var imageProfile: ImageView

    private var imageUri: Uri? = null

    companion object {

        const val PICK_IMAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_edit_worker_profile
        )

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
            findViewById<Switch>(
                R.id.switchAvailability
            )

        val btnChooseImage =
            findViewById<Button>(
                R.id.btnChooseImage
            )

        val btnUpdate =
            findViewById<Button>(
                R.id.btnUpdateProfile
            )

        imageProfile =
            findViewById(R.id.imageProfile)

        val oldPhone =
            intent.getStringExtra("phone") ?: ""

        val workersRef =
            FirebaseDatabase.getInstance()
                .getReference("Workers")

        // LOAD WORKER DATA

        workersRef.child(oldPhone)
            .get()
            .addOnSuccessListener { snapshot ->

                if (snapshot.exists()) {

                    val name =
                        snapshot.child("name")
                            .value.toString()

                    val work =
                        snapshot.child("work")
                            .value.toString()

                    val phone =
                        snapshot.child("phone")
                            .value.toString()

                    val location =
                        snapshot.child("location")
                            .value.toString()

                    val rate =
                        snapshot.child("rate")
                            .value.toString()

                    val status =
                        snapshot.child("status")
                            .value.toString()

                    val image =
                        snapshot.child("image")
                            .value.toString()

                    editName.setText(name)

                    editWork.setText(work)

                    editPhone.setText(phone)

                    editLocation.setText(location)

                    editRate.setText(rate)

                    switchAvailability.isChecked =
                        status == "Online"

                    try {

                        if (
                            image.isNotEmpty() &&
                            image.startsWith("content://")
                        ) {

                            imageUri =
                                Uri.parse(image)

                            imageProfile.setImageURI(
                                imageUri
                            )

                        } else {

                            imageProfile.setImageResource(
                                R.drawable.ic_launcher_foreground
                            )
                        }

                    } catch (e: Exception) {

                        imageProfile.setImageResource(
                            R.drawable.ic_launcher_foreground
                        )
                    }
                }
            }

        // CHOOSE IMAGE

        btnChooseImage.setOnClickListener {

            val galleryIntent =
                Intent(
                    Intent.ACTION_OPEN_DOCUMENT,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )

            galleryIntent.addFlags(
                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            )

            galleryIntent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            startActivityForResult(
                galleryIntent,
                PICK_IMAGE
            )
        }

        // UPDATE PROFILE

        btnUpdate.setOnClickListener {

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

            // GET OLD RATING DATA

            workersRef.child(oldPhone)
                .get()
                .addOnSuccessListener { snapshot ->

                    val rating =
                        snapshot.child("rating")
                            .value.toString()

                    val totalRating =
                        snapshot.child("totalRating")
                            .value.toString()

                    val ratingCount =
                        snapshot.child("ratingCount")
                            .value.toString()

                    val workerData =
                        hashMapOf(
                            "name" to name,
                            "work" to work,
                            "phone" to phone,
                            "location" to location,
                            "rate" to rate,
                            "status" to status,
                            "image" to image,
                            "rating" to rating,
                            "totalRating" to totalRating,
                            "ratingCount" to ratingCount
                        )

                    // SAVE UPDATED DATA

                    workersRef.child(phone)
                        .setValue(workerData)
                        .addOnSuccessListener {

                            // DELETE OLD NODE IF PHONE CHANGED

                            if (oldPhone != phone) {

                                workersRef.child(oldPhone)
                                    .removeValue()
                            }

                            Toast.makeText(
                                this,
                                "Profile Updated Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            // OPEN UPDATED PROFILE

                            val intent =
                                Intent(
                                    this,
                                    WorkerProfileActivity::class.java
                                )

                            intent.putExtra(
                                "phone",
                                phone
                            )

                            startActivity(intent)

                            finish()
                        }

                        .addOnFailureListener {

                            Toast.makeText(
                                this,
                                "Update Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
        }
    }

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
            data != null
        ) {

            try {

                imageUri = data.data

                imageUri?.let {

                    contentResolver.takePersistableUriPermission(
                        it,
                        FLAG_GRANT_READ_URI_PERMISSION
                    )

                    imageProfile.setImageURI(it)
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Image load failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}