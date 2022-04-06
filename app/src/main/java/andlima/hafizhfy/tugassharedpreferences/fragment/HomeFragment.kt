package andlima.hafizhfy.tugassharedpreferences.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andlima.hafizhfy.tugassharedpreferences.R
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.logout_alert.view.*

class HomeFragment : Fragment() {

    private val sharedPrefFile = "kotlinsharedpreference"
    private var doubleBackToExit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doubleBackExit()

        val sharedPreferences : SharedPreferences =
            requireContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val sharedUsername = sharedPreferences.getString("name_key", "name_key")
        tv_show_name.append("$sharedUsername")

        btn_logout.setOnClickListener {
            val logOutAlert = LayoutInflater.from(requireContext()).inflate(R.layout.logout_alert, null, false)

            val alert = AlertDialog.Builder(requireContext())
                .setView(logOutAlert)
                .create()

            logOutAlert.btn_logout_no.setOnClickListener {
                alert.dismiss()
            }

            logOutAlert.btn_logout_yes.setOnClickListener {
                val editor = sharedPreferences.edit()
                editor.putBoolean("is_login", false)
                editor.apply()

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment)
                closeFragment()

                Toast.makeText(requireContext(), "You're Logged Out", Toast.LENGTH_LONG).show()
                alert.dismiss()
            }

            alert.show()
        }
    }

    private fun closeFragment() {
        activity?.fragmentManager?.popBackStack()
    }

    private fun doubleBackExit() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (doubleBackToExit) {
                    activity!!.finish()
                } else {
                    doubleBackToExit = true
                    Toast.makeText(requireContext(), "Press again to exit", Toast.LENGTH_SHORT).show()

                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        kotlin.run {
                            doubleBackToExit = false
                        }
                    }, 2000)
                }
            }
        })
    }
}