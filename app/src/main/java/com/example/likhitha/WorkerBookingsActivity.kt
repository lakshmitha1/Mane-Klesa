package com.example.likhitha

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WorkerBookingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val scrollView = ScrollView(this)

        scrollView.setBackgroundColor(
            Color.parseColor("#F5F0FF")
        )

        val mainLayout = LinearLayout(this)

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

        val btnBack = Button(this)

        btnBack.text = "← Back"

        btnBack.textSize = 16f

        btnBack.setTextColor(Color.WHITE)

        btnBack.setBackgroundColor(
            Color.parseColor("#7E57C2")
        )

        btnBack.setOnClickListener {
            finish()
        }

        mainLayout.addView(btnBack)

        // HEADING

        val heading = TextView(this)

        heading.text = "Worker Booking Requests"

        heading.textSize = 28f

        heading.gravity = Gravity.CENTER

        heading.setTextColor(
            Color.parseColor("#5B2C91")
        )

        heading.setPadding(
            0,
            40,
            0,
            15
        )

        mainLayout.addView(heading)

        // SUBTITLE

        val subTitle = TextView(this)

        subTitle.text =
            "Manage customer bookings easily"

        subTitle.textSize = 16f

        subTitle.gravity = Gravity.CENTER

        subTitle.setTextColor(
            Color.parseColor("#7E57C2")
        )

        subTitle.setPadding(
            0,
            0,
            0,
            40
        )

        mainLayout.addView(subTitle)

        // GET WORKER PHONE

        val workerPhone =
            intent.getStringExtra(
                "phone"
            ) ?: ""

        // BOOKINGS DATA

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
                "No Booking Requests"

            emptyText.textSize = 22f

            emptyText.gravity =
                Gravity.CENTER

            mainLayout.addView(emptyText)

            return
        }

        val bookingList =
            bookings.split("###").toMutableList()

        for (i in bookingList.indices) {

            val booking = bookingList[i]

            if (
                booking.contains(
                    "WorkerPhone:$workerPhone"
                )
            ) {

                val details =
                    booking.split("|")

                var customer = ""
                var customerPhone = ""
                var work = ""
                var location = ""
                var status = ""

                for (item in details) {

                    when {

                        item.startsWith("Customer:") -> {

                            customer =
                                item.replace(
                                    "Customer:",
                                    ""
                                )
                        }

                        item.startsWith("CustomerPhone:") -> {

                            customerPhone =
                                item.replace(
                                    "CustomerPhone:",
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
                    40
                )

                cardLayout.layoutParams =
                    cardParams

                // TITLE

                val title =
                    TextView(this)

                title.text =
                    "Customer Booking Details"

                title.textSize = 22f

                title.setTextColor(
                    Color.parseColor("#5B2C91")
                )

                title.setPadding(
                    0,
                    0,
                    0,
                    25
                )

                cardLayout.addView(title)

                // DETAILS

                val bookingText =
                    TextView(this)

                bookingText.text =

                    "👤 Customer : $customer\n\n" +

                            "📞 Phone : $customerPhone\n\n" +

                            "🛠 Work : $work\n\n" +

                            "📍 Address : $location\n\n" +

                            "📌 Status : $status"

                bookingText.textSize = 18f

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

                // ACCEPT BUTTON

                if (status == "PENDING") {

                    val acceptBtn =
                        Button(this)

                    acceptBtn.text =
                        "ACCEPT BOOKING"

                    acceptBtn.setTextColor(
                        Color.WHITE
                    )

                    acceptBtn.setBackgroundColor(
                        Color.parseColor("#4CAF50")
                    )

                    val acceptParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )

                    acceptParams.setMargins(
                        0,
                        35,
                        0,
                        20
                    )

                    acceptBtn.layoutParams =
                        acceptParams

                    acceptBtn.setOnClickListener {

                        val updatedBooking =
                            booking.replace(
                                "Status:PENDING",
                                "Status:ACCEPTED"
                            )

                        bookingList[i] =
                            updatedBooking

                        val updatedData =
                            bookingList.joinToString("###")

                        sharedPreferences.edit()
                            .putString(
                                "bookings",
                                updatedData
                            )
                            .apply()

                        Toast.makeText(
                            this,
                            "Booking Accepted",
                            Toast.LENGTH_SHORT
                        ).show()

                        recreate()
                    }

                    cardLayout.addView(acceptBtn)

                    // DECLINE BUTTON

                    val declineBtn =
                        Button(this)

                    declineBtn.text =
                        "DECLINE BOOKING"

                    declineBtn.setTextColor(
                        Color.WHITE
                    )

                    declineBtn.setBackgroundColor(
                        Color.parseColor("#E53935")
                    )

                    declineBtn.setOnClickListener {

                        val updatedBooking =
                            booking.replace(
                                "Status:PENDING",
                                "Status:DECLINED"
                            )

                        bookingList[i] =
                            updatedBooking

                        val updatedData =
                            bookingList.joinToString("###")

                        sharedPreferences.edit()
                            .putString(
                                "bookings",
                                updatedData
                            )
                            .apply()

                        Toast.makeText(
                            this,
                            "Booking Declined",
                            Toast.LENGTH_SHORT
                        ).show()

                        recreate()
                    }

                    cardLayout.addView(declineBtn)
                }

                // COMPLETE BUTTON

                if (status == "ACCEPTED") {

                    val completeBtn =
                        Button(this)

                    completeBtn.text =
                        "MARK AS COMPLETED"

                    completeBtn.setTextColor(
                        Color.WHITE
                    )

                    completeBtn.setBackgroundColor(
                        Color.parseColor("#FF9800")
                    )

                    val completeParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )

                    completeParams.setMargins(
                        0,
                        35,
                        0,
                        20
                    )

                    completeBtn.layoutParams =
                        completeParams

                    completeBtn.setOnClickListener {

                        val updatedBooking =
                            booking.replace(
                                "Status:ACCEPTED",
                                "Status:COMPLETED"
                            )

                        bookingList[i] =
                            updatedBooking

                        val updatedData =
                            bookingList.joinToString("###")

                        sharedPreferences.edit()
                            .putString(
                                "bookings",
                                updatedData
                            )
                            .apply()

                        Toast.makeText(
                            this,
                            "Work Completed",
                            Toast.LENGTH_SHORT
                        ).show()

                        recreate()
                    }

                    cardLayout.addView(completeBtn)
                }

                // CALL BUTTON

                val callBtn =
                    Button(this)

                callBtn.text =
                    "CALL CUSTOMER"

                callBtn.setTextColor(
                    Color.WHITE
                )

                callBtn.setBackgroundColor(
                    Color.parseColor("#7E57C2")
                )

                val callParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                callParams.setMargins(
                    0,
                    25,
                    0,
                    20
                )

                callBtn.layoutParams =
                    callParams

                callBtn.setOnClickListener {

                    val intent =
                        Intent(
                            Intent.ACTION_DIAL
                        )

                    intent.data =
                        Uri.parse(
                            "tel:$customerPhone"
                        )

                    startActivity(intent)
                }

                cardLayout.addView(callBtn)

                // LOCATION BUTTON

                val locationBtn =
                    Button(this)

                locationBtn.text =
                    "OPEN CUSTOMER LOCATION"

                locationBtn.setTextColor(
                    Color.WHITE
                )

                locationBtn.setBackgroundColor(
                    Color.parseColor("#5B2C91")
                )

                locationBtn.setOnClickListener {

                    val intent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "geo:0,0?q=$location"
                            )
                        )

                    startActivity(intent)
                }

                cardLayout.addView(locationBtn)

                mainLayout.addView(cardLayout)
            }
        }
    }
}