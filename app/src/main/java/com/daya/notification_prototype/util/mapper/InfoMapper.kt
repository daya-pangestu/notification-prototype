package com.daya.notification_prototype.util.mapper

import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.info.InfoEntity
import com.daya.notification_prototype.data.info.InfoNet
import com.daya.notification_prototype.util.mapper.TopicMapper.mapEntityToGeneral
import com.daya.notification_prototype.util.mapper.TopicMapper.mapEntityToString
import com.daya.notification_prototype.util.mapper.TopicMapper.mapGeneralToEntity
import com.daya.notification_prototype.util.mapper.TopicMapper.mapGeneralToString
import com.daya.notification_prototype.util.mapper.TopicMapper.mapStringToEntity
import com.daya.notification_prototype.util.mapper.TopicMapper.mapStringToGeneral

object InfoMapper : Mapper<Info,InfoNet,InfoEntity> {
    override fun List<Info>.mapGeneralToNet(): List<InfoNet> {
        return this.map {
            InfoNet(
                senderId = it.senderId,
                title = it.title,
                description = it.description,
                urlAccess = it.urlAccess,
                urlImage = it.urlImage,
                status = it.status,
                topics = it.topics.mapGeneralToString(),
                broadcastRequested = it.broadcastRequested
            )
        }
    }

    override fun List<Info>.mapGeneralToEntity(): List<InfoEntity> {
        return this.map {
            InfoEntity(
                    senderId = it.senderId ?: "",
                    title = it.title,
                    description = it.description,
                    urlAccess = it.urlAccess,
                    urlImage = it.urlImage,
                    status = it.status,
                    topics = it.topics.mapGeneralToEntity(),
                    broadcastRequested = it.broadcastRequested
            )
        }
    }

    override fun List<InfoNet>.mapNetToGeneral(): List<Info> {
        return this.map {
            Info(
                senderId = it.senderId,
                title = it.title,
                description = it.description,
                urlAccess = it.urlAccess,
                urlImage = it.urlImage ?: "",
                status = it.status,
                topics = it.topics.mapStringToGeneral(),
                broadcastRequested = it.broadcastRequested
            )
        }
    }

    override fun List<InfoNet>.mapNetToEntity(): List<InfoEntity> {
        return this.map {
            InfoEntity(
                    senderId = it.senderId ?: "",
                    title = it.title,
                    description = it.description,
                    urlAccess = it.urlAccess,
                    urlImage = it.urlImage ?: "",
                    status = it.status,
                    topics = it.topics.mapStringToEntity(),
                    broadcastRequested = it.broadcastRequested
            )
        }
    }

    override fun List<InfoEntity>.mapEntityToGeneral(): List<Info> {
        return this.map {
            Info(
                senderId = it.senderId,
                title = it.title,
                description = it.description,
                urlAccess = it.urlAccess,
                urlImage = it.urlImage,
                status = it.status,
                topics = it.topics.mapEntityToGeneral(),
                broadcastRequested = it.broadcastRequested
            )
        }
    }

    override fun List<InfoEntity>.mapEntityToNet(): List<InfoNet> {
        return this.map {
            InfoNet(
                senderId = it.senderId,
                title = it.title,
                description = it.description,
                urlAccess = it.urlAccess,
                urlImage = it.urlImage,
                status = it.status,
                topics = it.topics.mapEntityToString(),
                broadcastRequested = it.broadcastRequested
            )
        }
    }
}