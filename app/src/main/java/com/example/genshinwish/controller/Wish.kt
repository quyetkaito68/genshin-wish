package com.example.genshinwish.controller

import com.example.genshinwish.model.Character
import kotlin.random.Random

class Wish : WishInterface {

    private var guaranteeFiveStar: Boolean = false
    private var guaranteeFourStar: Boolean = false
    private var fiveStarInit: Int = Random.nextInt(20)
    private var fourStarInit: Int = Random.nextInt(3)
    private var tempFive: Int = -1
    private var tempFour: Int = -1
    //
    private var fiveStarCharList = ArrayList<Character>()
    private var fourStarCharList = ArrayList<Character>()
    private var threeStar = ArrayList<Character>()

    init{
        val char: Character = Character("Venti","venti",true,5)
        fiveStarCharList.add(char)
        fiveStarCharList.add(Character("Diluc","diluc",false,5))
        fourStarCharList.add(Character("Razor","razor",true,4))
    }

    fun resetValue() {
        tempFive = -1
        tempFour = -1
        guaranteeFiveStar = false
        guaranteeFourStar = false
        fiveStarInit = Random.nextInt(20)
        fourStarInit = Random.nextInt(3)
    }

    fun getStar(): Int {
        val key = Random.nextInt(999)
        tempFive++
        tempFour++
        if (key <= 6 || fiveStarInit == 89 - tempFive) {
            fiveStarInit = Random.nextInt(85)
            tempFive = -1
            return 5
        } else if (key <= 51 || fourStarInit == 9 - tempFour) {
            //ra 4 sao
            fourStarInit = Random.nextInt(9)
            tempFour = -1
            return 4
        } else {
            //tra ve 3sao. ko can random
            return 3
        }
        return 0
    }

    override fun wishX1(): Int {
        var key2: Int = getStar()
        when (key2) {
            4 -> {
                return 4
            }
            5 -> {
                return 5
            }
            else -> return 3

        }
    }

    override fun wishX10(): ArrayList<Int> {
        var key3: Int
        var list = ArrayList<Int>()
        for (i in 1..10) {
            key3 = getStar()
            when (key3) {
                4 -> {
                    list.add(4)
                }
                5 -> {
                    list.add(5)
                }
                else -> {
                    list.add(3)
                }

            }
        }
        return list
    }

        fun wishX1Char(): Character {
        var key2: Int = getStar()
        when (key2) {
            4 -> {
                //kiem tra bao hiem
//                if (guaranteeFourStar==false){
//                    //random 4 sao
//                }else{
//                    //random 4sao rateup
//                }
                return fourStarCharList[0]
            }
            5 -> {
//                if (guaranteeFiveStar==false){
//                    //random 5sao
//                        val x= Random.nextInt(2)
//                    if (x==1){
//                        //trả về nhân vật rate up. lưu trạng thái là không lệch
//                    }else{
//                        //random lệch rate
//                        guaranteeFiveStar=true //lưu bảo hiểm
//                    }
//
//                }else{
//                    //random 5sao rateup
//                    //neu la banner nhan vat=> trả về luôn nhân vật
//                    //nếu là vũ khí => random 2.
//                }
                return fiveStarCharList[0]
            }
            else-> return fourStarCharList[0]
        }
    }

}