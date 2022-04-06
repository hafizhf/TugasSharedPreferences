package andlima.hafizhfy.tugassharedpreferences.fragment

import andlima.hafizhfy.tugassharedpreferences.HomeActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andlima.hafizhfy.tugassharedpreferences.R
import android.content.Context
import android.content.SharedPreferences
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = "Hafizh"
        val password = "12345"

        val sharedPreferences : SharedPreferences =
            requireContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // Cek sudah login atau belum --------------------------------------------------------------
        val sharedUsername = sharedPreferences.getString("name_key", "name_key")
        val sharedPassword = sharedPreferences.getString("pwd_key", "pwd_key")
        val isLogin = sharedPreferences.getBoolean("is_login", false)

//        if (sharedUsername.equals(username) && sharedPassword.equals(password) && isLogin) {
        if (isLogin) {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
            closeFragment()
        }

        // Show password ---------------------------------------------------------------------------
        btn_show_pwd.setOnClickListener {
            val hidden = PasswordTransformationMethod.getInstance()
            val show = HideReturnsTransformationMethod.getInstance()

            if (et_password.transformationMethod == hidden) {
                et_password.transformationMethod = show
            } else {
                et_password.transformationMethod = hidden
            }
        }

        // Action on Login button ------------------------------------------------------------------
        btn_login.setOnClickListener {
            when {
                et_username.text.toString() == "" -> {
                    isLoginCard.visibility = View.VISIBLE
                    isLoginSuccess.text = "Please input username"
                }
                et_username.text.toString() != sharedUsername -> {
                    isLoginCard.visibility = View.VISIBLE
                    isLoginSuccess.text = "Unknown username"
                }
                et_password.text.toString() != sharedPassword -> {
                    isLoginCard.visibility = View.VISIBLE
                    isLoginSuccess.text = "Wrong password"
                }
                else -> {
                    val editor : SharedPreferences.Editor = sharedPreferences.edit()

//                    editor.putString("name_key", et_username.text.toString())
//                    editor.putString("pwd_key", et_password.text.toString())
                    editor.putBoolean("is_login", true)
                    editor.apply()

                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
                    closeFragment()
                    Toast.makeText(requireContext(), "Welcome back ${et_username.text}", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Action on Register button ---------------------------------------------------------------
        btn_register.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun closeFragment() {
        activity?.fragmentManager?.popBackStack()
    }

//    override fun onPause() {
//        super.onPause()
//        closeFragment()
//    }
}