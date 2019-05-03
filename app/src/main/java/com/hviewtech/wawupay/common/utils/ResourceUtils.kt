package com.hviewtech.wawupay.common.utils

import android.net.Uri
import android.text.TextUtils
import com.hviewtech.wawupay.data.http.progress.ProgressRequestBody
import com.hviewtech.wawupay.data.http.progress.UploadProgressListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

/**
 * @author 诸葛不亮
 * @version 1.0
 * @description 资源处理工具类
 */

object ResourceUtils {

  /**
   * 加载本地图片（assets图片）
   *
   * @param nameWithSuffix 带后缀的名称
   */
  fun parseAssets2Uri(nameWithSuffix: String): String {
    return "asset:///$nameWithSuffix"
  }

  fun parseFile2Uri(path: String): String {
    return "file:///$path"
  }

  fun filesToMultipartBody(name: String, vararg files: File): MultipartBody {
    val builder = MultipartBody.Builder()
    for (file in files) {
      // 这里为了简单起见，没有判断file的类型
      val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
      builder.addFormDataPart(name, file.name, requestBody)
    }
    builder.setType(MultipartBody.FORM)
    return builder.build()
  }

  fun filesToMultipartBodyParts(name: String, vararg files: File): List<MultipartBody.Part?>? {
    val parts = ArrayList<MultipartBody.Part>(files.size)
    for (file in files) {
      // 这里为了简单起见，没有判断file的类型
      val part = filesToMultipartBodyPart(name, file)
      parts.add(part)
    }
    return parts
  }

  fun filesToMultipartBodyPart(name: String, file: File): MultipartBody.Part {
    // 这里为了简单起见，没有判断file的类型
    val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
    return MultipartBody.Part.createFormData(name, file.name, requestBody)
  }

  fun filesToMultipartBodyPart(name: String, file: File, progressListener: UploadProgressListener): MultipartBody.Part {
    // 这里为了简单起见，没有判断file的类型
    val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
    return MultipartBody.Part.createFormData(name, file.name, ProgressRequestBody(requestBody, progressListener))
  }


  fun uri2File(uriString: String?): File? {
    uriString ?: return null
    val uri = Uri.parse(uriString)
    return File(uri.path!!)
  }

  fun getFilenameForUrl(url: String): String {
    var filename = "temp"
    if (TextUtils.isEmpty(url)) {
      return filename
    }
    val index = url.lastIndexOf("/")
    if (index > 0) {
      filename = url.substring(index, url.length)
    }
    return filename
  }

}
