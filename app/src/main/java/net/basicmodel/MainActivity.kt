package net.basicmodel

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog
import com.sdsmdg.tastytoast.TastyToast
import com.xxxxxxh.lib.dilaog.DialogManager
import com.xxxxxxh.lib.download.DownloadManager
import com.xxxxxxh.lib.http.RequestManager
import com.xxxxxxh.lib.listener.CommonCallback
import com.xxxxxxh.lib.utils.SystemUtils
import net.utils.AesEncryptUtil
import net.utils.ResultEntity
import net.utils.Utils

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), CommonCallback {
    private var entity: ResultEntity? = null
    private var downloadDialog: AwesomeProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RequestManager.update(JSON.toJSONString(Utils.getRequestData(this)), this)
    }


    override fun onSuccess(response: String) {
        val result = AesEncryptUtil.decrypt(response)
        entity = JSON.parseObject(result, ResultEntity::class.java)
        if (Build.VERSION.SDK_INT > 24) {
            if (!this.packageManager.canRequestPackageInstalls()) {
                DialogManager.permissionDialog(this, this)
            } else {
                DialogManager.updateDialog(this, entity!!.ikey, this)
            }
        } else {
            DialogManager.updateDialog(this, entity!!.ikey, this)
        }
    }

    override fun onError(e: String) {
        TastyToast.makeText(this, e, TastyToast.LENGTH_LONG, TastyToast.ERROR)
    }

    override fun permissionBtnClick() {
        if (!this.packageManager.canRequestPackageInstalls()) {
            SystemUtils.allowThirdInstall(this, this)
        } else {
            DialogManager.updateDialog(this, entity!!.ikey, this)
        }
    }

    override fun updateBtnClick() {
        downloadDialog = DialogManager.downloadDialog(this)
        downloadDialog?.show()
        DownloadManager.download(entity!!.path, this)
    }

    override fun downloadError(e: String) {
        if (downloadDialog != null)
            downloadDialog!!.hide()
        TastyToast.makeText(this, e, TastyToast.LENGTH_LONG, TastyToast.ERROR)
    }

    override fun downloadSuccess(path: String) {
        if (downloadDialog != null)
            downloadDialog!!.hide()
        TastyToast.makeText(this, "saved in $path", TastyToast.LENGTH_LONG, TastyToast.SUCCESS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (!this.packageManager.canRequestPackageInstalls()) {
                DialogManager.permissionDialog(this, this)
            } else {
                DialogManager.updateDialog(this, entity!!.ikey, this)
            }
        }
    }
}