package com.example.projekuts

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user = findViewById<EditText>(R.id.etUser)
        val pass = findViewById<EditText>(R.id.etPass)
        val login = findViewById<Button>(R.id.btnLogin)
        val reg = findViewById<TextView>(R.id.tvRegister)

        sp = getSharedPreferences("DATAUSER", MODE_PRIVATE)

        user.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                val username = s.toString().trim()

                when {
                    username.isEmpty() -> user.error = "Isi Username"
                    username.length < 4 -> user.error = "Minimal 4 karakter"
                    else -> user.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                val password = s.toString().trim()

                when {
                    password.isEmpty() -> pass.error = "Isi Password"
                    password.length < 6 -> pass.error = "Minimal 6 karakter"
                    else -> pass.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        login.setOnClickListener {

            val username = user.text.toString().trim()
            val password = pass.text.toString().trim()

            val simpanUser = sp.getString("username", "")
            val simpanPass = sp.getString("password", "")

            if (user.error != null || username.isEmpty()) {
                user.requestFocus()
                return@setOnClickListener
            }

            if (pass.error != null || password.isEmpty()) {
                pass.requestFocus()
                return@setOnClickListener
            }

            if (username == simpanUser && password == simpanPass) {

                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()

                val i = Intent(this, MainActivity::class.java)
                i.putExtra("nama", username)
                startActivity(i)
                finish()

            } else {
                Toast.makeText(this, "Username / Password Salah", Toast.LENGTH_SHORT).show()
            }
        }

        reg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}