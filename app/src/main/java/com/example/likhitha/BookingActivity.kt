package com.example.likhitha

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_booking)

        val workerName =
            intent.getStringExtra("workerName") ?: ""

        val workerWork =
            intent.getStringExtra("workerWork") ?: ""

        val workerPhone =
            intent.getStringExtra("workerPhone") ?: ""

        val editCustomerName =
            findViewById<EditText>(
                R.id.editCustomerName
            )

        val editCustomerPhone =
            findViewById<EditText>(
                R.id.editCustomerPhone
            )

        val editCustomerAddress =
            findViewById<EditText>(
                R.id.editCustomerAddress
            )

        val btnConfirmBooking =
            findViewById<Button>(
                R.id.btnConfirmBooking
            )

        btnConfirmBooking.setOnClickListener {

            val customerName =
                editCustomerName.text.toString()

            val customerPhone =
                editCustomerPhone.text.toString()

            val customerAddress =
                editCustomerAddress.text.toString()

            if (
                customerName.isEmpty() ||
                customerPhone.isEmpty() ||
                customerAddress.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Fill all details",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                if (customerPhone == workerPhone) {

                    Toast.makeText(
                        this,
                        "You cannot hire yourself",
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    val bookingDetails =

                        "Worker:$workerName|" +
                                "Work:$workerWork|" +
                                "WorkerPhone:$workerPhone|" +
                                "Customer:$customerName|" +
                                "CustomerPhone:$customerPhone|" +
                                "Location:$customerAddress|" +
                                "Date:Today|" +
                                "Status:Pending###"

                    // IMPORTANT FIX
                    val sharedPreferences =
                        getSharedPreferences(
                            "Bookings",
                            MODE_PRIVATE
                        )

                    val oldBookings =
                        sharedPreferences.getString(
                            "booking_list",
                            ""
                        ) ?: ""

                    val updatedBookings =
                        oldBookings + bookingDetails

                    sharedPreferences.edit()
                        .putString(
                            "booking_list",
                            updatedBookings
                        )
                        .apply()

                    Toast.makeText(
                        this,
                        "Booking Sent Successfully",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                }
            }
        }
    }
}