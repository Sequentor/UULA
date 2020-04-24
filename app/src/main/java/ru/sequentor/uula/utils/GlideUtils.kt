package ru.sequentor.uula.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import ru.sequentor.uula.application.App

inline fun <reified T> initGlide(
    imageView: ImageView,
    image: T?
) {
    image?.let {
        with(Glide.with(App.getContext())) {
            with(load(it)) {
                transition(DrawableTransitionOptions.withCrossFade())
                into(imageView)
            }
        }
    }
}

inline fun <reified T> initGlideCorners(
    imageView: ImageView,
    image: T?,
    corners: Int
) {
    image?.let {
        with(Glide.with(App.getContext())) {
            with(load(it)) {
                apply(RequestOptions().transform(CenterCrop(), RoundedCorners(corners)))
                transition(DrawableTransitionOptions.withCrossFade())
                into(imageView)
            }
        }
    }
}