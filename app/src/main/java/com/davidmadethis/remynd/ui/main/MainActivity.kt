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
import android.view.Menu
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), MainContract.View {


    override var presenter: MainContract.Presenter = MainPresenter(this)
    val RC_SIGN_IN = 9090
    lateinit var mFirestore: FirebaseFirestore
    var account: GoogleSignInAccount? = null
    var userTimetable: ArrayList<Class> = ArrayList()

    override fun initView() {
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseApp.initializeApp(this)
        mFirestore = FirebaseFirestore.getInstance();


        val usertimetable = mFirestore.collection(account!!.id.toString())

        usertimetable.get().addOnSuccessListener {

            userTimetable.clear()
            showToast(it.documents[1].data.toString())
            it.documents.forEach {

                userTimetable.add(it.toObject(Class::class.java))
            }

            if (userTimetable.isNotEmpty()) {
                pager.adapter = DaysPagerAdapter(userTimetable, supportFragmentManager)
                tabs.setupWithViewPager(pager)

                // Normalize day value - our adapter works with five days, the first day (0) being Monday.
                val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2
                pager.currentItem = if (today in 0..4) today else 0
            }
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        account = GoogleSignIn.getLastSignedInAccount(this)

        if (account == null) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = mGoogleSignInClient.signInIntent

            startActivityForResult(signInIntent, RC_SIGN_IN)
        }


        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter.start()
    }


    override fun onStart() {
        super.onStart()
        initView()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
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

            showToast("You need to sign into your Google account to use this app.", "error")
            finish()

        }

    }
}
