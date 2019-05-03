package com.hviewtech.wawupay.common.bindings

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.DrawableRes
import android.text.TextUtils
import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.hviewtech.wawupay.common.Constants
import java.io.File

object ImageBindings {


  fun setImageUri(
    view: ImageView,
    uri: String? = null, @DrawableRes defaultRes: Any? = null,
    transformation: Transformation<Bitmap>? = null
  ) {
    var options: RequestOptions

    if (transformation == null) {
      options = RequestOptions.noTransformation()
    } else {
      options = RequestOptions.bitmapTransform(transformation)
    }

    if (TextUtils.isEmpty(uri)) {
      Glide.with(view.getContext())
        .load(defaultRes)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(options)
        .into(view)
    } else {
      if (defaultRes is Int) {
        if (defaultRes != 0) {
          options = options.placeholder(defaultRes).error(defaultRes)
        }
      } else if (defaultRes is Drawable) {
        options = options.placeholder(defaultRes).error(defaultRes)
      }

      val url: String

      if (URLUtil.isValidUrl(uri)) {
        url = uri!!
      } else {
        url = Constants.Prefix.IMG + uri
      }

      Glide.with(view.getContext())
        .load(Uri.parse(url))
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(options)
        .into(view)
    }
  }
  //
  //
  //  fun setImageUri(view: ImageView, uri: String?, @DrawableRes defaultRes: Drawable?, transformation: Transformation<Bitmap>?) {
  //    var options: RequestOptions
  //
  //    if (transformation == null) {
  //      options = RequestOptions.noTransformation()
  //    } else {
  //      options = RequestOptions.bitmapTransform(transformation)
  //    }
  //    if (defaultRes != null) {
  //      options = options.placeholder(defaultRes).error(defaultRes)
  //    }
  //    if (TextUtils.isEmpty(uri)) {
  //      Glide.with(view.getContext())
  //        .load(defaultRes)
  //        .transition(DrawableTransitionOptions.withCrossFade())
  //        .apply(options)
  //        .into(view)
  //    } else {
  //      Glide.with(view.getContext())
  //        .load(Uri.parse(uri))
  //        .transition(DrawableTransitionOptions.withCrossFade())
  //        .apply(options)
  //        .into(view)
  //    }
  //  }
  //
  //  fun setImageUri(view: ImageView, uri: String?, @DrawableRes defaultRes: Drawable?) {
  //    var options = RequestOptions.noTransformation()
  //    if (defaultRes != null) {
  //      options = options.placeholder(defaultRes)
  //        .error(defaultRes)
  //        //.priority(RenderScript.Priority.HIGH)
  //        //.skipMemoryCache(true)
  //        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
  //    }
  //    if (TextUtils.isEmpty(uri)) {
  //      Glide.with(view.getContext())
  //        .load(defaultRes)
  //        .transition(DrawableTransitionOptions.withCrossFade())
  //        .apply(options)
  //        .into(view)
  //    } else {
  //      Glide.with(view.getContext())
  //        .load(Uri.parse(uri))
  //        .transition(DrawableTransitionOptions.withCrossFade())
  //        .apply(options)
  //        .into(view)
  //    }
  //  }

  fun setAutoImageUri(view: ImageView, uri: String) {
    val options = RequestOptions.noTransformation()
      //                .priority(RenderScript.Priority.HIGH)
      //                .skipMemoryCache(true)
      .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    Glide.with(view.getContext())
      .load(Uri.parse(uri))
      .transition(DrawableTransitionOptions.withCrossFade())
      .apply(options)
      .into(view)
  }


  fun syncGetImage(context: Context, uri: String): File? {
    return Glide.with(context).asFile().load(uri).submit().get()
  }

  fun preloadImage(context: Context, uri: String) {
    Glide.with(context).asBitmap().load(uri).preload()
  }
}


