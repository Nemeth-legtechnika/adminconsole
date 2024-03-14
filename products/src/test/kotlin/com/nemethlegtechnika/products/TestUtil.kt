package com.nemethlegtechnika.products

import com.nemethlegtechnika.products.db.model.BaseEntity
import java.util.*

val <T : BaseEntity> T.optional: Optional<T> get() = Optional.of(this)
