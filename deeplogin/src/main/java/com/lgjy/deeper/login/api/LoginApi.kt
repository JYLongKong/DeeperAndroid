package com.lgjy.deeper.login.api

import com.lgjy.deeper.network.core.ApiResponse
import retrofit2.http.Field
import retrofit2.http.POST

/**
 * Created by LGJY on 2021/11/18.
 * Email：yujye@sina.com
 *
 * Api about processing of login
 */

interface LoginApi {

    /**
     * Logging in using a password
     */
    @POST("/user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): ApiResponse<String>

    /**
     * Register an account
     */
    @POST("/user/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiResponse<String>
}
