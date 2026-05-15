package com.example.likhitha

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class WorkerProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val scrollView =
            ScrollView(this)

        scrollView.setBackgroundColor(
            Color.parseColor("#EDE7F6")
        )

        val mainLayout =
            LinearLayout(this)

        mainLayout.orientation =
            LinearLayout.VERTICAL

        mainLayout.setPadding(
            35,
            35,
            35,
            35
        )

        scrollView.addView(mainLayout)

        setContentView(scrollView)

        // BACK BUTTON

        val backButton =
            Button(this)

        backButton.text =
            "← Back"

        backButton.setBackgroundColor(
            Color.parseColor("#7E57C2")
        )

        backButton.setTextColor(Color.WHITE)

        backButton.setOnClickListener {
            finish()
        }

        mainLayout.addView(backButton)

        // IMAGE

        val imageView =
            ImageView(this)

        val imageParams =
            LinearLayout.LayoutParams(
                300,
                300
            )

        imageParams.gravity =
            Gravity.CENTER_HORIZONTAL

        imageParams.setMargins(
            0,
            40,
            0,
            30
        )

        imageView.layoutParams =
            imageParams

        imageView.scaleType =
            ImageView.ScaleType.CENTER_CROP

        imageView.setImageResource(
            R.drawable.worker
        )

        mainLayout.addView(imageView)

        // TITLE

        val title =
            TextView(this)

        title.text =
            "Worker Profile"

        title.textSize = 26f

        title.gravity =
            Gravity.CENTER

        title.setTextColor(
            Color.parseColor("#5B2C91")
        )

        title.setPadding(
            0,
            0,
            0,
            40
        )

        mainLayout.addView(title)

        // GET PHONE ONLY

        val phone =
            intent.getStringExtra("phone") ?: ""

        // DETAILS FUNCTION

        fun createBox(text: String): TextView {

            val tv =
                TextView(this)

            tv.text = text

            tv.textSize = 20f

            tv.setTextColor(Color.BLACK)

            tv.setPadding(
                30,
                30,
                30,
                30
            )

            tv.setBackgroundColor(
                Color.parseColor("#DCD6F7")
            )

            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            params.setMargins(
                0,
                0,
                0,
                25
            )

            tv.layoutParams =
                params

            return tv
        }

        // CREATE TEXT BOXES

        val nameText =
            createBox("Name: Loading...")

        val workText =
            createBox("Work: Loading...")

        val phoneText =
            createBox("Phone: Loading...")

        val locationText =
            createBox("Location: Loading...")

        val statusText =
            createBox("Status: Loading...")

        val rateText =
            createBox("Rate: Loading...")

        val ratingText =
            createBox("Rating: Loading... ⭐")

        mainLayout.addView(nameText)
        mainLayout.addView(workText)
        mainLayout.addView(phoneText)
        mainLayout.addView(locationText)
        mainLayout.addView(statusText)
        mainLayout.addView(rateText)
        mainLayout.addView(ratingText)

        // EDIT PROFILE BUTTON

        val editButton =
            Button(this)

        editButton.text =
            "✏ EDIT PROFILE"

        editButton.setBackgroundColor(
            Color.parseColor("#7E57C2")
        )

        editButton.setTextColor(
            Color.WHITE
        )

        editButton.textSize = 18f

        editButton.setOnClickListener {

            val intent =
                Intent(
                    this,
                    EditWorkerProfileActivity::class.java
                )

            intent.putExtra(
                "phone",
                phone
            )

            startActivity(intent)
        }

        mainLayout.addView(editButton)

        // FIREBASE

        val workersRef =
            FirebaseDatabase.getInstance()
                .getReference("Workers")

        // LOAD LIVE DATA

        workersRef.child(phone)
            .get()
            .addOnSuccessListener { snapshot ->

                if (snapshot.exists()) {

                    val name =
                        snapshot.child("name")
                            .value.toString()

                    val work =
                        snapshot.child("work")
                            .value.toString()

                    val newPhone =
                        snapshot.child("phone")
                            .value.toString()

                    val location =
                        snapshot.child("location")
                            .value.toString()

                    val status =
                        snapshot.child("status")
                            .value.toString()

                    val rate =
                        snapshot.child("rate")
                            .value.toString()

                    val rating =
                        snapshot.child("rating")
                            .value.toString()

                    val image =
                        snapshot.child("image")
                            .value.toString()

                    // SET DATA

                    nameText.text =
                        "Name: $name"

                    workText.text =
                        "Work: $work"

                    phoneText.text =
                        "Phone: $newPhone"

                    locationText.text =
                        "Location: $location"

                    statusText.text =
                        "Status: $status"

                    rateText.text =
                        "Rate: ₹$rate"

                    ratingText.text =
                        "Rating: $rating ⭐"

                    // LOAD IMAGE

                    try {

                        if (
                            image.isNotEmpty() &&
                            image.startsWith("content://")
                        ) {

                            imageView.setImageURI(
                                Uri.parse(image)
                            )

                        } else {

                            imageView.setImageResource(
                                R.drawable.worker
                            )
                        }

                    } catch (e: Exception) {

                        imageView.setImageResource(
                            R.drawable.worker
                        )
                    }
                }
            }
    }
}