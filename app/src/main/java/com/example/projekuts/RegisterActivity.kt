package com.example.projekuts

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val user = findViewById<EditText>(R.id.etRegUser)
        val pass = findViewById<EditText>(R.id.etRegPass)
        val konfirmasi = findViewById<EditText>(R.id.etKonfirmasiPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val spinnerJurusan = findViewById<Spinner>(R.id.spinnerJurusan)
        val reg = findViewById<TextView>(R.id.tvLogin)

        // TAMBAHAN
        val rbLaki = findViewById<RadioButton>(R.id.rbLaki)
        val rbPerempuan = findViewById<RadioButton>(R.id.rbPerempuan)

        val cbMembaca = findViewById<CheckBox>(R.id.cbMembaca)
        val cbGaming = findViewById<CheckBox>(R.id.cbGaming)
        val cbOlahraga = findViewById<CheckBox>(R.id.cbOlahraga)

        sp = getSharedPreferences("DATAUSER", MODE_PRIVATE)

        val jurusan = arrayOf(
            "Pilih Jurusan",
            "Teknik Informatika",
            "Sistem Informasi",
            "Teknik Industri"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            jurusan
        )

        spinnerJurusan.adapter = adapter

        user.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (user.text.toString().trim().isNotEmpty()) user.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = pass.text.toString()

                if (password.length < 6) {
                    pass.error = "Minimal 6 karakter"
                } else {
                    pass.error = null
                }

                if (konfirmasi.text.toString().isNotEmpty()) {
                    if (password != konfirmasi.text.toString()) {
                        konfirmasi.error = "Password tidak sama"
                    } else {
                        konfirmasi.error = null
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        konfirmasi.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (konfirmasi.text.toString() != pass.text.toString()) {
                    konfirmasi.error = "Password tidak sama"
                } else {
                    konfirmasi.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnRegister.setOnClickListener {

            val username = user.text.toString().trim()
            val password = pass.text.toString().trim()
            val konfPass = konfirmasi.text.toString().trim()
            val pilihJurusan = spinnerJurusan.selectedItem.toString()

            val jk = when {
                rbLaki.isChecked -> "Laki-laki"
                rbPerempuan.isChecked -> "Perempuan"
                else -> "Belum Dipilih"
            }

            val listHobi = mutableListOf<String>()

            if (cbMembaca.isChecked) listHobi.add("Membaca")
            if (cbGaming.isChecked) listHobi.add("Gaming")
            if (cbOlahraga.isChecked) listHobi.add("Olahraga")

            val hobi =
                if (listHobi.isEmpty()) "Belum Dipilih"
                else listHobi.joinToString(", ")

            if (username.isEmpty()) {
                user.error = "Isi Username"
                user.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                pass.error = "Isi Password"
                pass.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                pass.error = "Minimal 6 karakter"
                pass.requestFocus()
                return@setOnClickListener
            }

            if (password != konfPass) {
                konfirmasi.error = "Password tidak sama"
                konfirmasi.requestFocus()
                return@setOnClickListener
            }

            if (pilihJurusan == "Pilih Jurusan") {
                Toast.makeText(this, "Pilih Jurusan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Data")
                .setMessage(
                    "Username : $username\n\n" +
                            "Jenis Kelamin : $jk\n\n" +
                            "Hobi : $hobi\n\n" +
                            "Jurusan : $pilihJurusan\n\n" +
                            "Apakah data sudah benar?"
                )
                .setPositiveButton("Ya") { dialog, which ->

                    val edit = sp.edit()
                    edit.putString("username", username)
                    edit.putString("password", password)
                    edit.apply()

                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton("Edit Lagi") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

        reg.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}