package com.example.eslamshahr.repositories

import com.example.eslamshahr.IDao
import com.example.eslamshahr.entities.User

class RegisterRepository(private val dao:IDao) {
    val users = dao.getAllUsers()

    suspend fun insert(user: User) {
        return dao.insertUser(user)
    }

    suspend fun getUserName(username: String) : User? {
        return dao.getUsername(username)
    }
}