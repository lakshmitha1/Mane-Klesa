package com.example.likhitha

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

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
                editCustomerName.text.toString().trim()

            val customerPhone =
                editCustomerPhone.text.toString().trim()

            val customerAddress =
                editCustomerAddress.text.toString().trim()

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

                    // FIREBASE

                    val database =
                        FirebaseDatabase.getInstance()

                    val bookingsRef =
                        database.getReference("Bookings")

                    val customersRef =
                        database.getReference("Customers")

                    // SAVE CUSTOMER

                    val customerData =
                        hashMapOf(
                            "name" to customerName,
                            "phone" to customerPhone,
                            "address" to customerAddress
                        )

                    customersRef.child(customerPhone)
                        .setValue(customerData)

                    // CREATE BOOKING

                    val bookingId =
                        bookingsRef.push().key ?: ""

                    val bookingData =
                        hashMapOf(
                            "workerName" to workerName,
                            "workerWork" to workerWork,
                            "workerPhone" to workerPhone,
                            "customerName" to customerName,
                            "customerPhone" to customerPhone,
                            "customerLocation" to customerAddress,
                            "date" to "Today",
                            "status" to "PENDING"
                        )

                    bookingsRef.child(bookingId)
                        .setValue(bookingData)

                        .addOnSuccessListener {

                            Toast.makeText(
                                this,
                                "Booking Sent Successfully",
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        }

                        .addOnFailureListener {

                            Toast.makeText(
                                this,
                                "Booking Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
    }
}