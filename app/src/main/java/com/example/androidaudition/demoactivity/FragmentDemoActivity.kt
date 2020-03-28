package com.example.androidaudition.demoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.androidaudition.R
import kotlinx.android.synthetic.main.activity_fragment_demo.*

class FragmentDemoActivity : AppCompatActivity() {

    var currentFragment: DemoFragment? = null
    var tagFragment: DemoFragment? = null
    val aFragment by lazy {
        DemoFragment()
    }
    val bFragment by lazy {
        DemoFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_demo)
        currentFragment = aFragment

        supportFragmentManager.beginTransaction()
                .add(R.id.fl, aFragment, "A")
                .commitAllowingStateLoss()
        btn_replace.setOnClickListener {
            var tag = "A"
            tagFragment = if (currentFragment == aFragment) {
                tag = "B"
                bFragment
            } else {
                tag = "A"
                aFragment
            }
            supportFragmentManager.beginTransaction().replace(R.id.fl, tagFragment!!,tag ).commitAllowingStateLoss()
            currentFragment = tagFragment
        }
        btn_show_hide.setOnClickListener {
            var tag: String = "A"
            tagFragment = if (currentFragment == aFragment) {
                tag = "B"
                bFragment
            } else {
                tag = "A"
                aFragment
            }
            val transaction = supportFragmentManager.beginTransaction()
            if (tagFragment?.isAdded == false) {
                transaction.add(tagFragment!!, tag).commitAllowingStateLoss()
            } else {
                transaction.hide(currentFragment!!).show(tagFragment!!).commitAllowingStateLoss()
            }
            currentFragment = tagFragment
        }

    }
}
