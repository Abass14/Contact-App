package com.example.week_six_task

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week_six_task.adapter.MainRecyclerAdapter
import com.example.week_six_task.adapter.RecyclerViewAdapter
import com.example.week_six_task.model.Contact
import com.example.week_six_task.model.MainContact
import com.example.week_six_task.ui.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

object DB  {
    lateinit var errorMsg: String
    var successMsg: String = ""
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Contact")

    fun add(contact: MainContact) : Task<Void> {
        return databaseReference.child(contact.id!!).setValue(contact)
    }

    fun editContact(id: String, nameIcon:String, firstName: String, surname:String, phone:String, mail:String): Boolean {
        var result: Boolean = true
        val contact = mapOf<String, String>(
            "id" to id,
            "firstLetter" to nameIcon,
            "firstName" to firstName,
            "lastName" to surname,
            "mobile" to phone,
            "email" to mail
        )
        databaseReference.child(id).updateChildren(contact).addOnSuccessListener {
            result = true
            successMsg = "Successfully edited"
        }.addOnFailureListener {
            result = false
            errorMsg = "Failed!!"
        }

        return result
    }

    fun deleteContact(id:String, contactName: String) : Boolean {
        var result: Boolean = true
        val database = FirebaseDatabase.getInstance().getReference("Contact")
        database.child(id).removeValue().addOnSuccessListener {

            result = true
        }.addOnFailureListener {
            result = false
        }
        return result
    }

    fun retrieveContact(onClickListener: MainRecyclerAdapter.ContactClickListener, mainContactList: MutableList<MainContact>, mainRecView: RecyclerView) : Boolean {
        var result = true
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    //mainContactList.clear()
                    for (userSnapshot in snapshot.children){
                        val contact = userSnapshot.getValue(MainContact::class.java)
                        mainContactList.add(contact!!)
                    }
                    mainRecView.adapter = MainRecyclerAdapter(mainContactList, onClickListener)
                    result = true
                }else{
                    result = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return result
    }

    fun makePhoneCall(phone: String?, context: Context) : Boolean{
        var result = true

        result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
        return result
    }

    fun getContact(cursor: Cursor?, contactList: MutableList<Contact>, recView: RecyclerView, context: Context) {
        if (cursor != null) {
            while (cursor.moveToNext()){
                var name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                var mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactList.add(Contact(name, " ", name.first().uppercaseChar().toString(), mobile))
            }
            cursor.close()
        }
        recView.adapter = RecyclerViewAdapter(contactList)
        recView.layoutManager = LinearLayoutManager(context)
        recView.setHasFixedSize(true)
    }


}