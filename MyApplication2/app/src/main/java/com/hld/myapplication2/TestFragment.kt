package com.hld.myapplication2

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class TestFragment : Fragment(){
    private val testViewModel by viewModels<TestViewModel>()

}