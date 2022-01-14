package com.xxxxxxh.lib.download

import android.os.Environment
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.xxxxxxh.lib.listener.CommonCallback
import java.io.File

object DownloadManager {
    fun download(url: String, callback: CommonCallback) {
        FileDownloader.getImpl().create(url)
            .setPath(
                Environment.getExternalStorageDirectory().toString() + File.separator + "a.apk"
            )
            .setListener(object : FileDownloadListener() {
                override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                }

                override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                }

                override fun completed(task: BaseDownloadTask?) {
                    callback.downloadSuccess(task!!.path)
                }

                override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                }

                override fun error(task: BaseDownloadTask?, e: Throwable?) {
                    callback.downloadError(e.toString())
                }

                override fun warn(task: BaseDownloadTask?) {

                }

            }).start()

    }
}