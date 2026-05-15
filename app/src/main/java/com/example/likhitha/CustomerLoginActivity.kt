package com.example.likhitha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CustomerLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_customer_login
        )

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }

        val editCustomerPhone =
            findViewById<EditText>(
                R.id.editCustomerPhone
            )

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val customerPhone =
                editCustomerPhone.text.toString().trim()

            if (customerPhone.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter Phone Number",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val customersRef =
                FirebaseDatabase.getInstance()
                    .getReference("Customers")

            customersRef.child(customerPhone)
                .get()

                .addOnSuccessListener { snapshot ->

                    if (snapshot.exists()) {

                        val intent =
                            Intent(
                                this,
                                CustomerBookingsActivity::class.java
                            )

                        intent.putExtra(
                            "customerPhone",
                            customerPhone
                        )

                        startActivity(intent)

                    } else {

                        Toast.makeText(
                            this,
                            "Customer Not Registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}