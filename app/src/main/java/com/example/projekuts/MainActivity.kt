package com.example.projekuts

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nama = intent.getStringExtra("nama")
        val tv = findViewById<TextView>(R.id.tvWelcome)
        val btn = findViewById<Button>(R.id.btnDaftar)

        if (nama != null) {
            tv.text = "Selamat Datang $nama"
        } else {
            tv.text = "Selamat Datang"
        }

        btn.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }
    }
}