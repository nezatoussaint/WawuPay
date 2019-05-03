package com.hviewtech.wawupay.model

import com.hviewtech.wawupay.bean.remote.BaseResult
import io.reactivex.Flowable

import java.io.File

interface ImgModel {

  fun putUploadImage(
    file: File
  ): Flowable<BaseResult<String?>>
}
