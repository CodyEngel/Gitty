package dev.engel.gitty.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import dagger.Reusable
import dev.engel.gitty.di.CoroutineIODispatcher
import dev.engel.gitty.repository.graphql.ViewerCardQuery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Reusable
class ViewerCardRepository @Inject constructor(
    private val apolloClient: ApolloClient,
    @CoroutineIODispatcher private val ioDispatcher: CoroutineDispatcher
) : RetrieveRepository<ViewerCard, ViewerCardRetrieveQuery> {
    suspend fun retrieve(): ViewerCard = retrieve(ViewerCardRetrieveQuery())

    override suspend fun retrieve(query: ViewerCardRetrieveQuery): ViewerCard {
        return withContext(ioDispatcher) {
            try {
                val viewerResponse = apolloClient.query(ViewerCardQuery()).await()
                val viewerData = viewerResponse.data
                val viewerErrors = viewerResponse.errors

                if (viewerErrors.isNullOrEmpty() && viewerData != null) {
                    viewerData
                } else {
                    throw RetrieveRepositoryException()
                }
            } catch (ex: ApolloException) {
                throw RetrieveRepositoryException()
            }
        }
    }
}

class ViewerCardRetrieveQuery : RetrieveQuery

typealias ViewerCard = ViewerCardQuery.Data
