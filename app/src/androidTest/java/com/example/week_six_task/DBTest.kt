package com.example.week_six_task

import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.week_six_task.model.MainContact
import com.example.week_six_task.ui.CreateContact
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.core.Context
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DBTest{
    lateinit var databaseReference: DatabaseReference
    lateinit var id: String
    lateinit var firstLetter: String
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var mobile: String
    lateinit var email: String
    lateinit var context: android.content.Context

    @Before
    fun setup(){
        context = ApplicationProvider.getApplicationContext<android.content.Context>()
//        context = this
//        var a = FirebaseApp.initializeApp(this)
        databaseReference = DB.databaseReference
        id = databaseReference.push().key!!
        firstName = "Abass"
        firstLetter = firstName.first().uppercaseChar().toString()
        lastName = "Adisa"
        mobile = "08094145784"
        email = "toyabilo3@gmail.com"
    }

//    @Test
//    fun to_test_when_a_new_contact_is_added_to_firebase (){
//        FirebaseApp.initializeApp(context)
//        val contact = MainContact(id, firstLetter, firstName, lastName, mobile, email)
//        val result = DB.add(contact)
//
//        assertEquals(databaseReference.child(contact.id!!).setValue(contact), result)
//    }

    @Test
    fun a(){
        val  result = DB.makePhoneCall(mobile, context)
        assertEquals(true, result)
    }

    @Test
    fun to_validate_edit_function_returns_true_when_edit_successful(){
        FirebaseApp.initializeApp(context)
        val result = DB.editContact(id, firstLetter, firstName, lastName, mobile, email)
        assertEquals(true, result)
    }
}