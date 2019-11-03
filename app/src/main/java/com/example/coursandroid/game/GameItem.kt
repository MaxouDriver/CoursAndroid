package com.example.coursandroid.game

data class GameItem(val id: Int, val content: String, val details: String, var image: String, var link: String) {
    override fun toString(): String = content
}