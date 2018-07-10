package ng.apk.instantemploy.ui.base

import android.annotation.SuppressLint
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import es.dmoral.toasty.Toasty


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    var dialog: MaterialDialog? = null

    fun showLoadingDialog(message: String) {
        dialog = MaterialDialog.Builder(this)
                .content(message)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show()
    }

    fun hideLoadingDialog(callback: () -> Unit) {
        if (dialog != null)
            dialog!!.dismiss()
        callback()

    }

    fun showToast(content: String, type: String = "info") {

        when (type) {
            "error" -> {
                Toasty.error(this, content, Toast.LENGTH_SHORT, true).show();

            }
            "info" -> {
                Toasty.info(this, content, Toast.LENGTH_SHORT, true).show();

            }
            "success" -> {
                Toasty.success(this, content, Toast.LENGTH_SHORT, true).show();

            }
            "warning" -> {
                Toasty.warning(this, content, Toast.LENGTH_SHORT, true).show();

            }
            "normal" -> {
                Toasty.normal(this, content, Toast.LENGTH_SHORT).show();

            }
            else -> {
                Toasty.normal(this, content, Toast.LENGTH_SHORT).show();

            }
        }
    }

    fun onErrorInConnection(callback: () -> Unit) {

        val mView: View = findViewById(android.R.id.content)
        val snackbar = Snackbar.make(mView, "There was an error reaching the server", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", { _ ->
                    callback()

                })
        snackbar.show()

    }

    fun finishActivity() {
        this.finish()
    }
}