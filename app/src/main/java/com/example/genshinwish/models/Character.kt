package com.example.genshinwish.models

class Character(charName: String, charId: String, charRateUp: Boolean, charStar: Int) : MyItem() {
    private var name: String = ""
    private var id: String = ""
    private var rateUp: Boolean = false
    private var cstar: Int = 0

    init {
        name = charName
        id = charId
        rateUp = charRateUp
        cstar = charStar
    }

    fun getCharId(): String {
        return id
    }

    fun getCstar(): Int {
        return cstar
    }

    fun getRateUp(): Boolean{
        return rateUp
    }
}