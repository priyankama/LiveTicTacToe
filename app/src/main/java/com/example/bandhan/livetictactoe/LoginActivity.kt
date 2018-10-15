package com.example.bandhan.livetictactoe

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.database.FirebaseDatabase




class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance();
    }
    fun btnLogin(view : View){
        LoginToFirebase(etEmail.text.toString(),etPassword.text.toString())

    }
    fun LoginToFirebase(email : String,password  : String){
        mAuth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext,"Successful login",Toast.LENGTH_SHORT).show()
                        LoadMain()
                    }else{
                        Toast.makeText(applicationContext,"UN-Successful login",Toast.LENGTH_SHORT).show()
                    }


                }
        var currentUser = mAuth!!.currentUser
        if(currentUser!=null){
            myRef.child("GameUsers").child(splitString(currentUser.email.toString())).setValue(currentUser.uid)

        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onStart() {
        LoadMain()
    }
    fun LoadMain(){
        var currentUser = mAuth!!.currentUser
        if(currentUser!=null){
            val intent = Intent(applicationContext,MainActivity::class.java)
            intent.putExtra("email",currentUser.email)
            intent.putExtra("uid",currentUser.uid)
        }

    }
    fun splitString(email : String):String{
        var split = email.split("@")
        return split[0]
    }
}
