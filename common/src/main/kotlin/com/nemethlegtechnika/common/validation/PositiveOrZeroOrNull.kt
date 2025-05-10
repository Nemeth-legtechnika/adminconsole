package com.nemethlegtechnika.common.validation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [PositiveOrZeroOrNullValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PositiveOrZeroOrNull(
    val message: String = "Number cannot be zero",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)

class PositiveOrZeroOrNullValidator : ConstraintValidator<PositiveOrZeroOrNull, Number> {

    override fun isValid(value: Number?, context: ConstraintValidatorContext) = value?.toDouble()?.let { it >= 0.0 } ?: true

}


