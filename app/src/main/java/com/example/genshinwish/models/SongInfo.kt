package com.example.genshinwish.models

class SongInfo {
    var Title: String?=null
    var Author: String?=null
    var SongURL: String?=null

    constructor(Title: String?, Author: String?, SongURL: String?) {
        this.Title = Title
        this.Author = Author
        this.SongURL = SongURL
    }
}