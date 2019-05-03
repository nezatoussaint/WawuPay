package com.hviewtech.wawupay.data.http.progress

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

/**
 * @author su
 * @date 2018/11/23
 * @description
 */
class ProgressRequestBody(private var body: RequestBody, private val listener: UploadProgressListener) : RequestBody() {

  private lateinit var conutingSink: CountingSink

  override fun contentType(): MediaType? {
    return body.contentType()
  }

  override fun writeTo(sink: BufferedSink) {
    conutingSink = CountingSink(sink)
    val buffer = Okio.buffer(conutingSink)
    body.writeTo(buffer)
    buffer.flush()
  }

  /**
   * 返回文件总的字节大小
   * 如果文件大小获取失败则返回-1
   *
   * @return
   */
  override fun contentLength(): Long {
    try {
      return body.contentLength()
    } catch (e: IOException) {
      return -1
    }
  }

  protected class CountingSink(delegate: Sink) : ForwardingSink(delegate) {

    private var byteWritten = 0L
    /**
     * 上传时调用该方法,在其中调用回调函数将上传进度暴露出去,该方法提供了缓冲区的自己大小
     *
     * @param source
     * @param byteCount
     * @throws IOException
     */
    override fun write(source: Buffer, byteCount: Long) {
      super.write(source, byteCount)
      byteWritten += byteCount
    }


  }

}