package com.tuangiao.monolith.presentation.mvvm

import androidx.lifecycle.*
import com.tuangiao.monolith.common.DataResult
import com.tuangiao.monolith.domain.mapper.LegoThemeDomainUIMapper
import com.tuangiao.monolith.domain.usecase.LegoThemeUseCase
import com.tuangiao.monolith.presentation.model.LegoThemeUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/***
 * Sample of MVVM with live data usage
 */
@HiltViewModel
class LegoThemeViewModel @Inject constructor(
    private val legoThemeUseCase: LegoThemeUseCase,
    private val mapper: LegoThemeDomainUIMapper
) : ViewModel() {

    var legoThemes: LiveData<DataResult<List<LegoThemeUIModel>>> = MutableLiveData()

    init {
        viewModelScope.launch {
            legoThemes = legoThemeUseCase.getLegoThemes().map { mapper.transformDataResultFrom(it) }
                .asLiveData()
        }
//
//        viewModelScope.launch {
//            legoThemeUseCase.getLegoThemes().map { mapper.transformDataResultFrom(it) }
//                .collect { result ->
//                    when (result) {
//                        is DataResult.Loading -> {
//
//                        }
//                        is DataResult.Error -> {
//                        }
//
//                        is DataResult.Success -> {
//                        }
//                    }
//                }
//        }
    }
}