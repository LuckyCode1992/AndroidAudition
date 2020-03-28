package com.example.androidaudition.demoactivity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidaudition.R
import kotlinx.android.synthetic.main.fragment_demo.*

class DemoFragment : Fragment() {
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("fragment_", "$tag-onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fragment_", "$tag-onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("fragment_", "$tag-onCreateView")
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("fragment_", "$tag-onViewCreated")
        ll.setBackgroundColor(
                if (tag == "A") {
                    Color.BLUE
                } else {
                    Color.GREEN
                }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("fragment_", "$tag-onActivityCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("fragment_", "$tag-onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d("fragment_", "$tag-onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("fragment_", "$tag-onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("fragment_", "$tag-onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("fragment_", "$tag-onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("fragment_", "$tag-onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("fragment_", "$tag-onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("fragment_", "$tag-onDetach")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d("fragment_", "$tag-onHiddenChanged-$hidden")
    }
}