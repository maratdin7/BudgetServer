package org.budget.budgetserver.controller

import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/group")
class GroupController {

    @Autowired
    lateinit var groupService: GroupService

    @PostMapping("/create")
    fun createGroup(@RequestParam groupName: String) =
        groupService.createGroup(groupName)

    @PostMapping("/invitationToJoinGroup")
    fun invitationToJoinGroup(@RequestParam groupId: Int, @RequestParam emailForInvite: String): UserEntity =
        groupService.invitationToJoinGroup(groupId, emailForInvite)

    @GetMapping("/addUserToGroup")
    fun addUserToGroup(@RequestParam email: String, @RequestParam groupId: Int, @RequestParam token: String) =
        groupService.addUserToGroup(email, groupId, token)

    @GetMapping("/allMyGroups")
    fun getAllUserGroups(): List<GroupEntity> {
        return groupService.getAllUserGroups()
    }

    @PostMapping("/allUsersInGroup")
    fun getAllUsersInGroup(@RequestParam groupId: Int): List<UserEntity> {
        return groupService.getAllUsersInGroup(groupId)
    }

}