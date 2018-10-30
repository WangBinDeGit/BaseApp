package com.wangbin.mydome.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.wangbin.mydome.R

/**
 * Created by wangbin
 * on 2018/7/16.
 */

class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        bundle?.getString("name")

    }

    companion object {

        fun newInstance(name: String): NewsFragment {
            val args = Bundle()
            args.putString("name", name)
            val fragment = NewsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}