package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.login.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email: String = binding.etEmail.text.toString().trim{it <= ' '}
            val pwd: String = binding.etPwd.text.toString().trim{it <= ' '}


            if(validateForm()) {
                firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener {
                    if(it.isSuccessful) {
                        val intent =Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                if(email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
                }
                else if(email.isEmpty()){
                    Toast.makeText(this,"Please Enter Email !!",Toast.LENGTH_SHORT).show()
                } else if(pwd.isEmpty()){
                    Toast.makeText(this,"Please Enter Password !!",Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        //check if user is Logged
        if(firebaseAuth.currentUser != null) {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    private fun validateForm(): Boolean {
        //Validate the email and password
        var valid = true

        val email = binding.etEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = "Required"
            valid = false
        } else {
            binding.etEmail.error = null
        }

        val password = binding.etPwd.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.etPwd.error = "Required"
            valid = false
        } else if (password.length < 6) {
            binding.etPwd.error = "Password should be more than 6 characters"
            valid = false
        } else {
            binding.etPwd.error = null
        }
        return valid
    }
}