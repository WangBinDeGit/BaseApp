package com.wangbin.mydome.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wangbin.mydome.R

/**
 * Created by jxd.
 * on 2018/11/2
 */
class IndexFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_index, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tv = view.findViewById<TextView>(R.id.fragment_test_tv)

        val bundle = arguments
        if (bundle != null) {
            val name = bundle.getString("name")
            tv.text = name
        }
    }

    companion object {

        fun newInstance(name: String): TestFragment {
            val args = Bundle()
            args.putString("name", name)
            val fragment = TestFragment()
            fragment.arguments = args
            return fragment
        }
    }

}