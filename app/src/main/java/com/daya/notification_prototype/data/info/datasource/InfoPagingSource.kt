package com.daya.notification_prototype.data.info.datasource

import androidx.paging.*
import com.daya.notification_prototype.data.info.InfoEntity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class InfoPagingSource
@Inject
constructor(
    private val inforef: CollectionReference
) : PagingSource<Date, InfoEntity>() {

    override suspend fun load(params: LoadParams<Date>): LoadResult<Date, InfoEntity> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: DateFormat.getDateInstance().parse()

            val batch = inforef
                .orderBy("broadcastRequested", Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .await()

            val listInfoEntity = batch.documents.map {
                it.toObject<InfoEntity>()!!.apply {
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

    override fun getRefreshKey(state: PagingState<Date, InfoEntity>): Date? = null

}