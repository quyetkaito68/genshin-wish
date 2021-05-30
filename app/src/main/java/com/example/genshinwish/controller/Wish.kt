package com.example.genshinwish.controller

import com.example.genshinwish.models.Character
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

    init {
        //init 4-5 star item list
        //5-s
        val char: Character = Character("Venti", "venti", true, 5)
        fiveStarCharList.add(char)
        fiveStarCharList.add(Character("Diluc", "diluc", false, 5))
        fiveStarCharList.add(Character("Mona", "mona", false, 5))
        fiveStarCharList.add(Character("Jean", "jean", false, 5))
        fiveStarCharList.add(Character("Qiqi", "qiqi", false, 5))
        fiveStarCharList.add(Character("Keqing", "keqing", false, 5))
        //4-s
        fourStarCharList.add(Character("Razor", "razor", true, 4))
        fourStarCharList.add(Character("Succrose", "succrose", true, 4))
        fourStarCharList.add(Character("Noelle", "noelle", true, 4))
        fourStarCharList.add(Character("Chongyun", "chongyun", false, 4))
        fourStarCharList.add(Character("Diona", "diona", false, 4))
        fourStarCharList.add(Character("Xinyan", "xinyan", false, 4))
        fourStarCharList.add(Character("Beidou", "beidou", false, 4))
        fourStarCharList.add(Character("Ningguang", "ningguang", false, 4))
        fourStarCharList.add(Character("Amber", "amber", false, 4))
        fourStarCharList.add(Character("Kaeya", "kaeya", false, 4))
        fourStarCharList.add(Character("Lisa", "lisa", false, 4))
        fourStarCharList.add(Character("Xingqiu", "xingqiu", false, 4))
        fourStarCharList.add(Character("Fischl", "fischl", false, 4))
        fourStarCharList.add(Character("Xiangling", "xiangling", false, 4))

        threeStar.add(Character("Noname","noname",false,3))

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
        if (key < 6 || fiveStarInit == 89 - tempFive) {
            fiveStarInit = Random.nextInt(89)
            tempFive = -1
            return 5
        } else if (key < 51 || fourStarInit == 9 - tempFour) {
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
        var list = ArrayList<Int>()
        for (i in 1..10) {
            list.add(wishX1())
        }
        return list
    }

    private fun wishX1Char(): Character {
        var key2: Int = getStar()
        var pos4: Int = 0
        var pos5: Int = 0
        when (key2) {
            4 -> {
                if (guaranteeFourStar == false) {//check bao hiem
                    if (Random.nextInt(1, 2) == 1) { //rateup 50%
                        //random rateup
                        pos4 = randomFourStarRateup()
                        guaranteeFourStar = false
                    } else { //lech rate
                        pos4 = Random.nextInt(0, fourStarCharList.size - 1)
                        if (fourStarCharList[pos4].getRateUp() == false) {
                            guaranteeFourStar = true
                        }
                    }
                } else {//co bao hiem
                    //random 4sao rateup
                    pos4 = randomFourStarRateup()
                    guaranteeFourStar = false
                }
                return fourStarCharList[pos4]
            }
            5 -> {
                if (guaranteeFiveStar == false) {
                    if (Random.nextInt(1, 2) == 1) { //rateup 50%
                        //random rateup
                        pos5 = 0
                        guaranteeFourStar = false
                    } else { //lech rate
                        pos5 = Random.nextInt(1, fiveStarCharList.size - 1)
                        guaranteeFiveStar = true
                    }
                } else {
                    pos5 = 0
                    guaranteeFiveStar = false
                    //random 5sao rateup
                    //neu la banner nhan vat=> trả về luôn nhân vật
                    //nếu là vũ khí => random list vu khi 2.
                }
                return fiveStarCharList[pos5]
            }
            else -> return threeStar[0]
        }
    }

    fun wishX10Char(): ArrayList<Character> {
        var list = ArrayList<Character>()
        for (i in 1..10) {
            list.add(wishX1Char())
        }
        return list
    }

    private fun randomFourStarRateup(): Int {
        var list = ArrayList<Int>()
        for (i in fourStarCharList) {
            if (i.getRateUp() == true) {
                list.add(fourStarCharList.indexOf(i))//vi tri cua char UP
            }
        }
        return list.random() //random vi tri char UP
    }

}