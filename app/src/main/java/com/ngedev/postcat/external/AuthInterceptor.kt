package com.ngedev.postcat.external

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.net.HttpURLConnection

class AuthInterceptor(private val sharedPreference: AppSharedPreference): Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = sharedPreference.fetchAccessToken()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val response = chain.proceed(newRequestWithAccessToken(accessToken, request))

        if(response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val newAccessToken = sharedPreference.getRefreshToken()
            if(newAccessToken != accessToken) {
                return chain.proceed(newRequestWithAccessToken(newAccessToken, request))
            }
        }


        return response
    }

    private fun newRequestWithAccessToken(accessToken: String?, request: Request): Request =
        request.newBuilder().header("Authorization", "Bearer $accessToken").build()
}