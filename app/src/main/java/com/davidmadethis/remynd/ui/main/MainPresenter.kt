package com.davidmadethis.remynd.ui.main

class MainPresenter(val mainView: MainContract.View) : MainContract.Presenter {
    override fun finalize() {

    }


    init {
        mainView.presenter = this

    }


    override fun start() {
        mainView.initView()
    }


}
