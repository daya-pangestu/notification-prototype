package com.daya.notification_prototype.util.mapper

interface Mapper<General,Net,Entity> {

    fun List<General>.mapGeneralToNet() : List<Net>
    fun List<General>.mapGeneralToEntity(): List<Entity>

    fun List<Net>.mapNetToGeneral(): List<General>
    fun List<Net>.mapNetToEntity(): List<Entity>

    fun List<Entity>.mapEntityToGeneral(): List<General>
    fun List<Entity>.mapEntityToNet(): List<Net>
}