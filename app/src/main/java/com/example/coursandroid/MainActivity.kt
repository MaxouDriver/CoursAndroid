package com.example.coursandroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coursandroid.game.GameContent
import com.example.coursandroid.game.GameDetailsFragment
import com.example.coursandroid.game.GameFragment
import com.example.coursandroid.game.WebFragment


class MainActivity : AppCompatActivity(), GameFragment.OnListFragmentInteractionListener, GameDetailsFragment.OnFragmentInteractionListener, WebFragment.OnFragmentInteractionListener{
    private var cpt: Int = 0
    private val PREF_NAME: String = "cpt"

    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onFragmentInteraction(link: String) {
        cpt++

        if (cpt % 2 == 0) {
            val transaction = supportFragmentManager.beginTransaction()
            val fragB = WebFragment.newInstance(link)
            transaction.replace(R.id.mainLayout, fragB)
            transaction.addToBackStack(null)
            transaction.commit()
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

        val transaction = supportFragmentManager.beginTransaction()
        val fragB = GameFragment.newInstance(1)
        transaction.add(R.id.mainLayout, fragB)
        transaction.addToBackStack(null)
        transaction.commit()

        val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        cpt = pref.getInt(PREF_NAME, 0)
        Log.e("getInt", cpt.toString())
    }

    override fun onStop() {
        super.onStop()
        val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putInt(PREF_NAME, cpt).apply()
        Log.e("putInt", cpt.toString())
    }
}
