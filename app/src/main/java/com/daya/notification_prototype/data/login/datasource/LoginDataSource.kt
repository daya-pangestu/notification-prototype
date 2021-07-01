package com.daya.notification_prototype.data.login.datasource

import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.login.LoggedInUser
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource
@Inject
constructor(){

    fun login(username: String, password: String): Resource<LoggedInUser> {
        return try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            Resource.success(fakeUser)
        } catch (e: Throwable) {
            Resource.error("Error logging in ${e.localizedMessage}")
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}