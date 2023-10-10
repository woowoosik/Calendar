package com.woo.calendarapp.viewmodel

import androidx.lifecycle.ViewModel
import com.woo.calendarapp.module.RepositoryRoom
import com.woo.calendarapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    @RepositoryRoom private val repo: Repository
) : ViewModel() {


}