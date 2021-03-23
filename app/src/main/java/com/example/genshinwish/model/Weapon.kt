package com.example.genshinwish.model

class Weapon(weapName: String, weapId: Int, weapRateUp: Boolean): MyItem() {
    private var name: String = ""
    private var id: Int = 0
    private var rateUp: Boolean = false

    init {
        name = weapName
        id = weapId
        rateUp = weapRateUp
    }
}