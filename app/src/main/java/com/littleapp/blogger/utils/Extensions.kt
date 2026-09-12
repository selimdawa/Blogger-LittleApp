package com.littleapp.blogger.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import coil.load
import com.littleapp.blogger.R

fun Context.openActivity(cls: Class<*>, init: Intent.() -> Unit = {}) {
    val intent = Intent(this, cls)
    intent.init()
    startActivity(intent)
}

fun ImageView.loadImage(url: String?) {
    load(url) {
        placeholder(R.color.image_profile)
        error(R.color.image_profile)
    }
}