package com.example.tdandroidsaumiersophie.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tdandroidsaumiersophie.databinding.LoaderViewBinding

class Loader {

    lateinit var binding: LoaderViewBinding

    fun show(context: Context, caption: String) {
        val inflater = LayoutInflater.from(context)
        binding = LoaderViewBinding.inflate(inflater)
        binding.loaderCaption.text = caption

        val activity = context as? Activity
        val view = activity?.window?.peekDecorView() as? ViewGroup

        activity?.runOnUiThread {
            view?.addView(binding.root)
        }
    }

    fun hide(context: Context) {
        val activity = context as? Activity
        val view = activity?.window?.peekDecorView() as? ViewGroup
        activity?.runOnUiThread {
            view?.removeView(binding.root)
        }
    }
}