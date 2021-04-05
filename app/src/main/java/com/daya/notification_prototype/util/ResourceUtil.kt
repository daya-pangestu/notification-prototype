package com.daya.notification_prototype.util

import com.daya.notification_prototype.data.Resource

class ResourceUtil {

    fun Any.wrapWithResource() {
        this.let {
            Resource.success(it)
        }
    }
}