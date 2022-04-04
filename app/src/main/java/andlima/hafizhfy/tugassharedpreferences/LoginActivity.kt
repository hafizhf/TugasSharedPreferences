package andlima.hafizhfy.tugassharedpreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    // Global Variable
    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = "Hafizh"
        val password = "12345"

        val sharedPreferences : SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // Cek sudah login atau belum
        val sharedUsername = sharedPreferences.getString("name_key", "name_key")
        val sharedPassword = sharedPreferences.getString("pwd_key", "pwd_key")
        if (sharedUsername.equals(username) && sharedPassword.equals(password)) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()

            Toast.makeText(this, "Welcome back, $sharedUsername!", Toast.LENGTH_LONG).show()
        }

        btn_login.setOnClickListener {
            when {
                et_username.text.toString() != username -> {
                    isLoginCard.visibility = View.VISIBLE
                    isLoginSuccess.text = "Unknown Username"
                }
                et_password.text.toString() != password -> {
                    isLoginCard.visibility = View.VISIBLE
//                    isLoginSuccess.setTextColor(Color.RED)
                    isLoginSuccess.text = "Wrong Password"
                }
                else -> {
                    val editor : SharedPreferences.Editor = sharedPreferences.edit()

                    editor.putString("name_key", et_username.text.toString())
                    editor.putString("pwd_key", et_password.text.toString())
                    editor.apply()

                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()

                    Toast.makeText(this, "Welcome ${et_username.text}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}