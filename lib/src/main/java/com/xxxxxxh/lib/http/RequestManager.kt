package com.xxxxxxh.lib.http

import android.content.Context
import com.xxxxxxh.lib.listener.CommonCallback
import com.xxxxxxh.lib.utils.Constant
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x


object RequestManager {
    fun update(data: String, callback: CommonCallback) {
        val params = RequestParams(Constant.update_url)
        params.addQueryStringParameter("data", data)
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String?) {
                callback.onSuccess(result!!)
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                callback.onError(ex.toString())
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onFinished() {
            }
        })
    }
}