package com.example.clonemessandroid.di.edit_fullname

import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.di.ViewModelKey
import com.example.clonemessandroid.ui.edit_fullname.EditFullNameViewModel
import com.example.clonemessandroid.ui.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class EditFullNameViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(EditFullNameViewModel::class)
    abstract fun bindEditFullNameViewModel(editFullNameViewModel: EditFullNameViewModel): ViewModel
}