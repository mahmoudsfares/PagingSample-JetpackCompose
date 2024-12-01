package com.example.pagingsample.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagingsample.Repository
import com.example.pagingsample.data.MyItem
import kotlinx.coroutines.flow.Flow

class MyPagingRepository(private val repository: Repository) {

    // TODO 5: create a paging repository.. naturally the paged data is a list of items
    //  so make this method return a flow of PagingData of that item
    fun getPagedItems(): Flow<PagingData<MyItem>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            // TODO 6: provide the class that implements the PagingResource interface as the paging resource factory
            pagingSourceFactory = { MyPagingSource(repository) }
        )
        // TODO 7: convert the created pager to flow
        return pager.flow
    }
}