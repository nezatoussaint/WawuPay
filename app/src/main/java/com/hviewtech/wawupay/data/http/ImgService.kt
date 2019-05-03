package com.hviewtech.wawupay.data.http

import com.hviewtech.wawupay.bean.remote.BaseResult
import io.reactivex.Flowable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ImgService {

    @Multipart
    @PUT(ApiConst.putUploadImage)
    fun putUploadImage(
        @Part part: MultipartBody.Part
    ): Flowable<BaseResult<String?>>

    companion object {
        val REL_URL = "http://167.99.89.30:8081"

        val DEV_URL = "http://167.99.89.30:8081"
    }

}
