package org.budget.budgetserver.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

//@Target(AnnotationTarget.TYPE)
//@Retention(AnnotationRetention.RUNTIME)
//@Constraint(validatedBy = [PasswordMatchesValidator::class])
//annotation class PasswordMatches(
//    val message: String = "Password not valid",
//    val groups: Array<KClass<*>> = [],
//    val payload: Array<KClass<out Payload>> = []
//)