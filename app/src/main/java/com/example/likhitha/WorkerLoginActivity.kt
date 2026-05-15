package com.example.likhitha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class WorkerLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_worker_login)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }

        val editPhoneLogin =
            findViewById<EditText>(R.id.editPhone)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val loginPhone =
                editPhoneLogin.text.toString().trim()

            if (loginPhone.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter Phone Number",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // FIREBASE

            val workersRef =
                FirebaseDatabase.getInstance()
                    .getReference("Workers")

            workersRef.child(loginPhone)
                .get()
                .addOnSuccessListener { snapshot ->

                    if (snapshot.exists()) {

                        val intent =
                            Intent(
                                this,
                                WorkerDashboardActivity::class.java
                            )

                        intent.putExtra(
                            "phone",
                            loginPhone
                        )

                        startActivity(intent)

                    } else {

                        Toast.makeText(
                            this,
                            "Worker Not Registered",
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