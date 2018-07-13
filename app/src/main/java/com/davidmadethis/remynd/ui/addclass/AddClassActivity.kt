package com.davidmadethis.remynd.ui.addclass

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.davidmadethis.remynd.R
import kotlinx.android.synthetic.main.activity_add_class.*
import ng.apk.instantemploy.ui.base.BaseActivity
import ng.apk.instantemploy.ui.base.BaseView

class AddClassActivity : BaseActivity(),AddClassContract.View {
    override var presenter: AddClassContract.Presenter = AddClassPresenter(this)
    override fun initView() {
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_class)

        setSupportActionBar(toolbar)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        supportActionBar!!.title = "Add Class to Timetable"

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
