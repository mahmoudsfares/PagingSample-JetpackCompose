package com.example.pagingsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingsample.data.MyItem
import kotlinx.coroutines.flow.Flow

class MyViewModel(private val myDataSource: MyDataSource) : ViewModel() {

    // TODO 5: create a method that creates a pager to page the data coming from the paging repo
    private fun getPagedItems(): Flow<PagingData<MyItem>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            // TODO 6: provide the paging repo with the data source instance as the paging factory
            pagingSourceFactory = { MyRepo(myDataSource) }
        )
        // TODO 7: convert the created pager to flow
        return pager.flow
    }

    // TODO 8: call getPagedItems and cache it in viewModelScope to preserve the data
    val items: Flow<PagingData<MyItem>> = getPagedItems().cachedIn(viewModelScope)
}