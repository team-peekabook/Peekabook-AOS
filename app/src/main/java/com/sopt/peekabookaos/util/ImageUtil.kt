package com.sopt.peekabookaos.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL

object ImageUtil {
    suspend fun urlToBitmap(url: String): Bitmap? = withContext(Dispatchers.IO) {
        return@withContext try {
            val connection = URL(url).openConnection()
            connection.doInput = true
            connection.connect()
            val input = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    fun getOrientationOfImage(uri: Uri, bitmap: Bitmap, cr: ContentResolver): Bitmap {
        val inputStream = cr.openInputStream(uri)
        val matrix = Matrix()
        val exif: ExifInterface? = try {
            ExifInterface(inputStream!!)
        } catch (e: IOException) {
            e.printStackTrace()
            return bitmap
        }
        inputStream.close()

        val orientation =
            exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        if (orientation != -1) {
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90F)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180F)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270F)
            }
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
