package com.hotdogcode.spendwise.data.datasource.local.sqldatabase.converters

import androidx.room.TypeConverter
import com.hotdogcode.spendwise.common.IconName


class IconTypeConverter {
    @TypeConverter
    fun fromIcon(iconName: IconName): String {
        return iconName.name // Convert enum to String
    }

    @TypeConverter
    fun toIcon(iconNameString: String): IconName {
        return IconName.valueOf(iconNameString) // Convert String back to enum
    }
}