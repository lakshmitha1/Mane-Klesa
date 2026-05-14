package com.example.likhitha

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
        val workerPhone =
            intent.getStringExtra("phone") ?: ""

        val sharedPreferences =
            getSharedPreferences(
                "WorkersData",
                MODE_PRIVATE
            )

        // GET WORKER NAME
        val workersData =
            sharedPreferences.getString(
                "workers",
                ""
            ) ?: ""

        val workersList =
            workersData.split("###")

        var workerName = ""

        for (worker in workersList) {

            if (
                worker.contains("Phone:$workerPhone")
            ) {

                val details =
                    worker.split("|")

                for (item in details) {

                    if (
                        item.startsWith("Name:")
                    ) {

                        workerName =
                            item.replace(
                                "Name:",
                                ""
                            )
                    }
                }
            }
        }

        textWelcome.text =
            "Welcome $workerName"

        // MY PROFILE
        btnMyProfile.setOnClickListener {

            val workersDataProfile =
                sharedPreferences.getString(
                    "workers",
                    ""
                ) ?: ""

            val workersListProfile =
                workersDataProfile.split("###")

            for (worker in workersListProfile) {

                if (
                    worker.contains("Phone:$workerPhone")
                ) {

                    val details =
                        worker.split("|")

                    var name = ""
                    var work = ""
                    var phone = ""
                    var location = ""
                    var status = ""
                    var image = ""
                    var rating = "0"
                    var rate = ""

                    for (item in details) {

                        when {

                            item.startsWith("Name:") ->
                                name =
                                    item.replace(
                                        "Name:",
                                        ""
                                    )

                            item.startsWith("Work:") ->
                                work =
                                    item.replace(
                                        "Work:",
                                        ""
                                    )

                            item.startsWith("Phone:") ->
                                phone =
                                    item.replace(
                                        "Phone:",
                                        ""
                                    )

                            item.startsWith("Location:") ->
                                location =
                                    item.replace(
                                        "Location:",
                                        ""
                                    )

                            item.startsWith("Status:") ->
                                status =
                                    item.replace(
                                        "Status:",
                                        ""
                                    )

                            item.startsWith("Image:") ->
                                image =
                                    item.replace(
                                        "Image:",
                                        ""
                                    )

                            item.startsWith("Rating:") ->
                                rating =
                                    item.replace(
                                        "Rating:",
                                        ""
                                    )

                            item.startsWith("Rate:") ->
                                rate =
                                    item.replace(
                                        "Rate:",
                                        ""
                                    )
                        }
                    }

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

                    // IMPORTANT
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

            val latestWorkersData =
                sharedPreferences.getString(
                    "workers",
                    ""
                ) ?: ""

            val latestWorkersList =
                latestWorkersData.split("###")

            var updatedData = ""

            var newStatus = ""

            for (worker in latestWorkersList) {

                if (
                    worker.contains("Phone:$workerPhone")
                ) {

                    val updatedWorker =

                        if (
                            worker.contains("Status:Online")
                        ) {

                            newStatus = "Offline"

                            worker.replace(
                                "Status:Online",
                                "Status:Offline"
                            )

                        } else {

                            newStatus = "Online"

                            if (
                                worker.contains("Status:Offline")
                            ) {

                                worker.replace(
                                    "Status:Offline",
                                    "Status:Online"
                                )

                            } else {

                                worker +
                                        "|Status:Online"
                            }
                        }

                    updatedData +=
                        updatedWorker + "###"

                } else {

                    if (worker.isNotEmpty()) {

                        updatedData +=
                            worker + "###"
                    }
                }
            }

            sharedPreferences.edit()
                .putString(
                    "workers",
                    updatedData
                )
                .apply()

            Toast.makeText(
                this,
                "Availability Changed to $newStatus",
                Toast.LENGTH_SHORT
            ).show()
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