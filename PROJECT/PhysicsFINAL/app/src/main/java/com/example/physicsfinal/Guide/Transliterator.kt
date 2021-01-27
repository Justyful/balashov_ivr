package com.example.physicsfinal.Guide

class Transliterator () {
    fun transliterateRUtoEN(word : String): String {
        val newArray = ArrayList<String>()
        for (i in word) {
            when (i.toString().toLowerCase()){
                "а" -> newArray.add("a")
                "б" -> newArray.add("b")
                "в" -> newArray.add("v")
                "г" -> newArray.add("g")
                "д" -> newArray.add("d")
                "е" -> newArray.add("e")
                "ё" -> newArray.add("yo")
                "ж" -> newArray.add("zh")
                "з" -> newArray.add("z")
                "и" -> newArray.add("i")
                "й" -> newArray.add("y")
                "к" -> newArray.add("k")
                "л" -> newArray.add("l")
                "м" -> newArray.add("m")
                "н" -> newArray.add("n")
                "о" -> newArray.add("o")
                "п" -> newArray.add("p")
                "р" -> newArray.add("r")
                "с" -> newArray.add("s")
                "т" -> newArray.add("t")
                "у" -> newArray.add("u")
                "ф" -> newArray.add("f")
                "х" -> newArray.add("kh")
                "ц" -> newArray.add("ts")
                "ч" -> newArray.add("ch")
                "ш" -> newArray.add("sh")
                "щ" -> newArray.add("sh'")
                "ъ" -> newArray.add("_tz_") ///////////
                "ы" -> newArray.add("bl")
                "ь" -> newArray.add("_mz_") ///////////
                "э" -> newArray.add("e") ///////////
                "ю" -> newArray.add("yu")
                "я" -> newArray.add("ya")
                "." -> newArray.add("kkkk")
                "/" -> newArray.add("llll")
                "," -> newArray.add("mmmm")
                ";" -> newArray.add("nnnn")
                "[" -> newArray.add("oooo")
                "]" -> newArray.add("pppp")
                "|" -> newArray.add("qqqq")
                ":" -> newArray.add("ssss")
                "0" -> newArray.add("0")
                "1" -> newArray.add("1")
                "2" -> newArray.add("2")
                "3" -> newArray.add("3")
                "4" -> newArray.add("4")
                "5" -> newArray.add("5")
                "6" -> newArray.add("6")
                "7" -> newArray.add("7")
                "8" -> newArray.add("8")
                "9" -> newArray.add("9")
                " " -> newArray.add("_")
                "-" -> newArray.add("tttt")
            }
        }
        return newArray.joinToString(separator = "")
    }
}