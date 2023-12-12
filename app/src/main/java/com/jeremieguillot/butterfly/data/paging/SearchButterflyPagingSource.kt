package com.jeremieguillot.butterfly.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeremieguillot.butterfly.data.network.client.ApiClient
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class SearchButterflyPagingSource(
    private val apiClient: ApiClient,
    private val query: String,
) : PagingSource<Int, ButterflyModel>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ButterflyModel> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val filterParam = "(common_name~'$query' || latin_name~'$query')"
            val response = apiClient.searchButterflies(nextPageNumber, filterParam)
            LoadResult.Page(
                data = response.items.map { it.toDomainModel() },
                prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) null else nextPageNumber,
                nextKey = response.page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ButterflyModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}