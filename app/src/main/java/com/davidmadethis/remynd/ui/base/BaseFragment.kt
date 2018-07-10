package ng.apk.instantemploy.ui.base

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import es.dmoral.toasty.Toasty


open class BaseFragment:Fragment(){

    var dialog: MaterialDialog? = null

    fun showLoadingDialog(message: String) {
        dialog = MaterialDialog.Builder(context!!)
                .content(message)
                .progress(true,0)
                .progressIndeterminateStyle(false)
                .show()
    }

//    init {
//
//    }
    fun hideLoadingDialog(callback: () -> Unit) {
        if(dialog!=null)
            dialog!!.dismiss()
            callback()

    }

    fun showToast(content:String,type:String="info"){

        when(type){
            "error"->{
                Toasty.error(context!!, content, Toast.LENGTH_SHORT, true).show();

            }
            "info"->{
                Toasty.info(context!!, content, Toast.LENGTH_SHORT, true).show();

            }
            "success"->{
                Toasty.success(context!!, content, Toast.LENGTH_SHORT, true).show();

            }
            "warning"->{
                Toasty.warning(context!!, content, Toast.LENGTH_SHORT, true).show();

            }
            "normal"->{
                Toasty.normal(context!!, content, Toast.LENGTH_SHORT).show();

            }
            else->{
                Toasty.normal(context!!, content, Toast.LENGTH_SHORT).show();

            }
        }
    }
    fun onErrorInConnection(callback: () -> Unit){
         val mView:View = activity!!.findViewById(android.R.id.content)
        val snackbar = Snackbar.make(mView,"There was an error reaching the server", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", {
                    _->
                    callback()

                })
        snackbar.show()

    }

    fun finishActivity(){
        activity!!.finish()
    }
}