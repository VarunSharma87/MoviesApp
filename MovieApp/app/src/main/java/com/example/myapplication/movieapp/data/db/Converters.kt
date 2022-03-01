package com.example.myapplication.movieapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String?): IntArray? {
        return value?.let {
            val listType = object : TypeToken<IntArray?>() {}.type
            Gson().fromJson(value, listType)
        }

    }

    @TypeConverter
    fun fromArray(array: IntArray?): String? {
        return array?.let {
            val gson = Gson()
            gson.toJson(array)
        }
    }

    @TypeConverter
    fun fromArray(array: List<String>?): String? {
        return array?.let {
            val gson = Gson()
            gson.toJson(array)
        }
    }

    @TypeConverter
    fun fromStringToList(value: String?): List<String>? {
        return value?.let {
            val listType = object : TypeToken<List<String>?>() {}.type
            Gson().fromJson(value, listType)
        }
    }
}