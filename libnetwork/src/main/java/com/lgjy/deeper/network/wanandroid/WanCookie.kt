package com.lgjy.deeper.network.wanandroid

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Created by LGJY on 2021/11/18.
 * Email：yujye@sina.com
 *
 * Cookie operations which WanAndroid required
 * see: https://www.wanandroid.com/blog/show/2
 */

class WanCookie : CookieJar {

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        // TODO: 2021/11/18  
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        // TODO: 2021/11/18  
    }
}
