package com.example.pagingsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.example.pagingsample.data.MyItem
import com.example.pagingsample.paging.MyPagingRepository
import com.example.pagingsample.ui.theme.PagingSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = MyViewModel(MyPagingRepository(Repository()))
            PagingSampleTheme {
                ItemList(viewModel)
            }
        }
    }
}

@Composable
fun ItemList(viewModel: MyViewModel) {
    // TODO 9: collect the flow in the view model as lazy paging items
    val lazyPagingItems = viewModel.items.collectAsLazyPagingItems()

    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        // TODO 10: show the data in a lazy column
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            // TODO 11: provide UI representation for the different states of data on appending

            // expected scenario: a list of items in the page
            items(
                count = lazyPagingItems.itemCount,
                contentType = lazyPagingItems.itemContentType { "MyItem" },
            ) { index ->
                val item: MyItem? = lazyPagingItems[index]
                if (item != null) {
                    Column {
                        Text(text = item.title)
                        Text(text = item.description)
                    }
                }
            }

            // loading a new page
            if (lazyPagingItems.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // error getting a new page
            else if (lazyPagingItems.loadState.append is LoadState.Error) {
                item {
                    Text(
                        text = "Error loading more items",
                        color = Color.Red
                    )
                }
            }
        }

        // TODO 12: outside of the lazy column scope, provide UI representation for initial state (first page)

        // loading for the first time
        if (lazyPagingItems.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // error while fetching data for the first time
        else if (lazyPagingItems.loadState.refresh is LoadState.Error) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error loading data",
                    color = Color.Red
                )
            }
        }
    }
}
