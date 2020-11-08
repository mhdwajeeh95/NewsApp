package com.github.mhdwajeeh95.newsapp.ui.base

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected fun toast(message: String) {
        (activity as BaseActivity).toast(message)
    }

    protected fun toast(@StringRes messageId: Int) {
        (activity as BaseActivity).toast(messageId)
    }

}