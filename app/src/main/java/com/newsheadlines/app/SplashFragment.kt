package com.newsheadlines.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.newsheadlines.app.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SplashFragment : Fragment(R.layout.fragment_splash){

    lateinit var binding : FragmentSplashBinding

    lateinit var timerTask : TimerTask

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater,container,false)


        val versionName: String = BuildConfig.VERSION_NAME

        binding.textVersion.text = String.format("v%s",versionName)

        val timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                lifecycleScope.launch(Dispatchers.Main) {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToNewsListFragment())
                }
            }
        }



        timer.schedule(timerTask,2000)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()

        timerTask.cancel()
    }

}