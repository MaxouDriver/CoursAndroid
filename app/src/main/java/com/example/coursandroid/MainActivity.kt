package com.example.coursandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import com.example.coursandroid.game.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), GameFragment.OnListFragmentInteractionListener, GameDetailsFragment.OnFragmentInteractionListener{
    private var cpt: Int = 0
    private val PREF_NAME: String = "cpt"

    override fun onFragmentInteraction(link: String) {
        cpt++

        if (cpt % 2 == 0) {
            val intent = Intent(this@MainActivity,GameWebDetailsActivity::class.java)
            intent.putExtra("link",link)
            startActivity(intent)
        }else{
            val uris = Uri.parse(link)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            startActivity(intents)
        }
    }

    override fun onListFragmentInteraction(item: GameContent.GameItem?) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragB = GameDetailsFragment.newInstance(item!!.id, item!!.content, item!!.details, item!!.image, item!!.link)
        transaction.replace(R.id.mainLayout, fragB)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout.setMargins(0, getStatusBarHeight(), 0 , 0)

        val transaction = supportFragmentManager.beginTransaction()
        val fragB = GameFragment.newInstance(1)
        transaction.add(R.id.mainLayout, fragB)
        transaction.addToBackStack(null)
        transaction.commit()

        val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        cpt = pref.getInt(PREF_NAME, 0)
        Log.e("getInt", cpt.toString())
    }

    fun View.setMargins(
        left: Int? = null,
        top: Int? = null,
        right: Int? = null,
        bottom: Int? = null
    ) {
        val lp = layoutParams as? ViewGroup.MarginLayoutParams
            ?: return

        lp.setMargins(
            left ?: lp.leftMargin,
            top ?: lp.topMargin,
            right ?: lp.rightMargin,
            bottom ?: lp.rightMargin
        )

        layoutParams = lp
    }

    private fun getStatusBarHeight(): Int {
        var result: Int = 0
        val resourceId: Int = getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId)
        }
        return result;
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onStop() {
        super.onStop()
        val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putInt(PREF_NAME, cpt).apply()
        Log.e("putInt", cpt.toString())
    }
}
