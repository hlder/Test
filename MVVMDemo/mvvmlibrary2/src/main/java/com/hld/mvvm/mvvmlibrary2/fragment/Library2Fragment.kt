package com.hld.mvvm.mvvmlibrary2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hld.mvvm.mvvmlibrary.api2.MVVMLibrary2Values
import com.hld.mvvm.mvvmlibrary2.databinding.FragmentLibrary2Binding

class Library2Fragment : Fragment() {
    private lateinit var binding: FragmentLibrary2Binding

    private val viewModel by viewModels<Library2FragmentViewModel>()
    private lateinit var viewModelAct:Library2ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelAct = requireActivity().viewModels<Library2ActivityViewModel>().value
        binding = FragmentLibrary2Binding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initData()
        initObserve()
        initListener()
        return binding.root
    }

    private fun initObserve(){
        viewModelAct.testValueAct.value = null
        viewModelAct.testValueAct.observe(viewLifecycleOwner){ value->
            // 正常情况，可以在这里做些处理，比如http请求啊啥的。
            value?.let {
                viewModel.testValue.value = it
            }
        }
    }

    private fun initListener(){
        binding.button.setOnClickListener {
            viewModel.testValue.value = "tttttttt"
        }
    }

    private fun initData(){
        val initStr = arguments?.getString(MVVMLibrary2Values.EXTRA_KEY_INIT_JSON)
        viewModel.testValue.value = initStr
    }
}