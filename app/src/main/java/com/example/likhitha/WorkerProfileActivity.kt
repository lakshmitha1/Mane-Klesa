package com.example.likhitha

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WorkerProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_worker_profile)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }

        val imageProfile =
            findViewById<ImageView>(R.id.imageProfile)

        val textName =
            findViewById<TextView>(R.id.textName)

        val textWork =
            findViewById<TextView>(R.id.textWork)

        val textPhone =
            findViewById<TextView>(R.id.textPhone)

        val textLocation =
            findViewById<TextView>(R.id.textLocation)

        val textStatus =
            findViewById<TextView>(R.id.textStatus)

        val textRating =
            findViewById<TextView>(R.id.textRating)

        // GET DATA

        val name =
            intent.getStringExtra("name") ?: ""

        val work =
            intent.getStringExtra("work") ?: ""

        val phone =
            intent.getStringExtra("phone") ?: ""

        val location =
            intent.getStringExtra("location") ?: ""

        val status =
            intent.getStringExtra("status") ?: ""

        val image =
            intent.getStringExtra("image") ?: ""

        val rating =
            intent.getStringExtra("rating") ?: "0"

        // SET TEXT

        textName.text =
            "Name: $name"

        textWork.text =
            "Work: $work"

        textPhone.text =
            "Phone: $phone"

        textLocation.text =
            "Location: $location"

        textStatus.text =
            "Status: $status"

        textRating.text =
            "Rating: $rating ⭐"

        // IMAGE LOAD

        if (
            image.isNotEmpty() &&
            image != "null"
        ) {

            try {

                imageProfile.setImageURI(
                    Uri.parse(image)
                )

            } catch (e: Exception) {

                imageProfile.setImageResource(
                    R.drawable.worker1
                )
            }

        } else {

            imageProfile.setImageResource(
                R.drawable.worker1
            )
        }
    }
}
