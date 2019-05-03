package com.hviewtech.wawupay.data.http.progress

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
interface UploadProgressListener {
  /**
   * 上传进度
   * @param currentBytesCount
   * @param totalBytesCount
   */
  fun onProgress(currentBytesCount: Long, totalBytesCount: Long)
}