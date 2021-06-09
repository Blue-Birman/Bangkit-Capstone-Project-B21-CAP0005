package com.overheat.capstoneproject.ui.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overheat.capstoneproject.core.data.source.remote.RemoteDataSource
import com.overheat.capstoneproject.core.domain.model.Result
import com.overheat.capstoneproject.core.utils.DataMapper

class PhotoViewModel(private val remote: RemoteDataSource) : ViewModel() {

    val result: MutableLiveData<Result> = MutableLiveData<Result>()

    fun getResultFromImage(image: String) {
        remote.getResultFromImageVoid(image) {
            if (it != null) {
                result.value = DataMapper.mapResponseToDomainResult(it)
            }
        }
    }
}