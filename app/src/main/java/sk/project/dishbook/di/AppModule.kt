package sk.project.dishbook.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sk.project.dishbook.data.repository.Repository
import sk.project.dishbook.ui.detail.DetailViewModel
import sk.project.dishbook.ui.home.HomeViewModel

val appModule = module {

    single { Repository() }

    viewModel { HomeViewModel(get()) }

    viewModel { DetailViewModel(get()) }

}