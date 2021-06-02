package com.overheat.capstoneproject.ui.photo

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.overheat.capstoneproject.core.domain.usecase.SkinCancerUseCase

class PhotoViewModel(private val useCase: SkinCancerUseCase) : ViewModel() {

    suspend fun getResult(imageString: String) = useCase.getResultFromImage(imageString)
}