package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.ProfileEntity
import com.sopt.peekabookaos.data.entity.response.ProfileModifyResponse
import com.sopt.peekabookaos.data.service.MyPageService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

data class MyPageDataSource @Inject constructor(
    private val myPageService: MyPageService
) {
    suspend fun getMyPage(): BaseResponse<ProfileEntity> =
        myPageService.getMyPage()

    suspend fun patchProfileModify(
        file: MultipartBody.Part?,
        requestBodyMap: HashMap<String, RequestBody>
    ): BaseResponse<ProfileModifyResponse> = myPageService.patchProfileModify(
        file,
        requestBodyMap
    )
}
