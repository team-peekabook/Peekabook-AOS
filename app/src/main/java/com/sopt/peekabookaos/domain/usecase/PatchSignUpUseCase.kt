package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.UserInputRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PatchSignUpUseCase @Inject constructor(
    private val userInputRepository: UserInputRepository
) {
    suspend operator fun invoke(
        file: MultipartBody.Part?,
        requestBodyMap: HashMap<String, RequestBody>
    ) = userInputRepository.patchSignUp(file, requestBodyMap)
}
