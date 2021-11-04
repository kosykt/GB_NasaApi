package ru.kostry.nasaapi.ui.podfragment.view

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import ru.kostry.nasaapi.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("setTitlePod")
fun bindTitle(textView: TextView, title: String?) {
    textView.text = title
}

@BindingAdapter("setTextPod")
fun bindText(textView: TextView, text: String?) {
    textView.text = text
}