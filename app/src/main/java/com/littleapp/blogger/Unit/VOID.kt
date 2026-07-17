package com.littleapp.blogger.unit

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import coil.load
import com.littleapp.blogger.R

object VOID {
    fun startActivity(context: Context, c: Class<*>?) {
        val intent = Intent(context, c)
        context.startActivity(intent)
    }

    fun startActivityWithExtra(context: Context, c: Class<*>?, key: String?, value: String?) {
        val intent = Intent(context, c)
        intent.putExtra(key, value)
        context.startActivity(intent)
    }

    fun loadImage(context: Context?, url: String?, imageView: ImageView) {
        try {
            if (context != null) {
                imageView.load(url) {
                    placeholder(R.color.image_profile)
                    error(R.color.image_profile)
                    crossfade(true)
                }
            }
        } catch (_: Exception) {
            imageView.setImageResource(R.color.image_profile)
        }
    }
}