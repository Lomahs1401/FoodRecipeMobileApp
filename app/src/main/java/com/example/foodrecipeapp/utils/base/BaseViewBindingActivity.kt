package com.example.foodrecipeapp.utils.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<T : ViewBinding> : AppCompatActivity() {

    private var mBinding: T? = null
    protected val binding: T
        get() = mBinding!!

    abstract fun createBindingActivity(): T
    abstract fun initView()
    abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = createBindingActivity()
        setContentView(binding.root)
        initData()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}
