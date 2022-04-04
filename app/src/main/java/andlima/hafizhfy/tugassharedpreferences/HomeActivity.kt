package andlima.hafizhfy.tugassharedpreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.logout_alert.view.*

class HomeActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences : SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // Get data dari SharedPreferences
        val sharedUsername = sharedPreferences.getString("name_key", "name_key")
        tv_show_name.append(" $sharedUsername")


        btn_logout.setOnClickListener {
            val customDialog = LayoutInflater.from(this).inflate(R.layout.logout_alert, null, false)

            val alert = AlertDialog.Builder(this)
                .setView(customDialog)
                .create()

            customDialog.btn_logout_no.setOnClickListener {
                Toast.makeText(this, "Logout Canceled", Toast.LENGTH_LONG).show()
                alert.dismiss()
            }

            customDialog.btn_logout_yes.setOnClickListener {
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()

                Toast.makeText(this, "You're Logged Out", Toast.LENGTH_LONG).show()
            }

            alert.show()
        }
    }
}