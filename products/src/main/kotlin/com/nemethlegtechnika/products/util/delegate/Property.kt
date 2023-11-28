package com.nemethlegtechnika.products.util.delegate

import javax.swing.JOptionPane.UNINITIALIZED_VALUE
import kotlin.reflect.KProperty

/**
 * Delegate for [com.nemethlegtechnika.products.db.model.CustomProperty]
 * Initialize the stored value laze like [Lazy]
 */
class Property<T> (
    private val name: String,
    private val initializer: (String) -> T,
    private val setterEffect: (String, T) -> Unit,
) {
    private var value: Any? = UNINITIALIZED_VALUE

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return synchronized(this) {
            val v1 = value
            if (v1 !== UNINITIALIZED_VALUE) {
                v1 as T
            } else {
                value = initializer(name)
                value as T
            }
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        synchronized(this) {
            setterEffect(name, value)
            this.value = value
        }
    }
}

