package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.ProfileModifyRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PatchProfileModifyUseCase @Inject constructor(
    private val profileModifyRepository: ProfileModifyRepository
) {
    suspend operator fun invoke(
        file: MultipartBody.Part,
        requestBodyMap: HashMap<String, RequestBody>
    ) = profileModifyRepository.patchProfileModify(file, requestBodyMap)
}
