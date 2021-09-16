package com.example.week_six_task.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.week_six_task.DB
import com.example.week_six_task.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditContact : AppCompatActivity() {
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        //Finding the views in the activity_edit_contact layout and assigning them to variables
        val editName = findViewById<EditText>(R.id.editName)
        val editSurname = findViewById<EditText>(R.id.editSurname)
        val editPhone = findViewById<EditText>(R.id.editPone)
        val editMail = findViewById<EditText>(R.id.editMail)
        val editClear = findViewById<ImageButton>(R.id.clearEdit)
        val edit = findViewById<Button>(R.id.edit)

        //getting passed strings from the Profile Activity and assigning them to variables
        val id = intent.getStringExtra("id")
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val mobile = intent.getStringExtra("mobile")
        val email = intent.getStringExtra("email")

        //Setting the editable view's hints in this Activity to the gotten strings from Profile Activity
        editName.hint = firstName
        editSurname.hint = lastName
        editPhone.hint = mobile
        editMail.hint = email

        //Setting onclick listener to the editClear button to onBackPressed
        editClear.setOnClickListener {
            onBackPressed()
        }

        //Setting onclick listener to the edit button
        edit.setOnClickListener {
            //Validating when the text inputs are empty
            if (editName.text.isEmpty() || editSurname.text.isEmpty() || editPhone.text.isEmpty()){
                Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
            }else{
                if (id != null){
                    //Calling the editContact function from the DB object singleton to edit contacts to the firebase database
                    if (DB.editContact(id,
                            editName.text.first().uppercaseChar().toString(),
                            editName.text.toString(),
                            editSurname.text.toString(),
                            editPhone.text.toString(),
                            editMail.text.toString())){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, DB.successMsg, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, DB.errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }


    }

}