package com.example.daggerhiltexample.adapter

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.DataSource


class BindingAdapter {
    companion object {
        @BindingAdapter("android:buckunLoadUrl")
        @JvmStatic
        fun buckunLoadUrl(imageView: ImageView, imageUrl: String) {
            try {
                Glide.with(imageView.context).load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any,
                            target: Target<Drawable?>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }
                    }).into(imageView)
            } catch (ignored: Exception) {
            }
        }
    }
}