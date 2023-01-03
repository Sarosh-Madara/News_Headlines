package com.newsheadlines.app.splash.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.newsheadlines.app.BuildConfig
import com.newsheadlines.app.R
import com.newsheadlines.app.databinding.FragmentSplashBinding
import com.newsheadlines.app.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Executor

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    lateinit var binding: FragmentSplashBinding

    lateinit var timerTask: TimerTask


    //
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)


        val versionName: String = BuildConfig.VERSION_NAME

        binding.textVersion.text = String.format("v%s", versionName)

        if (viewModel.canUseFingerPrint(requireContext())) {

            verifyFingerprint()

        } else {

            proceedManually()
        }


        return binding.root
    }

    private fun verifyFingerprint() {
        // verify fingerprint via sheet

        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        requireContext(),
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        requireContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                    proceedManually()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        requireContext(), "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun proceedManually() {
        // make navigation to news listing screen
        val timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                lifecycleScope.launch(Dispatchers.Main) {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToNewsListFragment())
                }

            }
        }
        timer.schedule(timerTask, 1500)
    }


    override fun onDestroy() {
        super.onDestroy()

        if (::timerTask.isInitialized)
            timerTask.cancel()
    }

}