package ru.kostry.nasaapi.ui.podfragment.view

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import ru.kostry.nasaapi.R
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PODApiStatus

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

@BindingAdapter("apiStatusImage")
fun bindStatus(imgView: ImageView, status: PODApiStatus?) {
    when (status) {
        PODApiStatus.LOADING -> {
            imgView.setImageResource(R.drawable.loading_animation)
        }
        PODApiStatus.ERROR -> {
            imgView.setImageResource(R.drawable.ic_broken_image)
        }
    }
}