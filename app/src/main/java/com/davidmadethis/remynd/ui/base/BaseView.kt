package ng.apk.instantemploy.ui.base

import android.content.Context
import android.view.View

interface BaseView<T> {

//    var activity:Any
    var presenter:T
    fun initView()
    fun showLoadingDialog(message:String)
    fun hideLoadingDialog(callback:()->Unit)
    fun showToast(content:String,type:String="info")
    fun onErrorInConnection(callback: () -> Unit)
//    val content:Context
    fun finishActivity()
 }
