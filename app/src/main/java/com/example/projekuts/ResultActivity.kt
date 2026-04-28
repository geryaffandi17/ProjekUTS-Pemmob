package com.example.projekuts

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tv = findViewById<TextView>(R.id.tvHasil)
        val btn = findViewById<Button>(R.id.btnBack)

        val hasil = """
Pendaftaran Berhasil

Nama : ${intent.getStringExtra("nama")}
Email : ${intent.getStringExtra("email")}
HP : ${intent.getStringExtra("hp")}
JK : ${intent.getStringExtra("jk")}
Seminar : ${intent.getStringExtra("seminar")}
        """.trimIndent()

        tv.text = hasil

        btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}