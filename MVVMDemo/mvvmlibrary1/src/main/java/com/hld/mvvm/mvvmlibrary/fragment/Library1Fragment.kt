package com.hld.mvvm.mvvmlibrary.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hld.mvvm.mvvmlibrary1.R
import com.hld.mvvm.mvvmlibrary1.databinding.FragmentLibrary1Binding

class Library1Fragment : Fragment() {
    private lateinit var binding: FragmentLibrary1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibrary1Binding.inflate(inflater)
//        binding.zoomView.setBitmap(BitmapFactory.decodeResource(resources, R.mipmap.test))
        binding.zoomView.setBitmap(BitmapFactory.decodeResource(resources, R.mipmap.test2))
//        binding.zoomView.setBitmap(BitmapFactory.decodeResource(resources, android.R.mipmap.sym_def_app_icon))

        binding.zoomView.maxZoom = 3
        binding.button1.setOnClickListener{
            binding.zoomView.undo()
        }
        binding.button2.setOnClickListener{
            binding.zoomView.advance()
        }
        binding.button3.setOnClickListener{
            binding.zoomView.animResetZoom()
        }
        return binding.root
    }

}