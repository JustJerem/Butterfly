package com.jeremieguillot.butterfly.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.jeremieguillot.butterfly.domain.model.VisibleMonth
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Types

@ProvidedTypeConverter
@JsonClass(generateAdapter = true)
class VisibleMonthConverter : Converter() {

    @TypeConverter
    fun fromString(value: String): List<VisibleMonth> {
        val listType = Types.newParameterizedType(List::class.java, VisibleMonth::class.java)
        val adapter: JsonAdapter<List<VisibleMonth>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun fromList(list: List<VisibleMonth>): String {
        val listType = Types.newParameterizedType(List::class.java, VisibleMonth::class.java)
        val adapter: JsonAdapter<List<VisibleMonth>> = moshi.adapter(listType)
        return adapter.toJson(list)
    }
}
