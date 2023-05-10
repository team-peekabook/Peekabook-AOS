package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.MyPageRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PatchProfileModifyUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(
        file: MultipartBody.Part?,
        requestBodyMap: HashMap<String, RequestBody>
    ) = myPageRepository.patchProfileModify(file, requestBodyMap)
}
