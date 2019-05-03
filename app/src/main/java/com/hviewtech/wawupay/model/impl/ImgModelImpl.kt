package com.hviewtech.wawupay.model.impl

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.common.utils.ResourceUtils
import com.hviewtech.wawupay.data.http.ImgService
import com.hviewtech.wawupay.model.ImgModel
import io.reactivex.Flowable
import java.io.File
import javax.inject.Inject

class ImgModelImpl @Inject constructor(private val mImgService: ImgService) : ImgModel {

  override fun putUploadImage(file: File): Flowable<BaseResult<String?>> {
    val part = ResourceUtils.filesToMultipartBodyPart("file", file)
    return mImgService.putUploadImage(part)
  }
}
