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
import com.google.firebase.database.FirebaseDatabase

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

        val btnBack = Button(this)

        btnBack.text = "← Back"

        btnBack.setTextColor(Color.WHITE)

        btnBack.setBackgroundColor(
            Color.parseColor("#7E57C2")
        )

        btnBack.setOnClickListener {
            finish()
        }

        mainLayout.addView(btnBack)

        val heading = TextView(this)

        heading.text =
            "Worker Booking Requests"

        heading.textSize = 28f

        heading.gravity =
            Gravity.CENTER

        heading.setTextColor(
            Color.parseColor("#5B2C91")
        )

        heading.setPadding(
            0,
            40,
            0,
            20
        )

        mainLayout.addView(heading)

        val workerPhone =
            intent.getStringExtra("phone") ?: ""

        val database =
            FirebaseDatabase.getInstance()

        val bookingsRef =
            database.getReference("Bookings")

        bookingsRef.get()
            .addOnSuccessListener { snapshot ->

                if (!snapshot.exists()) {

                    val emptyText =
                        TextView(this)

                    emptyText.text =
                        "No Booking Requests"

                    emptyText.textSize = 22f

                    emptyText.gravity =
                        Gravity.CENTER

                    mainLayout.addView(emptyText)

                    return@addOnSuccessListener
                }

                for (bookingSnapshot in snapshot.children) {

                    val bookingId =
                        bookingSnapshot.key.toString()

                    val customer =
                        bookingSnapshot.child("customerName")
                            .value.toString()

                    val customerPhone =
                        bookingSnapshot.child("customerPhone")
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

                    val workerPhoneDb =
                        bookingSnapshot.child("workerPhone")
                            .value.toString()

                    if (workerPhoneDb == workerPhone) {

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

                        val params =
                            LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                        params.setMargins(
                            0,
                            0,
                            0,
                            40
                        )

                        cardLayout.layoutParams =
                            params

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

                        cardLayout.addView(
                            bookingText
                        )

                        if (status == "PENDING") {

                            val acceptBtn =
                                Button(this)

                            acceptBtn.text =
                                "ACCEPT BOOKING"

                            acceptBtn.setBackgroundColor(
                                Color.parseColor("#4CAF50")
                            )

                            acceptBtn.setTextColor(
                                Color.WHITE
                            )

                            acceptBtn.setOnClickListener {

                                bookingsRef.child(bookingId)
                                    .child("status")
                                    .setValue("ACCEPTED")

                                Toast.makeText(
                                    this,
                                    "Booking Accepted",
                                    Toast.LENGTH_SHORT
                                ).show()

                                recreate()
                            }

                            cardLayout.addView(
                                acceptBtn
                            )

                            val declineBtn =
                                Button(this)

                            declineBtn.text =
                                "DECLINE BOOKING"

                            declineBtn.setBackgroundColor(
                                Color.RED
                            )

                            declineBtn.setTextColor(
                                Color.WHITE
                            )

                            declineBtn.setOnClickListener {

                                bookingsRef.child(bookingId)
                                    .child("status")
                                    .setValue("DECLINED")

                                Toast.makeText(
                                    this,
                                    "Booking Declined",
                                    Toast.LENGTH_SHORT
                                ).show()

                                recreate()
                            }

                            cardLayout.addView(
                                declineBtn
                            )
                        }

                        if (status == "ACCEPTED") {

                            val completeBtn =
                                Button(this)

                            completeBtn.text =
                                "MARK AS COMPLETED"

                            completeBtn.setBackgroundColor(
                                Color.parseColor("#FF9800")
                            )

                            completeBtn.setTextColor(
                                Color.WHITE
                            )

                            completeBtn.setOnClickListener {

                                bookingsRef.child(bookingId)
                                    .child("status")
                                    .setValue("COMPLETED")

                                Toast.makeText(
                                    this,
                                    "Work Completed",
                                    Toast.LENGTH_SHORT
                                ).show()

                                recreate()
                            }

                            cardLayout.addView(
                                completeBtn
                            )
                        }

                        val callBtn =
                            Button(this)

                        callBtn.text =
                            "CALL CUSTOMER"

                        callBtn.setBackgroundColor(
                            Color.parseColor("#7E57C2")
                        )

                        callBtn.setTextColor(
                            Color.WHITE
                        )

                        callBtn.setOnClickListener {

                            val intent =
                                Intent(
                                    Intent.ACTION_DIAL,
                                    Uri.parse("tel:$customerPhone")
                                )

                            startActivity(intent)
                        }

                        cardLayout.addView(callBtn)

                        val locationBtn =
                            Button(this)

                        locationBtn.text =
                            "OPEN CUSTOMER LOCATION"

                        locationBtn.setBackgroundColor(
                            Color.parseColor("#5B2C91")
                        )

                        locationBtn.setTextColor(
                            Color.WHITE
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
}