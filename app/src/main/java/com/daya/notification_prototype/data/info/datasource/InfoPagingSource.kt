package com.daya.notification_prototype.data.info.datasource

import androidx.paging.*
import com.daya.notification_prototype.data.info.InfoNet
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class InfoPagingSource
@Inject
constructor(
    private val inforef: CollectionReference
) : PagingSource<Date, InfoNet>() {

    override suspend fun load(params: LoadParams<Date>): LoadResult<Date, InfoNet> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: Date()

            val batch = inforef
                    .orderBy("broadcastRequested", Query.Direction.DESCENDING)
                    .limit(20)
                    .startAfter(nextPageNumber)
                    .get()
                    .await()


            val listInfoEntity  = batch.documents.map {
                //throw error java.lang.reflect.InvocationTargetException
                it.toObject(InfoNet::class.java).apply {
                    this ?: return@apply
                    senderId = it.id
                }
            }

            val nonNUllList : List<InfoNet> = listInfoEntity as List<InfoNet>

            return LoadResult.Page(
                data = nonNUllList,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
           return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Date, InfoNet>): Date? {
        val lastPosition = state.anchorPosition ?: return null
        val lastFetchedInfo = state.closestItemToPosition(lastPosition)
        return lastFetchedInfo?.broadcastRequested
    }

}