package com.example.genshinwish.model

class Character(charName: String, charId: String, charRateUp: Boolean, charStar:Int): MyItem() {
    private var name: String = ""
    private var id: String = ""
    private var rateUp: Boolean = false
    private var cstar: Int =0

    init {
        name = charName
        id = charId
        rateUp = charRateUp
        cstar = charStar
    }

    fun getCharId(): String{
        return id
    }

}