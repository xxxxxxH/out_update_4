package com.xxxxxxh.lib.dilaog

import android.content.Context
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog
import com.xxxxxxh.lib.listener.CommonCallback

object DialogManager {
    fun permissionDialog(context: Context, callback: CommonCallback) {
        AwesomeInfoDialog(context)
            .setTitle("Permissions")
            .setMessage("App need updated,please turn on allow from this source tes")
            .setPositiveButtonText("ok")
            .setPositiveButtonClick {
                callback.permissionBtnClick()
            }.show()
    }

    fun updateDialog(context: Context,msg:String, callback: CommonCallback){
        AwesomeInfoDialog(context)
            .setTitle("Update new version")
            .setMessage(msg)
            .setPositiveButtonText("update")
            .setPositiveButtonClick {
                callback.updateBtnClick()
            }.show()
    }

    fun downloadDialog(context: Context):AwesomeProgressDialog{
       return AwesomeProgressDialog(context)
            .setTitle("Downloading")
            .setMessage("Please wait...")
    }
}