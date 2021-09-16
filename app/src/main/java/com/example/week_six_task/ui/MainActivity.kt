package com.example.week_six_task.ui

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week_six_task.*
import com.example.week_six_task.R
import com.example.week_six_task.adapter.MainRecyclerAdapter
import com.example.week_six_task.model.MainContact
import com.google.firebase.database.*
import android.util.Pair as UtilPair

class MainActivity : AppCompatActivity(), MainRecyclerAdapter.ContactClickListener {
    lateinit var mainRecView: RecyclerView
    lateinit var mainContactList: MutableList<MainContact>
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Finding the views in the activity_main layout and assigning them to variables
        val addButton = findViewById<ImageButton>(R.id.addBtn)
        val nextBtn = findViewById<ImageButton>(R.id.nextBtn)

        //setting onclick listener to the add float button
        addButton.setOnClickListener {
            val intent = Intent(this, CreateContact::class.java)
            startActivity(intent)
        }

        //setting onclick listener to the nextBtn
        nextBtn.setOnClickListener {
            val intent = Intent(this, ReadContact::class.java)
            startActivity(intent)
        }

        //Setting the recyclerview in the activity_main_layout to a Linear Layout
        mainRecView = findViewById(R.id.main_recView)
        mainRecView.layoutManager = LinearLayoutManager(this)
        mainRecView.setHasFixedSize(true)
        mainContactList = mutableListOf()

        //Calling the retrieveContact function to retrieve contact from the firebase database
        DB.retrieveContact(this, mainContactList, mainRecView)

    }


    //The
    override fun onContactClick(position: Int, view: View) {
        val nameIcon = view.findViewById<TextView>(R.id.contactIcon_main)
        val name = view.findViewById<TextView>(R.id.contactName_main)
        val viewIcon = view.findViewById<View>(R.id.viewIcon_main)
        val mobile = view.findViewById<TextView>(R.id.mobile_main)
        Toast.makeText(this, "worked", Toast.LENGTH_SHORT).show()
        val options = ActivityOptions.makeSceneTransitionAnimation(this, UtilPair(nameIcon, "sharedAnim"), UtilPair(name, "Name"), UtilPair(viewIcon, "Container"))
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("selected_contact", mainContactList[position])
        startActivity(intent, options.toBundle())
    }

}