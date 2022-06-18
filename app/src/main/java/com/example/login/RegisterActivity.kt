package com.example.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.login.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnSubmit.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.etAccount.text.toString()
        val password = binding.etPwd.text.toString()
        val confirmPass = binding.etConfirmPwd.text.toString()

        if (validateForm()) {
            if (password == confirmPass) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            auth.signOut()
                            startActivity(Intent(this,LoginActivity::class.java))

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, task.exception?.message,
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Password is not matching!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        //Validate the email and password
        var valid = true

        val name = binding.etAccount.text.toString()
        if (TextUtils.isEmpty(name)) {
            binding.etAccount.error = "Required"
            valid = false
        } else {
            binding.etAccount.error = null
        }

        val email = binding.etConfirmPwd.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.etConfirmPwd.error = "Required."
            valid = false
        } else {
            binding.etConfirmPwd.error = null
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