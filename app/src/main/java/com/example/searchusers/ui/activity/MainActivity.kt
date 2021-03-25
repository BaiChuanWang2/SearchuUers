package com.example.searchusers.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.example.searchusers.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(LayoutInflater.from(this)).apply {
            binding = this
            setContentView(binding.root)
        }
    }
}