package com.daya.notification_prototype.data.info.datasource

import androidx.paging.*
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.info.InfoEntity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class InfoPagingSource
@Inject
constructor(
    private val inforef: CollectionReference
) : PagingSource<Date, Info>() {

    override suspend fun load(params: LoadParams<Date>): LoadResult<Date, Info> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: Date()

            val batch = inforef
                    .orderBy("broadcastRequested", Query.Direction.DESCENDING)
                    .limit(20)
                    .startAfter(nextPageNumber)
                    .get()
                    .await()

            val listInfoEntity = batch.documents.map {
                //throw error java.lang.reflect.InvocationTargetException
                it.toObject<Info>()!!.apply {
                    senderId = it.id
                }
            }

            return LoadResult.Page(
                data = listInfoEntity,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
           return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Date, Info>): Date? = null

}