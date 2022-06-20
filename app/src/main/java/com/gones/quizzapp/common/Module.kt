package com.gones.quizzapp.common

import com.gones.quizzapp.data.local.LocalNameApi
import com.gones.quizzapp.data.local.LocalQuizzApi
import com.gones.quizzapp.data.remote.QuizzApi
import com.gones.quizzapp.data.repository.QuizzRepository
import com.gones.quizzapp.data.repository.UserRepository
import com.gones.quizzapp.ui.quizz.QuizzViewModel
import com.gones.quizzapp.util.Constant.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules by lazy {
    listOf(
        viewModelModule,
        repositoryModule,
        dataModule
    )
}

val viewModelModule: Module = module {
    viewModel { QuizzViewModel(get(), get()) }
}

val repositoryModule: Module = module {
    single { QuizzRepository(quizzApi = get(), localQuizzApi = get()) }
    single { UserRepository(get()) }
}

val dataModule: Module = module {
    single { createApiClient().create(QuizzApi::class.java) }
    single { LocalQuizzApi(appContext = get()) }
    single { LocalNameApi(appContext = get()) }
}

fun createApiClient(): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}

