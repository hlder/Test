package com.hld.mvvm.mvvmlibrary.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hld.mvvm.mvvmlibrary1.R
import com.hld.mvvm.mvvmlibrary1.databinding.FragmentLibrary1Binding
import com.hld.mvvm.widgets.EraserListener

class Library1Fragment : Fragment() {
    private lateinit var binding: FragmentLibrary1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibrary1Binding.inflate(inflater)
        binding.zoomView.setEraserListener(object : EraserListener {
            override fun onErasure() {
                changeButtonStatus()
            }
            override fun onUndo() {
                changeButtonStatus()
            }
            override fun onaAdvance() {
                changeButtonStatus()
            }
            override fun onClearPath() {
                changeButtonStatus()
            }
        })

        binding.zoomView.setBitmap(BitmapFactory.decodeResource(resources, R.mipmap.test2))
//        binding.zoomView.setBitmap(BitmapFactory.decodeResource(resources, R.mipmap.test))
//        binding.zoomView.setBitmap(BitmapFactory.decodeResource(resources, android.R.mipmap.sym_def_app_icon))

        binding.zoomView.maxZoom = 99999
        binding.button1.setOnClickListener {
            binding.zoomView.undo()
        }
        binding.button2.setOnClickListener {
            binding.zoomView.advance()
        }
        binding.button3.setOnClickListener {
            binding.zoomView.animResetZoom()
        }
        binding.button4.setOnClickListener {
            binding.zoomView.setBitmap(BitmapFactory.decodeResource(resources, R.mipmap.test))
        }

        changeButtonStatus()
        return binding.root
    }

    private fun changeButtonStatus(){
        binding.button1.isClickable = binding.zoomView.isCanUndo()
        binding.button2.isClickable = binding.zoomView.isCanAdvance()
    }

}