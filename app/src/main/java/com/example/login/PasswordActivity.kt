package com.example.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.login.databinding.ActivityMainBinding
import com.example.login.databinding.ActivityPasswordBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.zip.CheckedInputStream

class PasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnSubmit.setOnClickListener {
            val newPwd = binding.etNewPwd.text.toString()
            val pwdAgain = binding.etPwdAgain.text.toString()

            if(pwdAgain == newPwd && pwdAgain.isNotEmpty() && newPwd.isNotEmpty()) {
                setNewPwd(newPwd)
            } else {
                Toast.makeText(this, "Check Password Again !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setNewPwd(pwd: String){
        if(CheckInternet.isNetworkAvailable(this)) {
            val user = Firebase.auth.currentUser

            user!!.updatePassword(pwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully updated!!", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                        startActivity(Intent(this,LoginActivity::class.java))
                        overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in,
                            com.google.android.material.R.anim.abc_fade_out)
                    }
                }
        } else {
            Toast.makeText(this, "No Internet connection available", Toast.LENGTH_SHORT).show()
        }
    }
}