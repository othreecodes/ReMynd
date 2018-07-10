package com.davidmadethis.remynd.util


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View



@Suppress("DEPRECATION")
class RecyclerTouchListener(context: Context?, recyclerView: RecyclerView, var clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {


    private var gestureDetector: GestureDetector

    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if (child != null && clickListener != null) {
                    clickListener!!.onLongClick(child, recyclerView.getChildPosition(child))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener!!.onClick(child, rv.getChildPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}