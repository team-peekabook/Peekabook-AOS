package com.sopt.peekabookaos.data.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.sopt.peekabookaos.data.source.local.LocalPrefDataSource
import com.sopt.peekabookaos.data.source.local.LocalSignedUpDataSource
import com.sopt.peekabookaos.data.source.local.LocalTokenDataSource
import com.sopt.peekabookaos.data.source.remote.AuthDataSource
import com.sopt.peekabookaos.domain.entity.Token
import com.sopt.peekabookaos.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
    private val localPrefDataSource: LocalPrefDataSource,
    private val localSignedUpDataSource: LocalSignedUpDataSource
) : AuthRepository {
    override suspend fun postLogin(socialPlatform: String, fcmToken: String): Result<Token> =
        kotlin.runCatching { authDataSource.postLogin(socialPlatform, fcmToken) }.map { response ->
            requireNotNull(response.data).toToken()
        }

    override suspend fun deleteUser(): Result<Unit> =
        kotlin.runCatching { authDataSource.deleteUser() }

    override fun initToken(accessToken: String, refreshToken: String, fcmToken: String) {
        localTokenDataSource.accessToken = accessToken
        localTokenDataSource.refreshToken = refreshToken
        localTokenDataSource.fcmToken = fcmToken
    }

    override fun clearLocalPref() = localPrefDataSource.clearLocalPref()

    override fun getSignedUp(): Boolean = localSignedUpDataSource.isSignedUp
    override fun setSignedUp() {
        localSignedUpDataSource.isSignedUp = true
    }

    override fun getFcmToken(setFcmToken: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.e("Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                setFcmToken(task.result)
            }
        )
    }
}
