package com.example.likhitha

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class FindWorkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_find_worker)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        val editSearch =
            findViewById<EditText>(R.id.editSearch)

        val btnSearch =
            findViewById<Button>(R.id.btnSearch)

        val workersContainer =
            findViewById<LinearLayout>(R.id.workersContainer)

        btnBack.setOnClickListener {
            finish()
        }

        fun loadWorkers(searchText: String = "") {

            workersContainer.removeAllViews()

            val database =
                FirebaseDatabase.getInstance()

            val workersRef =
                database.getReference("Workers")

            workersRef.get()
                .addOnSuccessListener { snapshot ->

                    workersContainer.removeAllViews()

                    if (!snapshot.exists()) {

                        val noWorker =
                            TextView(this)

                        noWorker.text =
                            "No workers found"

                        noWorker.textSize = 20f

                        noWorker.gravity =
                            Gravity.CENTER

                        noWorker.setTextColor(
                            Color.BLACK
                        )

                        workersContainer.addView(
                            noWorker
                        )

                        return@addOnSuccessListener
                    }

                    for (workerSnapshot in snapshot.children) {

                        val name =
                            workerSnapshot.child("name")
                                .value.toString()

                        val work =
                            workerSnapshot.child("work")
                                .value.toString()

                        val phone =
                            workerSnapshot.child("phone")
                                .value.toString()

                        val location =
                            workerSnapshot.child("location")
                                .value.toString()

                        val status =
                            workerSnapshot.child("status")
                                .value.toString()

                        val image =
                            workerSnapshot.child("image")
                                .value.toString()

                        val rating =
                            workerSnapshot.child("rating")
                                .value.toString()

                        val rate =
                            workerSnapshot.child("rate")
                                .value.toString()

                        if (
                            name.lowercase().contains(searchText.lowercase()) ||
                            work.lowercase().contains(searchText.lowercase()) ||
                            location.lowercase().contains(searchText.lowercase())
                        ) {

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

                            // IMAGE

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
                                    image.startsWith("content://")
                                ) {

                                    imageView.setImageURI(
                                        Uri.parse(image)
                                    )

                                } else {

                                    imageView.setImageResource(
                                        R.drawable.worker
                                    )
                                }

                            } catch (e: Exception) {

                                imageView.setImageResource(
                                    R.drawable.worker
                                )
                            }

                            // NAME

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

                            // DETAILS

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
                                "📍 Location : $location"

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

                            // CALL BUTTON

                            val callButton =
                                Button(this)

                            callButton.text =
                                "📞 CALL WORKER"

                            styleButton(callButton)

                            callButton.setOnClickListener {

                                val intent =
                                    Intent(
                                        Intent.ACTION_DIAL,
                                        Uri.parse("tel:$phone")
                                    )

                                startActivity(intent)
                            }

                            // LOCATION BUTTON

                            val locationButton =
                                Button(this)

                            locationButton.text =
                                "📍 OPEN LOCATION"

                            styleButton(locationButton)

                            locationButton.setOnClickListener {

                                val mapIntent =
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                            "https://www.google.com/maps/search/?api=1&query=$location"
                                        )
                                    )

                                startActivity(mapIntent)
                            }

                            // PROFILE BUTTON

                            val profileButton =
                                Button(this)

                            profileButton.text =
                                "👤 VIEW PROFILE"

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

                            // HIRE BUTTON

                            val hireButton =
                                Button(this)

                            hireButton.text =
                                "💼 HIRE WORKER"

                            styleButton(hireButton)

                            if (
                                status.equals("Online", true)
                            ) {

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
                                                customerName.text.toString().trim()

                                            val cphone =
                                                customerPhone.text.toString().trim()

                                            val clocation =
                                                customerLocation.text.toString().trim()

                                            val cdate =
                                                customerDate.text.toString().trim()

                                            if (
                                                cname.isEmpty() ||
                                                cphone.isEmpty() ||
                                                clocation.isEmpty() ||
                                                cdate.isEmpty()
                                            ) {

                                                Toast.makeText(
                                                    this,
                                                    "Fill all details",
                                                    Toast.LENGTH_LONG
                                                ).show()

                                                return@setPositiveButton
                                            }

                                            val bookingsRef =
                                                FirebaseDatabase
                                                    .getInstance()
                                                    .getReference("Bookings")

                                            val customersRef =
                                                FirebaseDatabase
                                                    .getInstance()
                                                    .getReference("Customers")

                                            // SAVE CUSTOMER

                                            val customerData =
                                                hashMapOf(
                                                    "name" to cname,
                                                    "phone" to cphone,
                                                    "address" to clocation
                                                )

                                            customersRef.child(cphone)
                                                .setValue(customerData)

                                            // CHECK EXISTING BOOKING

                                            bookingsRef.get()
                                                .addOnSuccessListener { bookingSnapshot ->

                                                    var alreadyBooked =
                                                        false

                                                    for (booking in bookingSnapshot.children) {

                                                        val bookedWorkerPhone =
                                                            booking.child("workerPhone")
                                                                .value.toString()

                                                        val bookedCustomerPhone =
                                                            booking.child("customerPhone")
                                                                .value.toString()

                                                        val bookingStatus =
                                                            booking.child("status")
                                                                .value.toString()

                                                        if (
                                                            bookedWorkerPhone == phone &&
                                                            bookedCustomerPhone == cphone &&
                                                            bookingStatus != "COMPLETED"
                                                        ) {

                                                            alreadyBooked =
                                                                true
                                                        }
                                                    }

                                                    if (alreadyBooked) {

                                                        Toast.makeText(
                                                            this,
                                                            "You already hired this worker",
                                                            Toast.LENGTH_LONG
                                                        ).show()

                                                    } else {

                                                        val bookingId =
                                                            bookingsRef.push().key ?: ""

                                                        val bookingData =
                                                            hashMapOf(
                                                                "workerName" to name,
                                                                "workerWork" to work,
                                                                "workerPhone" to phone,
                                                                "customerName" to cname,
                                                                "customerPhone" to cphone,
                                                                "customerLocation" to clocation,
                                                                "date" to cdate,
                                                                "status" to "PENDING"
                                                            )

                                                        bookingsRef.child(bookingId)
                                                            .setValue(bookingData)

                                                            .addOnSuccessListener {

                                                                Toast.makeText(
                                                                    this,
                                                                    "Booking Confirmed",
                                                                    Toast.LENGTH_LONG
                                                                ).show()
                                                            }
                                                    }
                                                }
                                        }

                                        .setNegativeButton(
                                            "Cancel",
                                            null
                                        )

                                        .show()
                                }

                            } else {

                                hireButton.text =
                                    "❌ WORKER OFFLINE"
                            }

                            // RATE BUTTON

                            val rateWorkerButton =
                                Button(this)

                            rateWorkerButton.text =
                                "⭐ RATE WORKER"

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

                                        workersRef.child(phone)
                                            .get()
                                            .addOnSuccessListener { latestSnapshot ->

                                                val oldTotal =
                                                    latestSnapshot.child("totalRating")
                                                        .value.toString()
                                                        .toFloatOrNull() ?: 0f

                                                val oldCount =
                                                    latestSnapshot.child("ratingCount")
                                                        .value.toString()
                                                        .toIntOrNull() ?: 0

                                                val newTotal =
                                                    oldTotal + selectedRating

                                                val newCount =
                                                    oldCount + 1

                                                val average =
                                                    newTotal / newCount

                                                workersRef.child(phone)
                                                    .child("totalRating")
                                                    .setValue(newTotal)

                                                workersRef.child(phone)
                                                    .child("ratingCount")
                                                    .setValue(newCount)

                                                workersRef.child(phone)
                                                    .child("rating")
                                                    .setValue(
                                                        String.format("%.1f", average)
                                                    )

                                                Toast.makeText(
                                                    this,
                                                    "Rating Submitted",
                                                    Toast.LENGTH_LONG
                                                ).show()

                                                loadWorkers(searchText)
                                            }
                                    }

                                    .setNegativeButton(
                                        "Cancel",
                                        null
                                    )

                                    .show()
                            }

                            // ADD VIEWS

                            cardLayout.addView(imageView)
                            cardLayout.addView(nameText)
                            cardLayout.addView(detailsLayout)

                            cardLayout.addView(callButton)
                            cardLayout.addView(locationButton)
                            cardLayout.addView(profileButton)
                            cardLayout.addView(hireButton)
                            cardLayout.addView(rateWorkerButton)

                            workersContainer.addView(cardLayout)
                        }
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