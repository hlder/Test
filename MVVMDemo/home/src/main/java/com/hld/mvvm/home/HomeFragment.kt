package com.hld.mvvm.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.hld.mvvm.common.ARouterController
import com.hld.mvvm.common.ARouterPathConfig
import com.hld.mvvm.common.ARouterProvider
import com.hld.mvvm.home.databinding.FragmentHomeBinding
import com.hld.mvvm.mvvmlibrary.api.MVVMLibrary1Controller
import com.hld.mvvm.mvvmlibrary.api2.MVVMLibrary2Controller
import com.hld.mvvm.mvvmlibrary.api2.MVVMLibrary2Values

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private var controller1: MVVMLibrary1Controller? = null
    private var controller2: MVVMLibrary2Controller? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        initListener()
        return binding.root
    }

    private fun initListener(){
        binding.button1.setOnClickListener {
            replaceFragment(controller1)
        }
        binding.button2.setOnClickListener {
            val bundle = Bundle().apply {
                putString(MVVMLibrary2Values.EXTRA_KEY_INIT_JSON, "\"name\":\"testName\"")
            }
            replaceFragment(controller2, bundle)
        }
        binding.textView1.setOnClickListener {
            controller2?.changeTestValue(requireActivity(),"12345678")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initARouterProvider()
        init()
    }

    private fun init() {
        replaceFragment(controller1)
    }

    private fun initARouterProvider() {
        controller1 = getController(ARouterPathConfig.PROVIDER_PATH_MVVM_LIBRARY1) as? MVVMLibrary1Controller
        controller2 = getController(ARouterPathConfig.PROVIDER_PATH_MVVM_LIBRARY2) as? MVVMLibrary2Controller
    }

    private fun replaceFragment(controller: ARouterController?, bundle: Bundle? = null) {
        controller?.getFragment(bundle)?.let {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.layoutFragment, it).commitAllowingStateLoss()
        }
    }

    private fun getController(path: String): ARouterController? {
        val obj = ARouter.getInstance().build(path).navigation()
        return (obj as? ARouterProvider)?.getController()
    }
}