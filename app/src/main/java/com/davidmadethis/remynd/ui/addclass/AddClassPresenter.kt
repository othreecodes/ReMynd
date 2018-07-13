package com.davidmadethis.remynd.ui.addclass



class AddClassPresenter(val mView: AddClassContract.View) : AddClassContract.Presenter {
    override fun finalize() {

    }


    init {
        mView.presenter = this

    }


    override fun start() {
        mView.initView()
    }


}
