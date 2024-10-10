package com.party.presentation.screen.login

import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.party.common.R
import com.party.common.ServerApiResponse
import com.party.domain.model.user.SocialLoginErrorResponse
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
): ViewModel() {

    private val _nextScreen = MutableSharedFlow<SocialLoginErrorResponse>()
    val nextScreen = _nextScreen.asSharedFlow()

    private val _goToHomeScreen = MutableSharedFlow<Unit>()
    val goToHomeScreen = _goToHomeScreen.asSharedFlow()

    fun googleSignIn(activityResult: ActivityResult, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            task.addOnCompleteListener { completedTask ->
                try {
                    val account = completedTask.getResult(ApiException::class.java)
                    val idToken = account.idToken
                    if (idToken != null) {
                        //println("account : $idToken")
                        //println("account : ${account.email}")
                        //println("account : ${account.displayName}")
                        //println("account : ${account.isExpired}")

                        serverToGoogleLogin(
                            userEmail = account.email.orEmpty(),
                            accessToken = "${context.getString(R.string.common5)} $idToken",
                        )
                    } else {
                        Log.e("Google Login Error", "Google ID Token is null")
                    }
                } catch (e: ApiException) {
                    println("Google Login Exception : ${e.message} ${e.statusCode}")
                }
            }
        }
    }

    private fun serverToGoogleLogin(userEmail: String, accessToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = googleLoginUseCase(accessToken = accessToken)){
                is ServerApiResponse.SuccessResponse<*> -> {
                    println("result123 Success : ${result.data}")
                }
                is ServerApiResponse.ErrorResponse<*> -> {
                    when(result.statusCode){
                        StatusCode.Unauthorized.code -> { // 회원가입이 되어있지 않은 상태
                            val socialLoginErrorResponse: SocialLoginErrorResponse = result.data as SocialLoginErrorResponse
                            socialLoginErrorResponse.userEmail = userEmail
                            _nextScreen.emit(socialLoginErrorResponse)
                        }
                        StatusCode.InternalServerError.code -> {
                            println("result123 Error : ${result.statusCode}")
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

                is ServerApiResponse.SuccessResponse<*> -> {
                    //println("result123 Success : ${result.data}")
                    _goToHomeScreen.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse<*> -> {
                    when(result.statusCode){
                        StatusCode.Unauthorized.code -> { // 회원가입이 되어있지 않은 상태
                            val socialLoginErrorResponse: SocialLoginErrorResponse = result.data as SocialLoginErrorResponse
                            socialLoginErrorResponse.userEmail = userEmail
                            _nextScreen.emit(socialLoginErrorResponse)
                        }
                        StatusCode.InternalServerError.code -> {
                            println("result123 Error : ${result.statusCode}")
                        }
                    }
                }
                is ServerApiResponse.ExceptionResponse<*> -> {
                    println("result123 Exception : ${result.message}")
                }
            }
        }
    }
}