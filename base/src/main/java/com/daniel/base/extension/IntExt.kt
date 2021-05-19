package com.daniel.base.extension

import com.daniel.base.Consts.FEET_MULTIPLIER_VALUE
import com.daniel.base.Consts.INCH_MULTIPLIER_VALUE
import java.lang.StringBuilder

fun Int.formatHeight(): String {
    val inch = String.format(" %.2f", INCH_MULTIPLIER_VALUE * this)
    val feet = String.format(" %.2f", FEET_MULTIPLIER_VALUE * this)
    val heightInCm = StringBuilder(this.toString()).insert(1, ".")
    return "Height in: cm: $heightInCm - In feet: $feet - In inches: $inch"
}
