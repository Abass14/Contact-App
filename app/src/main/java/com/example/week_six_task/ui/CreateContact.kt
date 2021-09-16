package com.example.week_six_task.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.week_six_task.DB
import com.example.week_six_task.R
import com.example.week_six_task.model.MainContact
import com.google.firebase.database.FirebaseDatabase

/**
 * Contacts are created in this Activity
 */

class CreateContact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)
        //Finding the views in the activity_create_contact layout and assigning them to variables
        val firstName = findViewById<EditText>(R.id.firstNameTxt)
        val secondName = findViewById<EditText>(R.id.surNameTxt)
        val phone = findViewById<EditText>(R.id.phoneTxt)
        val mail = findViewById<EditText>(R.id.mailTxt)
        val saveBtn = findViewById<Button>(R.id.save)
        val clearBtn = findViewById<ImageButton>(R.id.clear)

        //setting onclick listener to the clear button
        clearBtn.setOnClickListener {
            onBackPressed()
        }

        //setting on click listener to the save button
        saveBtn.setOnClickListener {
            //Validating when the text inputs are empty
            if (firstName.text.isEmpty() || secondName.text.isEmpty() || phone.text.isEmpty()){
                Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
            }else{
                //Creating an instance of MainContact
                val contact = MainContact(DB.databaseReference.push().key, firstName.text.first().uppercaseChar().toString(), firstName.text.toString(), secondName.text.toString(), phone.text.toString(), mail.text.toString())
                val intent = Intent(this, MainActivity::class.java)
                //Calling the add function from the DB object singleton to add contacts to the firebase database
                DB.add(contact).addOnSuccessListener {
                    Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
