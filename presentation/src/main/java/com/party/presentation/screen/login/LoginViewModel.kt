package com.party.presentation.screen.login

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.party.common.BaseErrorResponse
import com.party.common.BaseExceptionResponse
import com.party.common.BaseSuccessResponse
import com.party.domain.model.member.SocialLoginErrorResponse
import com.party.domain.usecase.user.GoogleLoginUseCase
import com.party.domain.usecase.user.KakaoLoginUseCase
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

    fun loginGoogle(activityResult: ActivityResult){
        viewModelScope.launch(Dispatchers.IO) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            task.addOnCompleteListener { completedTask ->
                try {
                    val account = completedTask.getResult(ApiException::class.java)
                    val idToken = account.idToken
                    if (idToken != null) {
                        println("idToken : $idToken")
                        println("account : ${account.email}")
                        println("account : ${account.displayName}")
                        println("account : ${account.isExpired}")

                        /*serveToLogin(
                            socialLoginRequest = SocialLoginRequest(
                                uid = AESUtil.encryptCBC(account.email!!),
                                idToken = idToken,
                            )
                        )*/

                    } else {
                        //LogUtils.e("Google ID Token is null")
                        println("asdasdas")
                    }
                } catch (e: ApiException) {
                    println("error : ${e.message} ${e.statusCode}")
                }
            }
        }
    }

    fun serveToKakaoLogin(userEmail: String, accessToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = kakaoLoginUseCase(accessToken = accessToken)){
                is BaseSuccessResponse<*> -> {
                    println("result123 Success : ${result.data}")
                }
                is BaseErrorResponse<*> -> {
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
                is BaseExceptionResponse -> {
                    println("result123 Exception : ${result.message}")
                }
            }
        }
    }
}