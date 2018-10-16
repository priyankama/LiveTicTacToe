package com.example.bandhan.livetictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import  com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mAuth: FirebaseAuth? = null
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference
    var userEmail : String ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        userEmail = intent.getStringExtra("email")
        incomingRequest()
    }
    fun btnRequestClicked(view : View){
        var email = etReqEmail.text.toString()
        var currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            myRef.child("GameUsers").child(email).child("Response").push().setValue(currentUser.uid)
        }
    }
    fun btnAcceptClicked(view: View){

        var email = etReqEmail.text.toString()
    }
    fun incomingRequest(){
        myRef.child("GameUsers").child(splitString(userEmail!!)).child("Response")
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {


                    }

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }
    fun splitString(email : String):String{
        var split = email.split("@")
        return split[0]
    }
}
