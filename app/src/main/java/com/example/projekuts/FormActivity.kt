package com.example.projekuts

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val nama = findViewById<EditText>(R.id.etNama)
        val email = findViewById<EditText>(R.id.etEmail)
        val hp = findViewById<EditText>(R.id.etHp)
        val laki = findViewById<RadioButton>(R.id.rbLaki)
        val spin = findViewById<Spinner>(R.id.spinnerSeminar)
        val cek = findViewById<CheckBox>(R.id.cbSetuju)
        val btn = findViewById<Button>(R.id.btnSubmit)

        val data = arrayOf(
            "Android Seminar",
            "AI Seminar",
            "Web Seminar",
            "Cloud Seminar",
            "Cyber Security"
        )

        spin.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            data
        )


        nama.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isi = s.toString().trim()

                when {
                    isi.isEmpty() -> nama.error = "Isi nama"
                    isi.length < 4 -> nama.error = "Minimal 4 huruf"
                    else -> nama.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                val isi = s.toString().trim()

                when {
                    isi.isEmpty() -> {
                        email.error = "Isi email"
                    }

                    !isi.contains("@") -> {
                        email.error = "Email harus mengandung @"
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(isi).matches() -> {
                        email.error = "Email tidak valid"
                    }

                    else -> {
                        email.error = null
                    }
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {}
        })


        hp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                val nohp = s.toString().trim()

                when {
                    nohp.isEmpty() -> hp.error = "Isi nomor HP"
                    !nohp.matches(Regex("[0-9]+")) -> hp.error = "Hanya angka"
                    !nohp.startsWith("08") -> hp.error = "Harus diawali 08"
                    nohp.length < 10 -> hp.error = "Minimal 10 digit"
                    nohp.length > 13 -> hp.error = "Maksimal 13 digit"
                    else -> hp.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        cek.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Persetujuan dicentang", Toast.LENGTH_SHORT).show()
            }
        }


        btn.setOnClickListener {

            if (nama.error != null || nama.text.toString().trim().isEmpty()) {
                nama.requestFocus()
                return@setOnClickListener
            }

            if (email.error != null || email.text.toString().trim().isEmpty()) {
                email.requestFocus()
                Toast.makeText(this, "Periksa Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (hp.error != null || hp.text.toString().trim().isEmpty()) {
                hp.requestFocus()
                Toast.makeText(this, "Periksa Nomor HP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cek.isChecked) {
                Toast.makeText(this, "Centang persetujuan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val jk = if (laki.isChecked) "Laki-laki" else "Perempuan"

            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah data sudah benar?")
                .setPositiveButton("Ya") { _, _ ->

                    val i = Intent(this, ResultActivity::class.java)
                    i.putExtra("nama", nama.text.toString())
                    i.putExtra("email", email.text.toString())
                    i.putExtra("hp", hp.text.toString())
                    i.putExtra("jk", jk)
                    i.putExtra("seminar", spin.selectedItem.toString())
                    startActivity(i)

                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }
}