package com.groupcreativesolution.authuser.services

import com.groupcreativesolution.authuser.dtos.UserDto
import com.groupcreativesolution.authuser.models.UserModel
import com.groupcreativesolution.authuser.repositories.UserRepository
import com.groupcreativesolution.authuser.services.impl.UserServiceImpl
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.Test

@SpringBootTest
class UserServiceImplTest {

    private val userRepository: UserRepository = mock(UserRepository::class.java)
    private val userService: UserService = UserServiceImpl(userRepository)

    @Test
    fun test_findAllUser_mustReturnAllUserInTheBank() {
        //given
        val listAllUser = listOf(
            UserModel(UUID.randomUUID(), "username", "email", "password", "role"),
            UserModel(UUID.randomUUID(), "username", "email", "password", "role")
        )
        //when
        `when`(userRepository.findAll()).thenReturn(listAllUser)
        val result = userService.findAllUser()
        //then
        assert(result.size == 2) { "size must be 2" }

    }

    @Test
    fun test_findById_mustReturnOneUser() {
        //given
        val userId = UUID.randomUUID()
        val listAllUser = listOf(
            UserModel(UUID.randomUUID(), "username", "1234", "test@test.com", "role"),
            UserModel(UUID.randomUUID(), "username", "12345", "test.test@test.com", "role"),
            UserModel(userId, "username", "123456", "test.test@test.com.br", "role")
        )

        //when
        `when`(userRepository.findById(userId)).thenReturn(listAllUser.find { it.userId == userId }
            ?.let { Optional.of(it) })
        val result = userService.findById(userId)

        //then
        assert(result.isPresent) { "result must be present" }
        assert(result.get().userId == userId) { "userId must be equal" }
        assert(result.get().username == "username") { "username must be equal" }
        assert(result.get().email == "test.test@test.com.br") { "email must be equal" }
        assert(result == listAllUser.find { it.userId == userId }
            ?.let { Optional.of(it) }) { "result must be equal" }
    }

    @Test
    fun test_deleteUserByUser_mustDeleteOneUser() {
        //given
        val listAllUser = mutableListOf(
            UserModel(UUID.randomUUID(), "username", "email", "password", "role"),
            UserModel(UUID.randomUUID(), "username", "email", "password", "role")
        )

        //when
        `when`(userRepository.delete(listAllUser[0])).then {
            listAllUser.removeAt(0)
        }
        userService.deleteUser(listAllUser[0])

        //then
        assert(listAllUser.size == 1) { "size must be 1" }

    }

    @Test
    fun test_saveUser_mustSaveOneUser() {
        //given
        val user = UserDto( "username","123456", "test@test.com", "role")
        val listUser = mutableListOf<UserModel>()

        //when
        `when`(userRepository.save(UserDto.fromDto(user))).thenReturn(UserDto.fromDto(user))

        listUser.add(userService.saveUser(user))

        //then
        assert(listUser.size == 1) { "size must be 1" }
    }

    @Test
    fun test_findByUsername_mustReturnOneUser() {
        //given
        val username = "username"
        val listAllUser = listOf(
            UserModel(UUID.randomUUID(), "username", "1234", "test@test.com", "role"),
            UserModel(UUID.randomUUID(), "test", "12345", "", "role")
        )

        //when
        `when`(userRepository.findByUsername(username)).thenReturn(listAllUser.find { it.username == username }
            ?.let { Optional.of(it) }
        )

        val result = userService.findByUsername(username)

        //then
        assert(result.isPresent) { "result must be present" }
        assert(result.get().username == username) { "username must be equal" }
        assert(result.get().email == "test@test.com") { "email must be equal" }
    }

    @Test
    fun test_findByUsername_mustReturnEmptyUser_whenNotHaveUser() {
        //given
        val username = "userna"
        val listAllUser = listOf(
            UserModel(UUID.randomUUID(), "username", "1234", "test@test.com", "role"),
            UserModel(UUID.randomUUID(), "test", "12345", "", "role")
        )

        //when
        `when`(userRepository.findByUsername(username)).thenReturn(listAllUser.find { it.username == username }
            ?.let { Optional.of(it) } ?: Optional.empty()
        )

        val result = userService.findByUsername(username)

        //then
        assert(result.isEmpty ) { "result must be not present" }
    }

    @Test
    fun test_findByEmail_mustReturnOneUser() {
        //given
        val email = "test@test.com"

        val listAllUser = listOf(
            UserModel(UUID.randomUUID(), "username", "1234", "test@test.com", "role"),
            UserModel(UUID.randomUUID(), "test", "12345", "", "role")
        )

        //when
        `when`(userRepository.findByEmail(email)).thenReturn(listAllUser.find { it.email == email }
            ?.let { Optional.of(it) }
        )

        val result = userService.findByEmail(email)

        //then
        assert(result.isPresent) { "result must be present" }
        assert(result.get().email == email) { "email must be equal" }

    }
}