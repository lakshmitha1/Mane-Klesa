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
                Intent.ACTION_GET_CONTENT

            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select Image"
                ),
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

            val sharedPreferences =
                getSharedPreferences(
                    "WorkersData",
                    MODE_PRIVATE
                )

            val oldWorkers =
                sharedPreferences.getString(
                    "workers",
                    ""
                ) ?: ""

            // CHECK DUPLICATE PHONE
            val workersList =
                oldWorkers.split("###")

            for (worker in workersList) {

                if (
                    worker.contains("Phone:$phone")
                ) {

                    Toast.makeText(
                        this,
                        "Profile Already Exists With This Phone Number",
                        Toast.LENGTH_LONG
                    ).show()

                    return@setOnClickListener
                }
            }

            // SAVE WORKER
            val worker =
                "Name:$name|" +
                        "Work:$work|" +
                        "Phone:$phone|" +
                        "Location:$location|" +
                        "Rate:$rate|" +
                        "Status:$status|" +
                        "Image:$image|" +
                        "Rating:0|" +
                        "TotalRating:0|" +
                        "RatingCount:0"

            val newWorkers =
                oldWorkers + worker + "###"

            sharedPreferences.edit()
                .putString(
                    "workers",
                    newWorkers
                )
                .apply()

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

            imageUri = data.data

            imageProfile.setImageURI(
                imageUri
            )

            try {

                contentResolver.takePersistableUriPermission(
                    imageUri!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}