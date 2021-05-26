package com.overheat.capstoneproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.overheat.capstoneproject.core.domain.usecase.SkinCancerUseCase

class HomeViewModel(private val useCase: SkinCancerUseCase) : ViewModel() {

    val faqs = useCase.getAllFaqs().asLiveData()
}