package org.budget.budgetserver.validation

import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

//class PasswordMatchesValidator : ConstraintValidator<PasswordMatches, Any> {
//
//    @Autowired
//    lateinit var userRepository: UserRepository
////
////    @Autowired
////    lateinit var
////
////    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
////        val userEntity = value as UserEntity
////        userRepository.getUserPassword(userEntity.name)
////    }
//
//}