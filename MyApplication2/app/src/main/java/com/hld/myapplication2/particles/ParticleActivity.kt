package com.hld.myapplication2.particles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hld.myapplication2.databinding.ActivityParticleBinding

class ParticleActivity : AppCompatActivity() {

    private lateinit var binding:ActivityParticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            println("============click")
            binding.particleAnimationView.startAnim()
        }
        binding.button2.setOnClickListener {
            binding.particleAnimationView.stopAnim()
        }
    }

}