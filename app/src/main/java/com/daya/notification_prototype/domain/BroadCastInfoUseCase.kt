package com.daya.notification_prototype.domain

import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.broadcast.datasource.BroadcastRepository
import com.daya.notification_prototype.data.info.InfoEntity
import com.daya.notification_prototype.data.info.InfoNet
import com.daya.notification_prototype.di.DefaultDispatcher
import com.daya.notification_prototype.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject


class BroadCastInfoUseCase
@Inject
constructor(
    private val repo: BroadcastRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val readFileDispatcher: CoroutineDispatcher
) : FlowUseCase<Info, Unit>(coroutineDispatcher) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(param: Info): Flow<Resource<Unit>> = callbackFlow<Resource<Unit>> {
        val infoNet = InfoNet(
            senderId = param.senderId,
            title = param.title,
            urlAccess = param.urlAccess,
            description = param.description,
            urlImage = param.urlImage,
            status = param.status,
            topics = param.topics,
            broadcastRequested = param.broadcastRequested
        )

        val isImageNotExist = infoNet.urlImage.isEmpty()
        offer(
            Resource.loading(
                if (isImageNotExist) "BroadCasting With Img" else "BroadCasting Without Img"
            )
        )
        if (isImageNotExist) {
            val casted = repo.broadCastWithOutImg(infoNet)
            val resCasted = casted!!.let {
                Resource.success(Unit)
            }
            offer(resCasted)
        } else {
            val stringImage = infoNet.urlImage
            val fileImage = File(stringImage)

            val streamImageLocal = withContext(readFileDispatcher) {
                repo.getImageIstreamFromUri(stringImage) // it should be nonNull
            }

            val uploadedName = "${System.currentTimeMillis()}_${fileImage.name}"

            val uploadedImageRef = repo.addChild(uploadedName)

            val uploadTask = uploadedImageRef.putStream(streamImageLocal)
                .addOnProgressListener { taskSnapshot ->
                    val progress: Double =
                        100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    offer(Resource.loading(progress = "uploading img: $progress"))
                }
                .addOnFailureListener {
                    offer(Resource.error(it.localizedMessage)) // it should get cought by flowUseCase
                }
                .addOnSuccessListener {
                    offer(Resource.loading(progress = "image uploaded"))
                }

            val uriImageCloud = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        offer(Resource.error(it.localizedMessage))
                    }
                }
                uploadedImageRef.downloadUrl
            }.await() ?: offer(Resource.error(" failed to upload image"))

            val infoWithDownloadUri = infoNet.apply {
                urlImage = uriImageCloud.toString()
            }
            repo.broadCastWithImg(infoWithDownloadUri)
                .addOnCompleteListener {task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            offer(Resource.error(it.localizedMessage))
                        }
                    }
                    this.offer(Resource.success(Unit))
                }

            awaitClose {
                if (uploadTask.isInProgress || uploadTask.isPaused) uploadTask.cancel()
            }
        }
    }
}