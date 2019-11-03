package com.example.coursandroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.coursandroid.game.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), GameFragment.OnListFragmentInteractionListener, GameDetailsFragment.OnFragmentInteractionListener{
    private var cpt: Int = 0
    private val PREF_NAME: String = "cpt"

    // On interaction with the game detail link button
    override fun onFragmentInteraction(link: String) {
        cpt++

        if (cpt % 2 == 0) {
            // Start the activity for game detail
            val intent = Intent(this@MainActivity,GameWebDetailsActivity::class.java)
            intent.putExtra("link",link)
            startActivity(intent)
        }else{
            // Start the web browser
            val uris = Uri.parse(link)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            startActivity(intents)
        }
    }
    // On interaction on list item click
    override fun onListFragmentInteraction(item: GameItem?) {
        // Show the fragment of the game details
        val transaction = supportFragmentManager.beginTransaction()
        val fragB = GameDetailsFragment.newInstance(item!!.id, item!!.content, item!!.details, item!!.image, item!!.link)
        transaction.replace(R.id.mainLayout, fragB)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add margin of the status bar for maximum compatibilities
        mainLayout.setMargins(0, getStatusBarHeight(), 0 , 0)

        // Load the game list fragment
        val transaction = supportFragmentManager.beginTransaction()
        val fragB = GameFragment.newInstance(1)
        transaction.add(R.id.mainLayout, fragB)
        transaction.addToBackStack(null)
        transaction.commit()

        // Retrieve the cpt from the shared preferences
        val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        cpt = pref.getInt(PREF_NAME, 0)
    }

    // Add method in view to dynamically change margin
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

    // Method used to get the height of the status bar
    private fun getStatusBarHeight(): Int {
        var result: Int = 0
        val resourceId: Int = getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId)
        }
        return result;
    }

    // When the application stop, save the counter in the shared preferences
    override fun onStop() {
        super.onStop()
        val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putInt(PREF_NAME, cpt).apply()
    }
}
