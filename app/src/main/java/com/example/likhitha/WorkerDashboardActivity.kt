package com.example.likhitha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class WorkerDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_worker_dashboard
        )

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }

        val textWelcome =
            findViewById<TextView>(R.id.textWelcome)

        val btnMyProfile =
            findViewById<Button>(R.id.btnMyProfile)

        val btnMyBookings =
            findViewById<Button>(R.id.btnMyBookings)

        val btnAvailability =
            findViewById<Button>(R.id.btnAvailability)

        val btnLogout =
            findViewById<Button>(R.id.btnLogout)

        val btnEditProfile =
            findViewById<Button>(R.id.btnEditProfile)

        // GET PHONE FROM LOGIN

        var workerPhone =
            intent.getStringExtra("phone") ?: ""

        // FIREBASE

        val workersRef =
            FirebaseDatabase.getInstance()
                .getReference("Workers")

        // LOAD WORKER NAME

        workersRef.child(workerPhone)
            .get()
            .addOnSuccessListener { snapshot ->

                if (snapshot.exists()) {

                    val workerName =
                        snapshot.child("name")
                            .value.toString()

                    textWelcome.text =
                        "Welcome $workerName"
                }
            }

        // MY PROFILE

        btnMyProfile.setOnClickListener {

            workersRef.child(workerPhone)
                .get()
                .addOnSuccessListener { snapshot ->

                    if (snapshot.exists()) {

                        val name =
                            snapshot.child("name")
                                .value.toString()

                        val work =
                            snapshot.child("work")
                                .value.toString()

                        val phone =
                            snapshot.child("phone")
                                .value.toString()

                        val location =
                            snapshot.child("location")
                                .value.toString()

                        val status =
                            snapshot.child("status")
                                .value.toString()

                        val image =
                            snapshot.child("image")
                                .value.toString()

                        val rating =
                            snapshot.child("rating")
                                .value.toString()

                        val rate =
                            snapshot.child("rate")
                                .value.toString()

                        val intent =
                            Intent(
                                this,
                                WorkerProfileActivity::class.java
                            )

                        intent.putExtra("name", name)
                        intent.putExtra("work", work)
                        intent.putExtra("phone", phone)
                        intent.putExtra("location", location)
                        intent.putExtra("status", status)
                        intent.putExtra("image", image)
                        intent.putExtra("rating", rating)
                        intent.putExtra("rate", rate)

                        intent.putExtra(
                            "workerPhone",
                            phone
                        )

                        startActivity(intent)
                    }
                }
        }

        // MY BOOKINGS

        btnMyBookings.setOnClickListener {

            val intent =
                Intent(
                    this,
                    WorkerBookingsActivity::class.java
                )

            intent.putExtra(
                "phone",
                workerPhone
            )

            startActivity(intent)
        }

        // CHANGE AVAILABILITY

        btnAvailability.setOnClickListener {

            workersRef.child(workerPhone)
                .get()
                .addOnSuccessListener { snapshot ->

                    if (snapshot.exists()) {

                        val currentStatus =
                            snapshot.child("status")
                                .value.toString()

                        val newStatus =

                            if (
                                currentStatus.equals(
                                    "Online",
                                    true
                                )
                            ) {
                                "Offline"
                            } else {
                                "Online"
                            }

                        workersRef.child(workerPhone)
                            .child("status")
                            .setValue(newStatus)

                        Toast.makeText(
                            this,
                            "Availability Changed to $newStatus",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // EDIT PROFILE

        btnEditProfile.setOnClickListener {

            val intent =
                Intent(
                    this,
                    EditWorkerProfileActivity::class.java
                )

            intent.putExtra(
                "phone",
                workerPhone
            )

            startActivity(intent)
        }

        // LOGOUT

        btnLogout.setOnClickListener {

            finish()

            Toast.makeText(
                this,
                "Logged Out",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}