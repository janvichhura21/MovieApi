package com.example.movieapi.db

import androidx.room.TypeConverter

class IntListConvertors {
    @TypeConverter
    fun gettingListFromString(genreIds: String): List<Int> {
        val list: MutableList<Int> = ArrayList()
        val array = genreIds.split(",").toTypedArray()
        for (s in array) {
            if (!s.isEmpty()) {
                list.add(s.toInt())
            }
        }
        return list
    }

    @TypeConverter
    fun writingStringFromList(list: List<Int>): String {
        var genreIds = ""
        for (i in list) {
            genreIds += ",$i"
        }
        return genreIds
    }
}