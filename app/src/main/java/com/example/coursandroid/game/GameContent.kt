package com.example.coursandroid.game

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object GameContent {

    /**
     * An array of sample (game) items.
     */
    val ITEMS: MutableList<GameItem> = mutableListOf<GameItem>()

    /**
     * A dummy item representing a piece of content.
     */
    data class GameItem(val id: Int, val content: String, val details: String, var image: String, var link: String) {
        override fun toString(): String = content
    }
}
