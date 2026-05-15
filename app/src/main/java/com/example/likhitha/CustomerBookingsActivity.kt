package com.example.likhitha

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CustomerBookingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val scrollView =
            ScrollView(this)

        scrollView.setBackgroundColor(
            Color.parseColor("#F5F0FF")
        )

        val mainLayout =
            LinearLayout(this)

        mainLayout.orientation =
            LinearLayout.VERTICAL

        mainLayout.setPadding(
            30,
            30,
            30,
            30
        )

        // HEADING

        val heading =
            TextView(this)

        heading.text =
            "Customer Booking Details"

        heading.textSize = 28f

        heading.gravity =
            Gravity.CENTER

        heading.setTextColor(
            Color.parseColor("#5B2C91")
        )

        heading.setPadding(
            0,
            20,
            0,
            40
        )

        mainLayout.addView(heading)

        scrollView.addView(mainLayout)

        setContentView(scrollView)

        val customerPhone =
            intent.getStringExtra(
                "customerPhone"
            ) ?: ""

        // FIREBASE

        val bookingsRef =
            FirebaseDatabase.getInstance()
                .getReference("Bookings")

        bookingsRef.get()
            .addOnSuccessListener { snapshot ->

                if (!snapshot.exists()) {

                    val emptyText =
                        TextView(this)

                    emptyText.text =
                        "No Bookings Found"

                    emptyText.textSize = 22f

                    emptyText.gravity =
                        Gravity.CENTER

                    mainLayout.addView(emptyText)

                } else {

                    for (bookingSnapshot in snapshot.children) {

                        val worker =
                            bookingSnapshot.child("workerName")
                                .value.toString()

                        val work =
                            bookingSnapshot.child("workerWork")
                                .value.toString()

                        val location =
                            bookingSnapshot.child("customerLocation")
                                .value.toString()

                        val status =
                            bookingSnapshot.child("status")
                                .value.toString()

                        val workerPhone =
                            bookingSnapshot.child("workerPhone")
                                .value.toString()

                        val dbCustomerPhone =
                            bookingSnapshot.child("customerPhone")
                                .value.toString()

                        if (
                            dbCustomerPhone == customerPhone
                        ) {

                            // CARD LAYOUT

                            val cardLayout =
                                LinearLayout(this)

                            cardLayout.orientation =
                                LinearLayout.VERTICAL

                            cardLayout.setPadding(
                                35,
                                35,
                                35,
                                35
                            )

                            cardLayout.setBackgroundColor(
                                Color.WHITE
                            )

                            cardLayout.elevation = 10f

                            val cardParams =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )

                            cardParams.setMargins(
                                0,
                                0,
                                0,
                                35
                            )

                            cardLayout.layoutParams =
                                cardParams

                            val bookingText =
                                TextView(this)

                            bookingText.text =

                                "👷 Worker : $worker\n\n" +

                                        "🛠 Work : $work\n\n" +

                                        "📍 Address : $location\n\n" +

                                        "📌 Status : $status"

                            bookingText.textSize = 19f

                            bookingText.setTextColor(
                                Color.BLACK
                            )

                            bookingText.setLineSpacing(
                                10f,
                                1f
                            )

                            cardLayout.addView(
                                bookingText
                            )

                            // ⭐ RATE WORKER BUTTON

                            if (
                                status == "COMPLETED"
                            ) {

                                val rateButton =
                                    Button(this)

                                rateButton.text =
                                    "⭐ RATE WORKER"

                                rateButton.setBackgroundColor(
                                    Color.parseColor("#7E57C2")
                                )

                                rateButton.setTextColor(
                                    Color.WHITE
                                )

                                rateButton.textSize =
                                    16f

                                val rateParams =
                                    LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                    )

                                rateParams.setMargins(
                                    0,
                                    25,
                                    0,
                                    0
                                )

                                rateButton.layoutParams =
                                    rateParams

                                rateButton.setOnClickListener {

                                    val dialogLayout =
                                        LinearLayout(this)

                                    dialogLayout.orientation =
                                        LinearLayout.VERTICAL

                                    dialogLayout.setPadding(
                                        40,
                                        40,
                                        40,
                                        40
                                    )

                                    val ratingBar =
                                        RatingBar(this)

                                    ratingBar.numStars = 5

                                    ratingBar.stepSize = 1f

                                    dialogLayout.addView(
                                        ratingBar
                                    )

                                    AlertDialog.Builder(this)

                                        .setTitle("Rate $worker")

                                        .setView(dialogLayout)

                                        .setPositiveButton(
                                            "Submit"
                                        ) { _, _ ->

                                            val selectedRating =
                                                ratingBar.rating

                                            val workersRef =
                                                FirebaseDatabase
                                                    .getInstance()
                                                    .getReference("Workers")

                                            workersRef.child(workerPhone)
                                                .get()

                                                .addOnSuccessListener { workerSnapshot ->

                                                    val oldRating =
                                                        workerSnapshot.child("rating")
                                                            .value.toString()
                                                            .toFloatOrNull() ?: 0f

                                                    val ratingCount =
                                                        workerSnapshot.child("ratingCount")
                                                            .value.toString()
                                                            .toIntOrNull() ?: 0

                                                    val totalRating =
                                                        workerSnapshot.child("totalRating")
                                                            .value.toString()
                                                            .toFloatOrNull() ?: 0f

                                                    val newTotal =
                                                        totalRating + selectedRating

                                                    val newCount =
                                                        ratingCount + 1

                                                    val average =
                                                        newTotal / newCount

                                                    val updateMap =
                                                        HashMap<String, Any>()

                                                    updateMap["rating"] =
                                                        String.format("%.1f", average)

                                                    updateMap["ratingCount"] =
                                                        newCount.toString()

                                                    updateMap["totalRating"] =
                                                        newTotal.toString()

                                                    workersRef.child(workerPhone)
                                                        .updateChildren(updateMap)

                                                    Toast.makeText(
                                                        this,
                                                        "Rating Submitted Successfully",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                        }

                                        .setNegativeButton(
                                            "Cancel",
                                            null
                                        )

                                        .show()
                                }

                                cardLayout.addView(rateButton)
                            }

                            mainLayout.addView(
                                cardLayout
                            )
                        }
                    }
                }

                // BACK BUTTON

                val btnBack =
                    Button(this)

                btnBack.text = "← BACK"

                btnBack.setBackgroundColor(
                    Color.parseColor("#7E57C2")
                )

                btnBack.setTextColor(Color.WHITE)

                btnBack.textSize = 16f

                val backParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                backParams.setMargins(
                    0,
                    20,
                    0,
                    20
                )

                btnBack.layoutParams =
                    backParams

                btnBack.setOnClickListener {

                    finish()
                }

                mainLayout.addView(btnBack)
            }
    }
}