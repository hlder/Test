package com.hld.myapplication2.testnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.hld.myapplication2.R
import com.hld.myapplication2.databinding.FragmentTest1Binding

class TestFragment1 : Fragment(){

    private lateinit var fragmentTest1Binding: FragmentTest1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTest1Binding = FragmentTest1Binding.inflate(layoutInflater)
        fragmentTest1Binding.button1.setOnClickListener {
            val graph = findNavController().navInflater.inflate(R.navigation.nav_test2)
            findNavController().graph.addAll(graph)
        }
        fragmentTest1Binding.button2.setOnClickListener {
            findNavController().navigate(R.id.testFragment2)
        }
        fragmentTest1Binding.button3.setOnClickListener {
            findNavController().navigate(R.id.testFragment3)
        }
        return fragmentTest1Binding.root
    }
}