package com.example.week_six_task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week_six_task.R
import com.example.week_six_task.model.MainContact

class MainRecyclerAdapter(val mainContactList: MutableList<MainContact>, var onContactClickListener: ContactClickListener) : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {
        var theContactClickListener = this.onContactClickListener
    inner class ViewHolder(view: View, viewHolderContact: ContactClickListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        val names = view.findViewById<TextView>(R.id.contactName_main)
        val mobile = view.findViewById<TextView>(R.id.mobile_main)
        val nameIcon = view.findViewById<TextView>(R.id.contactIcon_main)
        var vhContactClickListener = viewHolderContact
        val mainList = view.findViewById<View>(R.id.main_list)

        fun bindView(mainContactList: MainContact){
            names.text = "${mainContactList.firstName} ${mainContactList.lastName}"
            mobile.text = mainContactList.mobile
//
            mainList.setOnClickListener {
                vhContactClickListener.onContactClick(bindingAdapterPosition,it)
            }
            nameIcon.text = mainContactList.firstLetter
        }

        override fun onClick(v: View?) {
            vhContactClickListener.onContactClick(bindingAdapterPosition, v!!)
        }
    }

    interface ContactClickListener {
        fun onContactClick(position: Int, view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_contact_list, parent, false)
        return ViewHolder(view, theContactClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mainContactList[position])
    }

    override fun getItemCount(): Int {
        return mainContactList.size
    }
}