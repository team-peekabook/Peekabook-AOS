package com.sopt.peekabookaos.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.IOException
import okio.source
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ContentUriRequestBody(context: Context, private val name: String, private val uri: Uri) :
    RequestBody() {
    private val contentResolver = context.contentResolver
    private var fileName = ""
    private var size = -1L
    private var inputStream = try {
        contentResolver.openInputStream(uri)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    private var bitmap = BitmapFactory.decodeStream(inputStream as InputStream?)
    private val byteArrayOutputStream = ByteArrayOutputStream()
    private lateinit var requestBody: RequestBody
    private lateinit var uploadFile: MultipartBody.Part

    init {
        contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
                fileName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
            }
        }
    }

    private fun getFileName() = fileName

    override fun contentLength(): Long = size

    override fun contentType(): MediaType? =
        contentResolver.getType(uri)?.toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        contentResolver.openInputStream(uri)?.source()?.use { source ->
            sink.writeAll(source)
        }
    }

    fun compressBitmap(): MultipartBody.Part {
        bitmap = ImageUtil.getOrientationOfImage(uri, bitmap, contentResolver)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
        requestBody = create("image/jpg".toMediaTypeOrNull(), byteArrayOutputStream.toByteArray())
        uploadFile = MultipartBody.Part.createFormData(name, getFileName(), requestBody)
        return uploadFile
    }
}
