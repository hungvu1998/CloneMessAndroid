package com.example.clonemessandroid.di.detail_chat

import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.di.ViewModelKey
import com.example.clonemessandroid.ui.detail_chat.DetailChatViewModel
import com.example.clonemessandroid.ui.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailChatViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailChatViewModel::class)
    abstract fun bindDetailChatViewModel(detailChatViewModel: DetailChatViewModel): ViewModel
}