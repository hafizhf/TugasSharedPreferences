package andlima.hafizhfy.tugassharedpreferences.fragment

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
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences : SharedPreferences =
            requireContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        btn_show_pwd.setOnClickListener {
            showPassword(et_new_password)
        }

        btn_show_repwd.setOnClickListener {
            showPassword(et_reenter_password)
        }

        btn_register.setOnClickListener {
            val newUsername = et_new_username.text.toString()
            val newPassword = et_new_password.text.toString()
            val rePassword = et_reenter_password.text.toString()

            if (rePassword != newPassword) {

                isLoginCard.visibility = View.VISIBLE
                isLoginSuccess.text = "Re-enter password mismatch"

            } else {

                val editor : SharedPreferences.Editor = sharedPreferences.edit()

                editor.putString("name_key", newUsername)
                editor.putString("pwd_key", newPassword)
                editor.putBoolean("is_login", true)
                editor.apply()

                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_homeFragment)
                closeFragment()
                Toast.makeText(requireContext(), "Register Success, welcome ${et_new_username.text}!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showPassword(editText: EditText) {
        val hidden = PasswordTransformationMethod.getInstance()
        val show = HideReturnsTransformationMethod.getInstance()

        if (editText.transformationMethod == hidden) {
            editText.transformationMethod = show
        } else {
            editText.transformationMethod = hidden
        }
    }

    private fun closeFragment() {
        activity?.fragmentManager?.popBackStack()
    }
}