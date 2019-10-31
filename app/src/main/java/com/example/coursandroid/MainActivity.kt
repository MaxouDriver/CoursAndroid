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
import android.view.WindowManager
import com.example.coursandroid.game.*


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

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        val transaction = supportFragmentManager.beginTransaction()
        val fragB = GameFragment.newInstance(1)
        transaction.add(R.id.mainLayout, fragB)
        transaction.addToBackStack(null)
        transaction.commit()

        val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        cpt = pref.getInt(PREF_NAME, 0)
        Log.e("getInt", cpt.toString())
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
