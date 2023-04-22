package com.nemethlegtechnika.products.util


inline fun Long.round(action: (Long) -> Double): Long {
    val value = action(this)
    return kotlin.math.round(value).toLong()
}