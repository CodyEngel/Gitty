package dev.engel.gitty.repository

interface RetrieveRepository<T, Q : RetrieveQuery> {
    suspend fun retrieve(query: Q) : T
}

interface RetrieveQuery

class RetrieveRepositoryException : RuntimeException()
