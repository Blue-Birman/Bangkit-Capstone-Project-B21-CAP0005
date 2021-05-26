package com.overheat.capstoneproject.core.data.source

import com.overheat.capstoneproject.core.data.NetworkBoundResource
import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.data.source.local.LocalDataSource
import com.overheat.capstoneproject.core.data.source.remote.RemoteDataSource
import com.overheat.capstoneproject.core.data.source.remote.network.ApiResponse
import com.overheat.capstoneproject.core.data.source.remote.response.FaqResponse
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.Faq
import com.overheat.capstoneproject.core.domain.repository.ISkinCancerRepository
import com.overheat.capstoneproject.core.utils.AppExecutors
import com.overheat.capstoneproject.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SkinCancerRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : ISkinCancerRepository {

    override fun getAllFaqs(): Flow<Resource<List<Faq>>> {
        return object : NetworkBoundResource<List<Faq>, List<FaqResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Faq>> {
                return localDataSource.getAllFaqs().map {
                    DataMapper.mapEntityToDomainFaq(it)
                }
            }

            override fun shouldFetch(data: List<Faq>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<FaqResponse>>> {
                return remoteDataSource.getAllFaqs()
            }

            override suspend fun saveCallResult(data: List<FaqResponse>) {
                val listFaqs = DataMapper.mapResponseToEntityFaq(data)
                localDataSource.insertAllFaqs(listFaqs)
            }
        }.asFlow()
    }

    override fun getAllArticles(): Flow<Resource<List<Article>>> {
        TODO("Not yet implemented")
    }
}