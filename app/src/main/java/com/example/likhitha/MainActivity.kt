package com.example.likhitha

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            WorkerHiringApp()
        }
    }
}

@Composable
fun WorkerHiringApp() {

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFDFBFF),
                            Color(0xFFF3EEFF),
                            Color(0xFFE6DAFF),
                            Color(0xFFD7C2FF)
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.Center
            ) {

                // WELCOME TEXT
                Text(
                    text = "Welcome to",
                    fontSize = 24.sp,
                    color = Color.DarkGray
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                // APP NAME
                Text(
                    text = "Mane-Kelsa",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5B2C91)
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // SUBTITLE
                Text(
                    text = "Your trusted partner\nfor every work",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                // WORKER IMAGE
                Image(
                    painter = painterResource(
                        id = R.drawable.worker
                    ),

                    contentDescription = "Worker",

                    modifier = Modifier
                        .size(240.dp)
                        .clip(RoundedCornerShape(30.dp))
                )

                Spacer(
                    modifier = Modifier.height(35.dp)
                )

                // FIND WORKERS BUTTON
                Button(
                    onClick = {

                        val intent =
                            Intent(
                                context,
                                FindWorkerActivity::class.java
                            )

                        context.startActivity(intent)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp),

                    shape = RoundedCornerShape(28.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7E57C2)
                    )
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )

                        Spacer(
                            modifier = Modifier.size(10.dp)
                        )

                        Text(
                            text = "Find Workers",
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // REGISTER WORKER BUTTON
                Button(
                    onClick = {

                        val intent =
                            Intent(
                                context,
                                RegisterWorkerActivity::class.java
                            )

                        context.startActivity(intent)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp),

                    shape = RoundedCornerShape(28.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8E63D2)
                    )
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "Register"
                        )

                        Spacer(
                            modifier = Modifier.size(10.dp)
                        )

                        Text(
                            text = "Register as Worker",
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // WORKER LOGIN BUTTON
                Button(
                    onClick = {

                        val intent =
                            Intent(
                                context,
                                WorkerLoginActivity::class.java
                            )

                        context.startActivity(intent)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp),

                    shape = RoundedCornerShape(28.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9C74E0)
                    )
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Login"
                        )

                        Spacer(
                            modifier = Modifier.size(10.dp)
                        )

                        Text(
                            text = "Worker Login",
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // MY BOOKINGS BUTTON
                Button(
                    onClick = {

                        val intent =
                            Intent(
                                context,
                                CustomerLoginActivity::class.java
                            )

                        context.startActivity(intent)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp),

                    shape = RoundedCornerShape(28.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFAA82EE)
                    )
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Bookings"
                        )

                        Spacer(
                            modifier = Modifier.size(10.dp)
                        )

                        Text(
                            text = "My Bookings",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}