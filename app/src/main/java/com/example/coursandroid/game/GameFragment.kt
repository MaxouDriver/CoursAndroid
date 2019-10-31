package com.example.coursandroid.game

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.coursandroid.R

import com.example.coursandroid.game.GameContent.GameItem
import kotlinx.android.synthetic.main.fragment_game_details.*
import kotlinx.android.synthetic.main.fragment_game_list.*
import org.json.JSONArray
import org.json.JSONObject



/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [GameFragment.OnListFragmentInteractionListener] interface.
 */
class GameFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private var games: MutableList<GameItem> = GameContent.ITEMS

    private lateinit var gameAdapter: RecyclerView.Adapter<MyGameRecyclerViewAdapter.ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)


        gameAdapter = MyGameRecyclerViewAdapter(games, listener)

        fetchData()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        list.layoutManager = layoutManager
        list.adapter = gameAdapter

        swiperefresh.setOnRefreshListener {
            games.clear()
            fetchData()
        }
    }

    private fun fetchData() {
        val queue = Volley.newRequestQueue(context)
        val request = JsonArrayRequest(Request.Method.GET, "https://my-json-server.typicode.com/bgdom/cours-android/games", null,
            Response.Listener<JSONArray> { response: JSONArray? ->
                if (response != null) {
                    val responseSize = response.length()

                    var newGames: MutableList<GameContent.GameItem> = arrayListOf<GameContent.GameItem>()

                    for (i in 0 until responseSize) {
                        val myJsonObject = response.get(i) as JSONObject
                        newGames.add(GameContent.GameItem(i, myJsonObject.getString("name"), myJsonObject.getString("description"), myJsonObject.getString("img"), myJsonObject.getString("link")))
                    }
                    games.clear()
                    games.addAll(newGames)
                    gameAdapter.notifyDataSetChanged()

                    swiperefresh.isRefreshing = false
                }

            } , Response.ErrorListener { error -> Log.e("MAin", error.localizedMessage) })

        queue.add(request)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
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
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: GameItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
