package com.example.week_six_task.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MainContact(val id: String? = "",
                  val firstLetter: String = "",
                  val firstName: String? = "",
                  val lastName: String? = "",
                  val mobile: String = "",
                  val email: String = "" ) : Parcelable {
}