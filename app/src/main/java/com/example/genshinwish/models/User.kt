package com.example.genshinwish.models

data class User(var name: String, var email: String) {
    constructor():this("","")
}