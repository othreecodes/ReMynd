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
import com.davidmadethis.remynd.ui.addclass.AddClassActivity
import com.davidmadethis.remynd.ui.signin.SigninActivity
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), MainContract.View {
    override fun checkUserAccount() {
        account = GoogleSignIn.getLastSignedInAccount(this)

        if (account == null) {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish()
        } else {

            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar)
            presenter.start()

        }
    }


    override var presenter: MainContract.Presenter = MainPresenter(this)

    lateinit var mFirestore: FirebaseFirestore
    var account: GoogleSignInAccount? = null
    var userTimetable: ArrayList<Class> = ArrayList()

    override fun initView() {

        showLoadingDialog("Please wait while we load your timetable...")
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseApp.initializeApp(this)
        mFirestore = FirebaseFirestore.getInstance();


        val usertimetable = mFirestore.collection(account!!.id.toString())

        usertimetable.get().addOnSuccessListener {
            hideLoadingDialog {

                userTimetable.clear()

                it.documents.forEach {

                    userTimetable.add(it.toObject(Class::class.java))
                }

                pager.adapter = DaysPagerAdapter(userTimetable, supportFragmentManager)
                tabs.setupWithViewPager(pager)

                val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2
                pager.currentItem = if (today in 0..4) today else 0
                if (userTimetable.isNotEmpty()) {

                } else {
                    showToast("You haven't added any classes yet. Click the + button to add some.")
                }

            }
        }




        fab.setOnClickListener {
            val intent: Intent = Intent(this, AddClassActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkUserAccount()
    }


    override fun onStart() {
        super.onStart()
//        checkUserAccount()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


}
