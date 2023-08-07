package com.example.csc306_newsapp.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.csc306_newsapp.LoginActivity
import com.example.csc306_newsapp.MainActivity
import com.example.csc306_newsapp.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * This class is responsible for the user register page
 */
class RegisterFragment : Fragment() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirm: EditText
    private lateinit var mAuth: FirebaseAuth

    /**
     * Function to inflate the fragment layout
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register, container, false)

        username = view.findViewById(R.id.input_email_reg)
        password = view.findViewById(R.id.input_password_reg)
        confirm = view.findViewById(R.id.input_confirm_reg)
        mAuth = Firebase.auth

        view.findViewById<Button>(R.id.btn_login_reg).setOnClickListener{
            val intent = Intent (requireActivity(), LoginActivity::class.java)
            requireActivity().startActivity(intent)

            //var navRegister = activity as FragmentNavigation
            //navRegister.navigateFrag(LoginFragment(), false)
        }

        view.findViewById<Button>(R.id.btn_register_reg).setOnClickListener{
            validateEmptyForm()
        }
        return view
    }

    /**
     * This function checks the input user data
     */
    private fun firebaseSignUp(){
        //btn_register_reg.isEnabled = false
        //btn_register_reg.alpha = 0.5f
        mAuth.createUserWithEmailAndPassword(username.text.toString(),
        password.text.toString()).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                /*var navHome = activity as FragmentNavigation
                navHome.navigateFrag(NewsFragment(),addToStack = true)*/
                //Snackbar.make(this.requireView(),"Welcome!",Snackbar.LENGTH_SHORT).show()
            }else{
                //btn_register_reg.isEnabled = true
                //btn_register_reg.alpha = 1.0f
                Snackbar.make(this.requireView(), task.exception?.message.toString(),Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Validating format of the user log in data
     */
    private fun validateEmptyForm(){
        val icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_error_24)
        icon?.setBounds(0,0,icon.intrinsicWidth,icon.intrinsicHeight)
        when{
            TextUtils.isEmpty(username.text.toString().trim())->{
                username.setError("Please Enter Email",icon)
            }
            TextUtils.isEmpty(password.text.toString().trim())->{
                password.setError("Please Enter Password",icon)
            }
            TextUtils.isEmpty(confirm.text.toString().trim())->{
                confirm.setError("Please Enter Password Again",icon)
            }

            username.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() &&
                    confirm.text.toString().isNotEmpty() ->
            {
                if(isValidEmail(username.text)){
                    if(password.text.toString().length>=5){
                        if (password.text.toString() == confirm.text.toString()){
                            firebaseSignUp()

                        } else {
                            confirm.setError("Passwords do not match",icon)
                        }
                    }else{
                        password.setError("Password must be at least 6 characters", icon)
                    }
                }else{
                    username.setError("Please Enter Valid Email", icon)
                }
            }
        }
    }

    /**
     * This method validates entered email address
     */
    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}