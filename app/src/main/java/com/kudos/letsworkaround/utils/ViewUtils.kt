package com.kudos.letsworkaround.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

object ViewUtils {

    fun View.show(){
        visibility = View.VISIBLE
    }

    fun View.hide(){
        visibility = View.GONE
    }

    fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, msg, length).show()
    }

    fun Fragment.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
        requireContext().toast(msg, length)
    }

}