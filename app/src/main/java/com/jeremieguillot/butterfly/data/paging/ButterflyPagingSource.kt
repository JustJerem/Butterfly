package com.jeremieguillot.butterfly.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeremieguillot.butterfly.data.network.client.ApiClient
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ButterflyPagingSource(
    private val apiClient: ApiClient,
    private val filter: String,
) : PagingSource<Int, ButterflyModel>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ButterflyModel> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = apiClient.getAllButterflies(nextPageNumber, filter)
            LoadResult.Page(
                data = response.items.map { it.toDomainModel() },
                prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) null else nextPageNumber,
                nextKey = (response.page + 1).takeIf { it <= response.totalPages }
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

        //https://warm-butterfly.pockethost.io/api/collections/butterfly/records?page=1&filter=(family="Papilionidés")&sort=common_name
    }

    override fun getRefreshKey(state: PagingState<Int, ButterflyModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}