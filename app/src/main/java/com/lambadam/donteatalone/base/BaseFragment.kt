package com.lambadam.donteatalone.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment(){

    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View =
            inflater.inflate(layoutId, container, false)


    protected fun notify(root: View, messageId: Int){
        Snackbar.make(root, getString(messageId), Snackbar.LENGTH_SHORT).show()
    }
}
