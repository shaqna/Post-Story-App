package com.ngedev.postcat.data.repository

import androidx.paging.*
import androidx.room.withTransaction
import com.ngedev.postcat.data.source.NetworkRequest
import com.ngedev.postcat.data.source.StoryRemoteMediator
import com.ngedev.postcat.data.source.local.LocalDatabase
import com.ngedev.postcat.data.source.local.RemoteKeys
import com.ngedev.postcat.data.source.response.ApiResponse
import com.ngedev.postcat.data.source.response.PostStoryResponse
import com.ngedev.postcat.data.source.response.StoriesResponse
import com.ngedev.postcat.data.source.service.StoryRemote
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.domain.repository.StoryRepository
import com.ngedev.postcat.domain.state.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody

class StoryRepositoryImpl(
    private val remote: StoryRemote,
    private val local: LocalDatabase
) : StoryRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllStories(): Flow<PagingData<Story>> =
        Pager(
            config = PagingConfig(10, enablePlaceholders = true, initialLoadSize = 10),
            remoteMediator = object : StoryRemoteMediator<Story, StoriesResponse, LocalDatabase>() {
                override suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Story>): RemoteKeys? =
                    state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                        ?.let { data ->
                            database.remoteKeysDao().getRemoteKeysId(data.id)
                        }

                override suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Story>): RemoteKeys? =
                    state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                        ?.let { data ->
                            database.remoteKeysDao().getRemoteKeysId(data.id)
                        }

                override suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Story>): RemoteKeys? =
                    state.anchorPosition?.let { position ->
                        state.closestItemToPosition(position)?.id?.let { id ->
                            database.remoteKeysDao().getRemoteKeysId(id)
                        }
                    }

                override suspend fun createCall(page: Int, size: Int): StoriesResponse =
                    remote.getAllStories(page, size)

                override val database: LocalDatabase
                    get() = local

                override suspend fun createDatabaseWithTransaction(
                    database: LocalDatabase,
                    loadType: LoadType,
                    response: StoriesResponse,
                    page: Int
                ): Boolean {

                    val endOfPaginationReached = response.listStory.isEmpty()
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.remoteKeysDao().deleteRemoteKeys()
                            database.storyDao().deleteAll()
                        }
                        val prevKey = if (page == 1) null else page - 1
                        val nextKey = if (endOfPaginationReached) null else page + 1
                        val keys = response.listStory.map {
                            RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                        }

                        database.remoteKeysDao().insertAll(keys)

                        response.listStory.forEach {
                            database.storyDao().insertStory(it)
                        }
                    }

                    return endOfPaginationReached

                }
            },
            pagingSourceFactory = {
                local.storyDao().getAllStories()
            }
        ).flow

    override fun getAllStoriesWithLocation(): Flow<Resource<StoriesResponse>> =
        object: NetworkRequest<StoriesResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<StoriesResponse>> =
                remote.getAllStoriesWithLocation()

            override fun fetchResult(result: StoriesResponse): StoriesResponse =
                result

        }.asFlow()


    override fun uploadStory(
        photo: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): Flow<Resource<PostStoryResponse>> =
        object : NetworkRequest<PostStoryResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<PostStoryResponse>> =
                remote.uploadStory(photo, description, lat, lon)

            override fun fetchResult(result: PostStoryResponse): PostStoryResponse =
                result

        }.asFlow()

}