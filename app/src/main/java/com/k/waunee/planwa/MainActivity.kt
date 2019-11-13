package com.k.waunee.planwa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.k.waunee.planwa.Statement.Companion.STATEMENT_KEY
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    var TAG :String = "MainActivity"
    lateinit var database :DatabaseReference
    private val list: MutableList<User> = mutableListOf()
    private val listStatement: MutableList<Statement> = mutableListOf()

    @SuppressLint("SimpleDateFormat")
    val strDate = SimpleDateFormat("dd-MM-yyyy")
    @SuppressLint("SimpleDateFormat")
    val strDateTime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance().reference

        writeNewStatement("Kit Kat", "18","Shopping")
        writeNewStatement("Pen", "25","Other")

//        writeNewStatementDate("Pen", "25","Other")
        initStatement()

    }

    private fun writeNewStatement(name: String?, price :String?, type :String?){

        val statement =  Statement(name, strDateTime.format(Date()), price, type)
//        new children
//        val statement2 =  Statement("Oreo", strDateTime.format(Date()), "25", type)
//        val map = HashMap<String, Statement>()
//        map.put("T05", statement)
//        map.put("T06", statement2)
        database.child(STATEMENT_KEY).child(strDate.format(Date())).push().setValue(statement)
    }


    private fun initStatement() {
        val listener = object :ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listStatement.clear()
                // per date (child) has children
                dataSnapshot.child("12-11-2019").children.mapNotNullTo(listStatement) {
                    it.getValue<Statement>(Statement::class.java)
                }
                setTextStatement(listStatement)
                Log.d(TAG,"size : ${listStatement.size}")
                Log.d(TAG,"key : ${dataSnapshot.hasChild("12-11-2019")}")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG,"loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        database.child(STATEMENT_KEY).addListenerForSingleValueEvent(listener)
    }

    private fun writeNewUser(userId: String, name: String, email: String?) {
        val user = User(name, email)
        database.child("users").child(userId).setValue(user)
    }

    private fun setTextStatement(list :MutableList<Statement>){
        val textView = findViewById<TextView>(R.id.text_data)
        var msg :String = ""
        for (item in list){
            msg += "${item.Date} ${item.Name} ${item.Type} ${item.Price}\n"
        }
        textView.text = msg
    }
}
