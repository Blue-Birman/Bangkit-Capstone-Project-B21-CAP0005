package com.overheat.capstoneproject.core.data.source.remote

import com.overheat.capstoneproject.core.data.source.remote.network.ApiResponse
import com.overheat.capstoneproject.core.data.source.remote.response.FaqResponse
import com.overheat.capstoneproject.core.utils.JsonHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(
    private val jsonHelper: JsonHelper
) {

    fun getAllFaqs() : Flow<ApiResponse<List<FaqResponse>>> {
        return flow {
            try {
                val response = jsonHelper.readFaqDataset()
                val listFaqs = response.data

                if (listFaqs.isNotEmpty()) {
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}