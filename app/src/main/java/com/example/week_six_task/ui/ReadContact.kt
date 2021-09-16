package com.example.week_six_task.ui

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week_six_task.DB
import com.example.week_six_task.model.Contact
import com.example.week_six_task.R
import com.example.week_six_task.adapter.RecyclerViewAdapter

class ReadContact : AppCompatActivity() {
    lateinit var recView: RecyclerView
    lateinit var contactList: MutableList<Contact>
    lateinit var permissionList: Array<String>
    lateinit var errorTxt: TextView
    var cursor: Cursor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_contact)

        contactList = mutableListOf()
        recView = findViewById(R.id.recView)
        val requestPermission = findViewById<Button>(R.id.requestPermission)
        val backBtn = findViewById<ImageButton>(R.id.back)
        permissionList = arrayOf(Manifest.permission.READ_CONTACTS)

        backBtn.setOnClickListener {
            onBackPressed()
        }


        requestPermission.setOnClickListener {

            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED){
                requestPermissions(permissionList, 1)
            }else{
                val cursors = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)!!
                DB.getContact(cursors, contactList, recView, this)
                requestPermission.visibility = View.INVISIBLE
            }
        }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        errorTxt = findViewById(R.id.errorTxt)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                DB.getContact(cursor, contactList, recView, this)
                errorTxt.visibility = View.INVISIBLE
            }else{
                errorTxt.visibility = View.VISIBLE
                errorTxt.text = getString(R.string.permission_denied)

            }
        }
    }
}