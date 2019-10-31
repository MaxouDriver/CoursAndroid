package com.example.coursandroid.game

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.coursandroid.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_game_details.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GameDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GameDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameDetailsFragment : Fragment() {
    private var id: Int? = null
    private var name: String? = null
    private var details: String? = null
    private var image: String? = null
    private var link: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val picasso = Picasso.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt("id")
            name = it.getString("name")
            details = it.getString("details")
            image = it.getString("image")
            link = it.getString("link")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nameTextView.text = name
        detailsTextView.text = details
        picasso.load(image)
            .into(imageView)
        linkButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onButtonPressed()
            }})
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        listener?.onFragmentInteraction(link!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(link: String)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id: Int, name: String, details: String, image: String, link: String) =
            GameDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                    putString("name", name)
                    putString("details", details)
                    putString("image", image)
                    putString("link", link)
                }
            }
    }
}
