package dev.engel.gitty.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import dagger.Reusable
import dev.engel.gitty.core.Skribe
import dev.engel.gitty.di.CoroutineIODispatcher
import dev.engel.gitty.repository.graphql.ViewerCardQuery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
class ViewerCardRepository @Inject constructor(
    private val apolloClient: ApolloClient,
    private val skribe: Skribe,
    @CoroutineIODispatcher private val ioDispatcher: CoroutineDispatcher
) : RetrieveRepository<ViewerCard, ViewerCardRetrieveQuery> {

    init {
        skribe tag javaClass.simpleName
    }

    suspend fun retrieve(): ViewerCard = retrieve(ViewerCardRetrieveQuery())

    override suspend fun retrieve(query: ViewerCardRetrieveQuery): ViewerCard {
        skribe trace "ViewerCardRepository#retrieve"
        return withContext(ioDispatcher) {
            try {
                val viewerResponse = apolloClient.query(ViewerCardQuery()).await()
                val viewerData = viewerResponse.data
                val viewerErrors = viewerResponse.errors

                if (viewerErrors.isNullOrEmpty() && viewerData != null) {
                    skribe trace "GraphQL returned viewerData: $viewerData"
                    viewerData
                } else {
                    skribe error "GraphQL returned one or more errors..."
                    viewerErrors?.forEach { skribe error it.message }
                    throw RetrieveRepositoryException()
                }
            } catch (ex: ApolloException) {
                skribe error "Encountered an ApolloException: ${ex.localizedMessage}"
                throw RetrieveRepositoryException()
            }
        }
    }
}

class ViewerCardRetrieveQuery : RetrieveQuery

typealias ViewerCard = ViewerCardQuery.Data
