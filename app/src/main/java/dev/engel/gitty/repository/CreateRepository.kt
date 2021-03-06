package dev.engel.gitty.repository

interface CreateRepository<T, Q : CreateQuery> {
    suspend fun create(query: Q): T
}

interface CreateQuery

class CreateRepositoryException : RuntimeException()
