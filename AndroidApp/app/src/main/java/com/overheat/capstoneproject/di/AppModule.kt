package com.overheat.capstoneproject.di

import com.overheat.capstoneproject.core.domain.usecase.SkinCancerInteractor
import com.overheat.capstoneproject.core.domain.usecase.SkinCancerUseCase
import com.overheat.capstoneproject.ui.photo.PhotoViewModel
import com.overheat.capstoneproject.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<SkinCancerUseCase> {
        SkinCancerInteractor(get())
    }
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }
}