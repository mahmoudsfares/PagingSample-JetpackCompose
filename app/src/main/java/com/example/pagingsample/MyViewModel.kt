package com.example.pagingsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingsample.data.MyItem
import com.example.pagingsample.paging.MyPagingRepository
import kotlinx.coroutines.flow.Flow

class MyViewModel(pagingRepository: MyPagingRepository) : ViewModel() {
    // TODO 8: call getPagedItems method in the paging repo class & cache it in viewModelScope to preserve the data
    val items: Flow<PagingData<MyItem>> = pagingRepository.getPagedItems().cachedIn(viewModelScope)
}