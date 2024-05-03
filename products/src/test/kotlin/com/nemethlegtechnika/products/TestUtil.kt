package com.nemethlegtechnika.products

import com.nemethlegtechnika.products.model.BaseEntity
import java.util.*

val <T : BaseEntity> T.optional: Optional<T> get() = Optional.of(this)
