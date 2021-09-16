package com.example.week_six_task.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.week_six_task.DB
import com.example.week_six_task.R
import com.example.week_six_task.model.MainContact
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    var phoneNumber: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val contact = intent.getParcelableExtra<MainContact>("selected_contact")
        val name = findViewById<TextView>(R.id.bigName)
        val phone = findViewById<TextView>(R.id.numberTxt)
        val nameIndex = findViewById<TextView>(R.id.firstIndexText)
        val callBtn = findViewById<ImageButton>(R.id.callBtn)
        val shareBtn = findViewById<ImageButton>(R.id.shareBtn)
        val editBtn = findViewById<ImageButton>(R.id.editBtn)
        val deleteBtn = findViewById<ImageButton>(R.id.deleteButton)
        val backBtn = findViewById<ImageButton>(R.id.backBtn)

        shareBtn.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Contact \n Name: ${contact?.firstName} ${contact?.lastName} \n Phone: ${contact?.mobile}")
                type = "text/plain"
            }
            val share = Intent.createChooser(sendIntent, null)
            startActivity(share)
        }


        backBtn.setOnClickListener {
            onBackPressed()
        }

        name.text = "${contact?.firstName} ${contact?.lastName}"
        phone.text = "Phone: ${contact?.mobile}"
        nameIndex.text = contact?.firstLetter

        callBtn.setOnClickListener {
//            makePhoneCall(contact?.mobile)
            val list = arrayOf(android.Manifest.permission.CALL_PHONE)
            if (DB.makePhoneCall(contact?.mobile, this)){
                ActivityCompat.requestPermissions(this, list, 1)
            }else{
                val dial = "tel:${contact?.mobile}"
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }

        }

        editBtn.setOnClickListener {
            val intent = Intent(this, EditContact::class.java)
            intent.putExtra("id", contact?.id)
            intent.putExtra("firstName", contact?.firstName)
            intent.putExtra("lastName", contact?.lastName)
            intent.putExtra("mobile", contact?.mobile)
            intent.putExtra("email", contact?.email)
            startActivity(intent)
        }

        deleteBtn.setOnClickListener {
            val id = contact?.id
            if (id != null){
                if (DB.deleteContact(id, contact.firstName!!)){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "${contact.firstName} deleted successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Contact was not deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                DB.makePhoneCall(phoneNumber, this)
            }else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

//Html.fromHtml("<Div style=\"color:#bbdefb;\">" +
//"<h1>Contact:</h1>" +
//"<p>Name: ${contact?.firstName} ${contact?.lastName}</p>" +
//"<p>${contact?.mobile}</p>" +
//"</Div>")