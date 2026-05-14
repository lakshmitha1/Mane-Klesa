package com.example.likhitha

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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

        val sharedPreferences =
            getSharedPreferences(
                "BookingsData",
                MODE_PRIVATE
            )

        val bookings =
            sharedPreferences.getString(
                "bookings",
                ""
            ) ?: ""

        if (bookings.isEmpty()) {

            val emptyText =
                TextView(this)

            emptyText.text =
                "No Bookings Found"

            emptyText.textSize = 22f

            emptyText.gravity =
                Gravity.CENTER

            mainLayout.addView(emptyText)

        } else {

            val bookingList =
                bookings.split("###")

            for (booking in bookingList) {

                if (
                    booking.contains(
                        "CustomerPhone:$customerPhone"
                    )
                ) {

                    val details =
                        booking.split("|")

                    var worker = ""
                    var work = ""
                    var location = ""
                    var status = ""

                    for (item in details) {

                        when {

                            item.startsWith("Worker:") -> {

                                worker =
                                    item.replace(
                                        "Worker:",
                                        ""
                                    )
                            }

                            item.startsWith("Work:") -> {

                                work =
                                    item.replace(
                                        "Work:",
                                        ""
                                    )
                            }

                            item.startsWith("CustomerLocation:") -> {

                                location =
                                    item.replace(
                                        "CustomerLocation:",
                                        ""
                                    )
                            }

                            item.startsWith("Status:") -> {

                                status =
                                    item.replace(
                                        "Status:",
                                        ""
                                    )
                            }
                        }
                    }

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