package com.biblinder.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListType(value: ListType?): String? = value?.name

    @TypeConverter
    fun toListType(value: String?): ListType? = value?.let { enumValueOf<ListType>(it) }
}
