package com.xxxxxxh.lib.base

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.xxxxxxh.lib.utils.FaceBookManager
import com.xxxxxxh.lib.utils.GoogleManager

abstract class BaseActivity : AppCompatActivity() {

    protected var appLink: String? = null
    protected var installReferrer: String? = null
    var msgCount = 0

    private val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                msgCount++
                if (msgCount == 10) {
                    dismissLoadingDialog()
                    homeActivity()
                } else {
                    if (!TextUtils.isEmpty(appLink) && !TextUtils.isEmpty(installReferrer)) {
                        dismissLoadingDialog()
                        homeActivity()
                    } else {
                        sendEmptyMessageDelayed(1, 1000)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        requestPermissions()
    }

    private fun requestPermissions() {
        XXPermissions.with(this)
            .permission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    if (all){
                        showLoadingDialog()
                        appLink = FaceBookManager.getFacebookInfo(this@BaseActivity)
                        installReferrer = GoogleManager.getGoogleInfo(this@BaseActivity)
                        handler.sendEmptyMessage(1)
                    }
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    super.onDenied(permissions, never)
                    finish()
                }
            })
    }

    abstract fun getLayoutId(): Int

    abstract fun homeActivity()

    abstract fun showLoadingDialog()

    abstract fun dismissLoadingDialog()
}