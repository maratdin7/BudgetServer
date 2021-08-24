package org.budget.budgetserver.service.token

import org.budget.budgetserver.exception.UserAlreadyActivatedException
import org.budget.budgetserver.exception.VerificationTokenNotFoundException
import org.budget.budgetserver.jpa.ConfirmationTokenEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.ConfirmationTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConfirmationTokenService : AbstractTokenService<String>() {

    @Autowired
    private lateinit var confirmationTokenRepository: ConfirmationTokenRepository

    override fun generateToken(userEntity: UserEntity): String {
        if (userEntity.enable)
            throw UserAlreadyActivatedException()

        var confirmationTokenEntity = confirmationTokenRepository.findByUserId(userEntity.id)

        if (confirmationTokenEntity != null)
            return confirmationTokenEntity.token

        confirmationTokenEntity = ConfirmationTokenEntity(
            refUserEntity = userEntity,
            token = stringTokenGenerator(),
        )

        confirmationTokenEntity = confirmationTokenRepository.save(confirmationTokenEntity)
        return confirmationTokenEntity.token
    }

    override fun validateToken(userId: Int, token: String): Boolean {
        val tokenExist = confirmationTokenRepository.existsByUserIdAndToken(userId, token)

        if (tokenExist.not())
            throw VerificationTokenNotFoundException()

        return true
    }
}
