package com.party.presentation.screen.login.viewmodel

import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.party.common.ServerApiResponse
import com.party.domain.model.user.AccessTokenRequest
import com.party.domain.model.user.RecoverInfo
import com.party.domain.model.user.SocialLogin
import com.party.domain.model.user.SocialLoginError
import com.party.domain.model.user.SocialLoginSuccess
import com.party.domain.usecase.datastore.SaveAccessTokenUseCase
import com.party.domain.usecase.user.auth.GoogleLoginUseCase
import com.party.domain.usecase.user.auth.KakaoLoginUseCase
import com.skydoves.sandwich.StatusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
): ViewModel() {

    private val _nextScreen = MutableSharedFlow<SocialLoginError>()
    val nextScreen = _nextScreen.asSharedFlow()

    private val _goToHomeScreen = MutableSharedFlow<Unit>()
    val goToHomeScreen = _goToHomeScreen.asSharedFlow()

    private val _goToRecoverScreen = MutableSharedFlow<RecoverInfo>()
    val goToRecoverScreen = _goToRecoverScreen.asSharedFlow()

    fun googleSignIn(activityResult: ActivityResult, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            task.addOnCompleteListener { completedTask ->
                try {
                    val account = completedTask.getResult(ApiException::class.java)
                    val idToken = account.idToken
                    if (idToken != null) {

                        serverToGoogleLogin(
                            userEmail = account.email.orEmpty(),
                            accessToken = idToken
                        )
                    } else {
                        Log.e("Google Login Error", "Google ID Token is null")
                    }
                } catch (e: ApiException) {
                    Log.e("Google Login Exception", "${e.message} ${e.statusCode}")
                }
            }
        }
    }

    private fun serverToGoogleLogin(userEmail: String, accessToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = googleLoginUseCase(accessTokenRequest = AccessTokenRequest(idToken = accessToken))){
                is ServerApiResponse.SuccessResponse<*> -> {
                    val socialLoginResponse = result.data as SocialLoginSuccess
                    saveAccessToken(token = socialLoginResponse.accessToken)
                    _goToHomeScreen.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse<*> -> {
                    when(result.statusCode){
                        StatusCode.Unauthorized.code -> { // 회원가입이 되어있지 않은 상태
                            val socialLoginErrorResponse: SocialLoginError = result.data as SocialLoginError
                            socialLoginErrorResponse.userEmail = userEmail
                            _nextScreen.emit(socialLoginErrorResponse)
                        }
                        StatusCode.InternalServerError.code -> {
                            println("result123 Error : ${result.statusCode}")
                        }
                        StatusCode.Forbidden.code -> {
                            val email = result.email ?: ""
                            val deletedAt = result.deletedAt ?: ""
                            val recoverAccessToken = result.recoverAccessToken ?: ""
                            _goToRecoverScreen.emit(RecoverInfo(email, deletedAt, recoverAccessToken))
                        }
                    }
                }
                is ServerApiResponse.ExceptionResponse -> {
                    println("result123 Exception : ${result.message}")
                }
            }
        }
    }

    fun serveToKakaoLogin(userEmail: String, accessToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = kakaoLoginUseCase(accessToken = accessToken)){
                is ServerApiResponse.SuccessResponse<SocialLogin> -> {
                    //println("result123 Success : ${result.data}")
                    val socialLoginResponse = result.data as SocialLoginSuccess
                    saveAccessToken(token = socialLoginResponse.accessToken)
                    _goToHomeScreen.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse<*> -> {
                    when(result.statusCode){
                        StatusCode.Unauthorized.code -> { // 회원가입이 되어있지 않은 상태
                            val socialLoginErrorResponse: SocialLoginError = result.data as SocialLoginError
                            socialLoginErrorResponse.userEmail = userEmail
                            _nextScreen.emit(socialLoginErrorResponse)
                        }
                        StatusCode.InternalServerError.code -> {
                            println("result123 Error : ${result.statusCode}")
                        }
                        StatusCode.Forbidden.code -> {
                            val email = result.email ?: ""
                            val deletedAt = result.deletedAt ?: ""
                            val recoverAccessToken = result.recoverAccessToken ?: ""
                            _goToRecoverScreen.emit(RecoverInfo(email, deletedAt, recoverAccessToken))
                        }
                    }
                }
                is ServerApiResponse.ExceptionResponse<*> -> {
                    println("result123 Exception : ${result.message}")
                }
            }
        }
    }

    private fun saveAccessToken(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            saveAccessTokenUseCase(token = token)
        }
    }
}