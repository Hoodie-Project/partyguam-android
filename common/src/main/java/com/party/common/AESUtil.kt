package com.party.common

import android.util.Base64
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtil {

    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private const val CHARSET = "UTF-8"
    private const val AES = "AES"
    private const val IV_SIZE = 16  // AES 블록 크기(16 바이트)

    // AES-256 키 (32바이트 길이)
    private val key = "paikdmkwur1wkmd6akem123456789012".toByteArray(StandardCharsets.UTF_8)

    // IV (16바이트 길이)
    //private val iv = "avsdmkmd20ekm0l6".toByteArray(Charset.forName(CHARSET))
    private val iv = "avsdmkmd20ekm0l6".toByteArray(StandardCharsets.UTF_8)

    // 암호화
    /*fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKey = SecretKeySpec(key, AES)
        val ivSpec = IvParameterSpec(iv)

        // 암호화 설정
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

        // 데이터 암호화
        val encryptedData = cipher.doFinal(data.toByteArray(Charset.forName(CHARSET)))

        // Base64 인코딩하여 반환
        return Base64.encodeToString(encryptedData, Base64.DEFAULT)
    }*/

    fun encrypt(data: String): String {
        return try {
            val keyData = "paikdmkwur1wkmd6akem123456789012".toByteArray(StandardCharsets.UTF_8)
            val ivData = "paikdmkwur1wkmd6akem123456789012".substring(0, 16).toByteArray(StandardCharsets.UTF_8)
            val secretKey: SecretKey = SecretKeySpec(keyData, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(ivData))
            val encrypted = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))
            java.util.Base64.getEncoder().encodeToString(encrypted)
        } catch (e: Exception) {
            //logger.error { e.toString() }
            data
        }
    }
}