package com.example.coursandroid.game

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.coursandroid.R


import com.example.coursandroid.game.GameFragment.OnListFragmentInteractionListener
import com.example.coursandroid.game.GameContent.GameItem
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.fragment_game.view.*
import kotlin.random.Random

/**
 * [RecyclerView.Adapter] that can display a [GameItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyGameRecyclerViewAdapter(private val mValues: List<GameItem>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyGameRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val picasso = Picasso.get()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as GameItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mNameView.text = item.content

        val difficulty = when(Random.nextInt(1, 3)) {
            1 -> "Easy"
            2 -> "Medium"
            3 -> "Hard"
            else -> "NA"
        }
        holder.mDifficulty.text = difficulty

        picasso.load(item.image)
            .into(holder.mImageView)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameView: TextView = mView.name
        val mDifficulty: TextView = mView.difficulty
        val mImageView: ImageView = mView.image

        override fun toString(): String {
            return super.toString() + " '" + mNameView.text + "'"
        }
    }
}
