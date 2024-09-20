package com.party.presentation.screen.login

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.party.common.AESUtil
import com.party.domain.model.member.SocialLoginRequest
import com.party.domain.usecase.user.GoogleLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleLoginUseCase: GoogleLoginUseCase,
): ViewModel() {

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

                        serveToLogin(
                            socialLoginRequest = SocialLoginRequest(
                                uid = AESUtil.encrypt(account.email!!),
                                idToken = idToken,
                            )
                        )

                    } else {
                        //LogUtils.e("Google ID Token is null")
                        println("asdasdas")
                    }
                } catch (e: ApiException) {
                    println("errro : " + e.message)
                    println("errro : " + e.statusCode)
                }
            }
        }
    }

    fun serveToLogin(socialLoginRequest: SocialLoginRequest){
        viewModelScope.launch(Dispatchers.IO) {
            val result = googleLoginUseCase(socialLoginRequest = socialLoginRequest)

            println("result : $result")

        }
    }
}