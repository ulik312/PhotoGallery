package com.sbor.photogallery
import android.widget.ImageView
import com.sbor.photogallery.model.Gallery
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String){
    Glide.with(this.context).load(url).into(this)
}
