package com.example.ksharsutra

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomePageActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val navigation_profile = findViewById<ImageView>(R.id.navigation_profile)
        val proceedButton = findViewById<Button>(R.id.proceed)

        navigation_profile.setOnClickListener {
            val intent = Intent(this, UserDetailsActivity::class.java)
            startActivity(intent)
        }

        proceedButton.setOnClickListener {
            val intent = Intent(this, PredictionActivity::class.java)
            startActivity(intent)
        }
    }
}