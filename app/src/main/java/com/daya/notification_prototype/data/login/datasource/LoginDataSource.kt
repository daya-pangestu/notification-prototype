package com.daya.notification_prototype.data.login.datasource

import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.login.LoggedInUser

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Resource<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Resource.success(fakeUser)
        } catch (e: Throwable) {
            return Resource.error("Error logging in ${e.localizedMessage}")
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}