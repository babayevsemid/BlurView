package com.example.myapplication

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.semid.blurView.BlurView
import com.semid.blurView.RenderScriptBlur


class MainActivity : AppCompatActivity() {
    private val binding by lazy (LazyThreadSafetyMode.NONE){
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.blurView.setupWith(binding.root)
            .setBlurAlgorithm(RenderScriptBlur(applicationContext))
            ?.setBlurRadius(1f)
            ?.setBlurAutoUpdate(true)
            ?.setHasFixedTransformationMatrix(true)

        binding.blurView.setOnClickListener {
            binding.blurView.setBlurEnabled(!binding.blurView.isBlurEnabled)
        }
    }
}