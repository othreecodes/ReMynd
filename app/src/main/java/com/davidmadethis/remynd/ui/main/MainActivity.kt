package com.davidmadethis.remynd.ui.main

import android.os.Bundle
import com.davidmadethis.remynd.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.io.IOUtils
import com.davidmadethis.remynd.data.local.dto.Class
import com.google.android.gms.auth.api.signin.GoogleSignIn
import ng.apk.instantemploy.ui.base.BaseActivity
import java.util.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.R.attr.data
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException


class MainActivity : BaseActivity(), MainContract.View {
    override var presenter: MainContract.Presenter = MainPresenter(this)
    val RC_SIGN_IN = 9090


    override fun initView() {
        val timetable = Gson().fromJson<ArrayList<Class>>(
                IOUtils.toString(assets.open("timetable.json"), "UTF-8"),
                object : TypeToken<ArrayList<Class>>() {}.type
        )

        if (timetable != null && timetable.isNotEmpty()) {
            pager.adapter = DaysPagerAdapter(timetable, supportFragmentManager)
            tabs.setupWithViewPager(pager)

            // Normalize day value - our adapter works with five days, the first day (0) being Monday.
            val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2
            pager.currentItem = if (today in 0..4) today else 0
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
//                .requestScopes()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent

        startActivityForResult(signInIntent, RC_SIGN_IN)


        presenter.start()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            showToast(account.displayName!!)
            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("err", "signInResult:failed code=" + e.statusCode)

        }

    }
}
