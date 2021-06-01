package com.example.genshinwish.models

import java.io.Serializable

data class Song(var title: String, var singer: String, var image: Int, val resource: Int?, var url: String, val duration: Int): Serializable
