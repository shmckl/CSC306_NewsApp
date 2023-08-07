package com.example.csc306_newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import com.example.csc306_newsapp.fragments.RegisterFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var mAuth: FirebaseAuth

    /**
     * inflating user log in page and setting up button listeners
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.input_email)
        password = findViewById(R.id.input_password)
        mAuth = Firebase.auth

        Firebase.auth.signOut()
        findViewById<Button>(R.id.btn_register).setOnClickListener{
            supportFragmentManager.beginTransaction()
                .add(R.id.login_container, RegisterFragment())
                .commit()
        }

        findViewById<Button>(R.id.btn_login).setOnClickListener{
            validateForm(it)
        }
    }

/*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        username = view.findViewById(R.id.input_email)
        password = view.findViewById(R.id.input_password)
        mAuth = Firebase.auth

        view.findViewById<Button>(R.id.btn_register).setOnClickListener{
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(RegisterFragment(), false)
        }

        view.findViewById<Button>(R.id.btn_login).setOnClickListener{
            validateForm()
        }
        return view
    }*/

    /**
     * This function checks the input user data
     */
    private fun firebaseSignIn(view : View){
        //btn_login.isEnabled = false
        //btn_login.alpha = 0.5f
        mAuth.signInWithEmailAndPassword(username.text.toString(),
            password.text.toString()).addOnCompleteListener{
                task ->
            if(task.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                //var navHome = activity as FragmentNavigation
                //navHome.navigateFrag(NewsFragment(),addToStack = true)
            }else{
                //btn_login.isEnabled = false
                //btn_login.alpha = 0.5f
                Snackbar.make(view, task.exception?.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Validating format of the user log in data
     */
    private fun validateForm(view : View){
        val icon = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_error_24)
        icon?.setBounds(0,0,icon.intrinsicWidth,icon.intrinsicHeight)
        when{
            TextUtils.isEmpty(username.text.toString().trim())->{
                username.setError("Please Enter Email",icon)
            }
            TextUtils.isEmpty(password.text.toString().trim())->{
                password.setError("Please Enter Password",icon)
            }

            username.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() ->
            {
                if(isValidEmail(username.text)){
                    firebaseSignIn(view)

                }else{
                    username.setError("Please Enter Valid Email", icon)
                }
            }
        }
    }
    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target.toString()).matches()
    }

    /**
     * ensures that the user can't return to the app once logged out
     */
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
