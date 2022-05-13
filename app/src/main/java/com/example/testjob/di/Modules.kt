package com.example.testjob.di

import com.example.testjob.data.repository.Repository
import com.example.testjob.ui.viewModels.ItemsViewModel
import com.example.testjob.ui.viewModels.SomeItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ItemsViewModel(get())
    }

    viewModel { parameters ->
        SomeItemViewModel(get(), parameters[0])
    }
}

val repositoryModule = module {
    single { Repository() }
}