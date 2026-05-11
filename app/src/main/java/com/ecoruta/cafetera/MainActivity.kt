package com.ecoruta.cafetera

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ecoruta.cafetera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Respetar barras del sistema
        binding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.setFlags(0, 0)

        setContentView(binding.root)
    }
}