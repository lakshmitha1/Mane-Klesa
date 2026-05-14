package com.example.likhitha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
                editPhoneLogin.text.toString()

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

            val workersList =
                workersData.split("###")

            var workerFound = false

            for (worker in workersList) {

                if (
                    worker.contains("Phone:$loginPhone")
                ) {

                    workerFound = true

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

                    break
                }
            }

            if (!workerFound) {

                Toast.makeText(
                    this,
                    "Worker Not Registered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}