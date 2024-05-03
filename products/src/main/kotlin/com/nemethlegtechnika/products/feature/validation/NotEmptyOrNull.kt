package com.nemethlegtechnika.products.feature.validation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [NotEmptyOrNullValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NotEmptyOrNull(
    val message: String = "Cannot be empty",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)

class NotEmptyOrNullValidator : ConstraintValidator<NotEmptyOrNull, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext) = value?.isNotEmpty() ?: true

}


