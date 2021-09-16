package com.example.week_six_task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week_six_task.R
import com.example.week_six_task.model.Contact

class RecyclerViewAdapter(var contactList: MutableList<Contact>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val contactName = itemView.findViewById<TextView>(R.id.contactName)
        val contactNameIcon = itemView.findViewById<TextView>(R.id.contactIcon)
        val contactPhone = itemView.findViewById<TextView>(R.id.mobile)

        fun bindView(contactList: Contact){
            contactName.text = "${contactList.firstName} ${contactList.surname}"
            contactNameIcon.text = contactList.contactIcon
            contactPhone.text = contactList.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(contactList[position])
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}