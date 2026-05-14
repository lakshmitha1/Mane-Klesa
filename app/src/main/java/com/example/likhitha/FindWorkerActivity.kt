package com.example.likhitha

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FindWorkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_worker)
        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }

        val editSearch =
            findViewById<EditText>(R.id.editSearch)

        val btnSearch =
            findViewById<Button>(R.id.btnSearch)

        val workersContainer =
            findViewById<LinearLayout>(R.id.workersContainer)

        fun loadWorkers(searchText: String = "") {

            workersContainer.removeAllViews()

            val sharedPreferences =
                getSharedPreferences(
                    "WorkersData",
                    MODE_PRIVATE
                )

            val workersData =
                sharedPreferences.getString(
                    "workers",
                    ""
                ) ?: ""

            if (workersData.isEmpty()) {

                val noWorker = TextView(this)

                noWorker.text = "No workers found"
                noWorker.textSize = 20f
                noWorker.gravity = Gravity.CENTER
                noWorker.setTextColor(Color.BLACK)

                workersContainer.addView(noWorker)

                return
            }

            val workersList =
                workersData.split("###")

            for (worker in workersList) {

                if (
                    worker.isNotEmpty() &&
                    worker.lowercase()
                        .contains(searchText.lowercase())
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

                            item.startsWith("Name:") -> {
                                name =
                                    item.replace(
                                        "Name:",
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

                            item.startsWith("Phone:") -> {
                                phone =
                                    item.replace(
                                        "Phone:",
                                        ""
                                    )
                            }

                            item.startsWith("Location:") -> {
                                location =
                                    item.replace(
                                        "Location:",
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

                            item.startsWith("Image:") -> {
                                image =
                                    item.replace(
                                        "Image:",
                                        ""
                                    )
                            }

                            item.startsWith("Rating:") -> {
                                rating =
                                    item.replace(
                                        "Rating:",
                                        ""
                                    )
                            }

                            item.startsWith("Rate:") -> {
                                rate =
                                    item.replace(
                                        "Rate:",
                                        ""
                                    )
                            }
                        }
                    }

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

                    val cardParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )

                    cardParams.setMargins(
                        20,
                        20,
                        20,
                        20
                    )

                    cardLayout.layoutParams =
                        cardParams

                    cardLayout.setBackgroundResource(
                        R.drawable.rounded_card
                    )

                    cardLayout.elevation = 10f

                    val imageView =
                        ImageView(this)

                    val imageParams =
                        LinearLayout.LayoutParams(
                            190,
                            190
                        )

                    imageParams.gravity =
                        Gravity.CENTER_HORIZONTAL

                    imageParams.setMargins(
                        0,
                        0,
                        0,
                        20
                    )

                    imageView.layoutParams =
                        imageParams

                    imageView.scaleType =
                        ImageView.ScaleType.CENTER_CROP

                    imageView.background =
                        getDrawable(R.drawable.round_image)

                    imageView.clipToOutline = true

                    try {

                        if (
                            image.isNotEmpty() &&
                            image != "null"
                        ) {

                            imageView.setImageURI(
                                Uri.parse(image)
                            )

                        } else {

                            imageView.setImageResource(
                                R.drawable.ic_launcher_foreground
                            )
                        }

                    } catch (e: Exception) {

                        imageView.setImageResource(
                            R.drawable.ic_launcher_foreground
                        )
                    }
                    val nameText =
                        TextView(this)

                    nameText.text =
                        "👩‍🔧 $name"

                    nameText.textSize = 24f

                    nameText.gravity =
                        Gravity.CENTER

                    nameText.setTextColor(
                        Color.parseColor("#4A148C")
                    )

                    nameText.setPadding(
                        0,
                        10,
                        0,
                        20
                    )

                    val detailsLayout =
                        LinearLayout(this)

                    detailsLayout.orientation =
                        LinearLayout.VERTICAL

                    detailsLayout.setPadding(
                        30,
                        25,
                        30,
                        25
                    )

                    detailsLayout.setBackgroundColor(
                        Color.parseColor("#EDE7F6")
                    )

                    val workText =
                        TextView(this)

                    workText.text =
                        "🛠 Skill : $work"

                    workText.textSize = 17f

                    val locationText =
                        TextView(this)

                    locationText.text =
                        "📍 Location : " + location

                    locationText.isClickable = false

                    locationText.textSize = 17f

                    val phoneText =
                        TextView(this)

                    phoneText.text =
                        "📞 Phone : $phone"

                    phoneText.textSize = 17f

                    val rateText =
                        TextView(this)

                    rateText.text =
                        "💰 Rate : ₹$rate"

                    rateText.textSize = 17f

                    val ratingText =
                        TextView(this)

                    ratingText.text =
                        "⭐ Rating : $rating"

                    ratingText.textSize = 17f

                    val statusText =
                        TextView(this)

                    statusText.text =
                        "🟢 Status : $status"

                    statusText.textSize = 17f

                    detailsLayout.addView(workText)
                    detailsLayout.addView(locationText)
                    detailsLayout.addView(phoneText)
                    detailsLayout.addView(rateText)
                    detailsLayout.addView(ratingText)
                    detailsLayout.addView(statusText)

                    fun styleButton(button: Button) {

                        button.setBackgroundColor(
                            Color.parseColor("#7E57C2")
                        )

                        button.setTextColor(
                            Color.WHITE
                        )

                        button.textSize = 16f

                        val params =
                            LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                        params.setMargins(
                            0,
                            12,
                            0,
                            0
                        )

                        button.layoutParams =
                            params
                    }

                    val callButton =
                        Button(this)

                    callButton.text =
                        "📞 Call Worker"

                    styleButton(callButton)

                    callButton.setOnClickListener {

                        val intent =
                            Intent(
                                Intent.ACTION_DIAL,
                                Uri.parse("tel:$phone")
                            )

                        startActivity(intent)
                    }

                    val locationButton =
                        Button(this)

                    locationButton.text =
                        "📍 Open Location"

                    styleButton(locationButton)

                    locationButton.setOnClickListener {

                        try {

                            val mapIntent =
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(
                                        "https://www.google.com/maps/search/?api=1&query=$location"
                                    )
                                )

                            startActivity(mapIntent)

                        } catch (e: Exception) {

                            Toast.makeText(
                                this,
                                "Location not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    val profileButton =
                        Button(this)

                    profileButton.text =
                        "👤 View Profile"

                    styleButton(profileButton)

                    profileButton.setOnClickListener {

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

                        startActivity(intent)
                    }

                    val hireButton =
                        Button(this)

                    hireButton.text =
                        "💼 Hire Worker"

                    styleButton(hireButton)

                    if (
                        status.lowercase() != "online"
                    ) {

                        hireButton.text =
                            "❌ Worker Offline"

                    } else {

                        hireButton.setOnClickListener {

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

                            val customerName =
                                EditText(this)

                            customerName.hint =
                                "Enter Customer Name"

                            val customerPhone =
                                EditText(this)

                            customerPhone.hint =
                                "Enter Customer Phone"

                            val customerLocation =
                                EditText(this)

                            customerLocation.hint =
                                "Enter Address"

                            val customerDate =
                                EditText(this)

                            customerDate.hint =
                                "Enter Work Date"

                            dialogLayout.addView(customerName)
                            dialogLayout.addView(customerPhone)
                            dialogLayout.addView(customerLocation)
                            dialogLayout.addView(customerDate)

                            AlertDialog.Builder(this)
                                .setTitle("Hire $name")
                                .setView(dialogLayout)

                                .setPositiveButton(
                                    "Confirm"
                                ) { _, _ ->

                                    val cname =
                                        customerName.text.toString()

                                    val cphone =
                                        customerPhone.text.toString()

                                    val clocation =
                                        customerLocation.text.toString()

                                    val cdate =
                                        customerDate.text.toString()

                                    if (
                                        cphone == phone
                                    ) {

                                        Toast.makeText(
                                            this,
                                            "You cannot hire yourself",
                                            Toast.LENGTH_LONG
                                        ).show()

                                    } else {

                                        val bookingPrefs =
                                            getSharedPreferences(
                                                "BookingsData",
                                                MODE_PRIVATE
                                            )

                                        val oldBookings =
                                            bookingPrefs.getString(
                                                "bookings",
                                                ""
                                            ) ?: ""

                                        val bookingList =
                                            oldBookings.split("###")

                                        var alreadyBooked = false

                                        for (singleBooking in bookingList) {

                                            if (

                                                singleBooking.contains(
                                                    "WorkerPhone:$phone"
                                                ) &&

                                                singleBooking.contains(
                                                    "CustomerPhone:$cphone"
                                                ) &&

                                                !singleBooking.contains(
                                                    "Status:COMPLETED"
                                                )
                                            ) {

                                                alreadyBooked = true
                                            }
                                        }

                                        if (alreadyBooked) {

                                            Toast.makeText(
                                                this,
                                                "You already hired this worker",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        } else {

                                            val bookingData =

                                                "Worker:$name|" +
                                                        "Work:$work|" +
                                                        "WorkerPhone:$phone|" +
                                                        "Customer:$cname|" +
                                                        "CustomerPhone:$cphone|" +
                                                        "CustomerLocation:$clocation|" +
                                                        "Date:$cdate|" +
                                                        "Status:PENDING###"

                                            bookingPrefs.edit()
                                                .putString(
                                                    "bookings",
                                                    oldBookings + bookingData
                                                )
                                                .apply()

                                            Toast.makeText(
                                                this,
                                                "Booking Confirmed",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }

                                .setNegativeButton(
                                    "Cancel",
                                    null
                                )

                                .show()
                        }
                    }

                    cardLayout.addView(imageView)
                    cardLayout.addView(nameText)
                    cardLayout.addView(detailsLayout)

                    cardLayout.addView(callButton)
                    cardLayout.addView(locationButton)
                    cardLayout.addView(profileButton)
                    cardLayout.addView(hireButton)


// ⭐ RATE WORKER BUTTON

                    val rateWorkerButton =
                        Button(this)

                    rateWorkerButton.text =
                        "⭐ Rate Worker"

                    styleButton(rateWorkerButton)

                    rateWorkerButton.setOnClickListener {

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

                            .setTitle("Rate $name")

                            .setView(dialogLayout)

                            .setPositiveButton(
                                "Submit"
                            ) { _, _ ->

                                val selectedRating =
                                    ratingBar.rating

                                val sharedPreferences =
                                    getSharedPreferences(
                                        "WorkersData",
                                        MODE_PRIVATE
                                    )

                                val workers =
                                    sharedPreferences.getString(
                                        "workers",
                                        ""
                                    ) ?: ""

                                val workerList =
                                    workers.split("###")

                                var updatedWorkers = ""

                                for (singleWorker in workerList) {

                                    if (
                                        singleWorker.contains(
                                            "Phone:$phone"
                                        )
                                    ) {

                                        var oldRating = 0f

                                        var totalPeople = 0

                                        val details =
                                            singleWorker.split("|")

                                        for (item in details) {

                                            if (
                                                item.startsWith("Rating:")
                                            ) {

                                                oldRating =
                                                    item.replace(
                                                        "Rating:",
                                                        ""
                                                    ).toFloatOrNull() ?: 0f
                                            }

                                            if (
                                                item.startsWith("People:")
                                            ) {

                                                totalPeople =
                                                    item.replace(
                                                        "People:",
                                                        ""
                                                    ).toIntOrNull() ?: 0
                                            }
                                        }

                                        val totalRating =
                                            (oldRating * totalPeople) +
                                                    selectedRating

                                        val newPeople =
                                            totalPeople + 1

                                        val average =
                                            totalRating / newPeople

                                        var updatedWorker =
                                            singleWorker

                                        if (
                                            updatedWorker.contains(
                                                "Rating:"
                                            )
                                        ) {

                                            updatedWorker =
                                                updatedWorker.replace(
                                                    Regex("Rating:[^|]*"),
                                                    "Rating:${String.format("%.1f", average)}"
                                                )

                                        } else {

                                            updatedWorker +=
                                                "|Rating:${String.format("%.1f", average)}"
                                        }

                                        if (
                                            updatedWorker.contains(
                                                "People:"
                                            )
                                        ) {

                                            updatedWorker =
                                                updatedWorker.replace(
                                                    Regex("People:[^|]*"),
                                                    "People:$newPeople"
                                                )

                                        } else {

                                            updatedWorker +=
                                                "|People:$newPeople"
                                        }

                                        updatedWorkers +=
                                            updatedWorker + "###"

                                    } else {

                                        updatedWorkers +=
                                            singleWorker + "###"
                                    }
                                }

                                sharedPreferences.edit()
                                    .putString(
                                        "workers",
                                        updatedWorkers
                                    )
                                    .apply()

                                Toast.makeText(
                                    this,
                                    "Rating Submitted",
                                    Toast.LENGTH_LONG
                                ).show()

                                loadWorkers(searchText)
                            }

                            .setNegativeButton(
                                "Cancel",
                                null
                            )

                            .show()
                    }

                    cardLayout.addView(rateWorkerButton)

                    workersContainer.addView(cardLayout)
                }
            }
        }

        loadWorkers()

        btnSearch.setOnClickListener {

            val searchText =
                editSearch.text.toString()

            loadWorkers(searchText)
        }
    }


}