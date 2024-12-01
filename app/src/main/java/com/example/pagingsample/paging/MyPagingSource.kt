package com.example.pagingsample.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingsample.Repository
import com.example.pagingsample.data.MyItem

// TODO 3: create a class implementing PagingSource, provide the key type (int) and the item data type
class MyPagingSource(private val repository: Repository) : PagingSource<Int, MyItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyItem> {
        return try {
            val page = params.key ?: 0
            // TODO 4: in load(), call the repo method to get the items to provide the load result page with
            val items = repository.getItems(page, params.loadSize)
            LoadResult.Page(
                data = items,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (items.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, MyItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}