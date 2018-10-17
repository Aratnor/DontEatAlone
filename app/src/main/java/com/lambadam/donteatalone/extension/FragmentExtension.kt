package com.lambadam.donteatalone.extension

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import com.lambadam.donteatalone.base.BaseFragment

inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory).get(T::class.java)
    vm.body()
    return vm
}

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

