package org.budget.budgetserver.service.token

import org.budget.budgetserver.exception.UserIsAlreadyMemberOfGroupException
import org.budget.budgetserver.exception.UserToGroupTokenException
import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.jpa.UserToGroupTokenEntity
import org.budget.budgetserver.repository.UserToGroupTokenRepository
import org.budget.budgetserver.service.AccessService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserToGroupTokenService {

    @Autowired
    private lateinit var userToGroupRepository: UserToGroupTokenRepository

    @Autowired
    private lateinit var accessService: AccessService

    private fun generateToken(): String = UUID.randomUUID().toString()

    fun generateToken(userEntity: UserEntity, groupEntity: GroupEntity): String {

        var userToGroupTokenEntity =
            userToGroupRepository.findByUserIdAndGroupId(userEntity.id, groupEntity.id)

        if (userToGroupTokenEntity == null) {
            userToGroupTokenEntity = UserToGroupTokenEntity(
                refUserEntity = userEntity,
                refGroupEntity = groupEntity,
                token = ""
            )
        }

        userToGroupTokenEntity.token = generateToken()
        userToGroupRepository.save(userToGroupTokenEntity)

        return userToGroupTokenEntity.token
    }

    fun validateToken(userId: Int, groupId: Int, token: String): Boolean {
        accessService.userNotMemberOfGroup(userId, groupId)

        userToGroupRepository.findByUserIdAndGroupIdAndToken(userId, groupId, token) ?:
            throw UserToGroupTokenException()

        return true
    }

}