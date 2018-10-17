package com.lambadam.donteatalone.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val layoutId: Int
    protected abstract val fragmentContainerId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        addFragment(savedInstanceState)
    }

    private fun addFragment(savedInstanceState: Bundle?){
        savedInstanceState ?: supportFragmentManager
                .beginTransaction()
                .add(fragmentContainerId, fragment())
                .commit()
    }

    protected abstract fun fragment(): BaseFragment
}