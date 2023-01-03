package com.newsheadlines.app.splash.viewmodel

import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.lifecycle.ViewModel

/*
* ViewModel for Splash Screen
* */
class SplashViewModel : ViewModel() {

    fun canUseFingerPrint(context: Context): Boolean {
        // and lets check if our user can use biometric sensor or not
        val biometricManager: BiometricManager =
            BiometricManager.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            when (biometricManager.canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    return true
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE or BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE
                or BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED-> {
                    return false
                }
            }
        }
        return false
    }
}