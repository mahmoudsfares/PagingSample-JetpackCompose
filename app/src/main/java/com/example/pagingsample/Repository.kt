package com.example.pagingsample

import com.example.pagingsample.data.MyItem
import kotlinx.coroutines.delay

class Repository {

    private val remoteDataSource = (1..100).map {
        MyItem(
            title = "Item $it",
            description = "Description $it"
        )
    }

    // TODO 2: make the repository return a list of the required data type
    suspend fun getItems(page: Int, pageSize: Int): List<MyItem> {
        delay(2000L)
        val startingIndex = page * pageSize
        return if(startingIndex + pageSize <= remoteDataSource.size) {
            remoteDataSource.slice(startingIndex until startingIndex + pageSize)
        } else emptyList()
    }
}