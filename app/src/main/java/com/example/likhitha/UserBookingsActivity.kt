package com.example.likhitha

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserBookingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_user_bookings
        )

        val bookingsContainer =
            findViewById<LinearLayout>(
                R.id.userBookingsContainer
            )

        val sharedPreferences =
            getSharedPreferences(
                "WorkersData",
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

            emptyText.textSize = 20f

            bookingsContainer.addView(
                emptyText
            )

            return
        }

        val bookingList =
            bookings.split("###")

        for (booking in bookingList) {

            if (booking.isNotEmpty()) {

                val details =
                    booking.split("|")

                var workerName = ""
                var work = ""
                var customerName = ""
                var customerPhone = ""
                var customerLocation = ""
                var date = ""
                var status = "PENDING"

                for (item in details) {

                    when {

                        item.startsWith("Worker:") ->

                            workerName =
                                item.replace(
                                    "Worker:",
                                    ""
                                )

                        item.startsWith("Work:") ->

                            work =
                                item.replace(
                                    "Work:",
                                    ""
                                )

                        item.startsWith("Customer:") ->

                            customerName =
                                item.replace(
                                    "Customer:",
                                    ""
                                )

                        item.startsWith("CustomerPhone:") ->

                            customerPhone =
                                item.replace(
                                    "CustomerPhone:",
                                    ""
                                )

                        item.startsWith("CustomerLocation:") ->

                            customerLocation =
                                item.replace(
                                    "CustomerLocation:",
                                    ""
                                )

                        item.startsWith("Date:") ->

                            date =
                                item.replace(
                                    "Date:",
                                    ""
                                )

                        item.startsWith("Status:") ->

                            status =
                                item.replace(
                                    "Status:",
                                    ""
                                )
                    }
                }

                val bookingText =
                    TextView(this)

                bookingText.text =

                    "Worker Name: $workerName\n\n" +

                            "Work: $work\n\n" +

                            "Customer Name: $customerName\n" +

                            "Customer Phone: $customerPhone\n" +

                            "Customer Location: $customerLocation\n\n" +

                            "Work Date: $date\n\n" +

                            "Booking Status: $status"

                bookingText.textSize = 18f

                bookingText.setPadding(
                    20,
                    20,
                    20,
                    20
                )

                bookingsContainer.addView(
                    bookingText
                )

                // DIVIDER
                val divider =
                    TextView(this)

                divider.text =
                    "\n-----------------------------\n"

                bookingsContainer.addView(
                    divider
                )
            }
        }
    }
}