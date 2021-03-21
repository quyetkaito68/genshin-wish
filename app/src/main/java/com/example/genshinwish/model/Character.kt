package com.example.genshinwish.model

class Character(charName: String, charId: Int, charRateUp: Boolean): MyItem() {
    private var name: String = ""
    private var id: Int = 0
    private var rateUp: Boolean = false

    init {
        name = charName
        id = charId
        rateUp = charRateUp
    }


}