package com.nk.firstsimplegameapp

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception

class GamePanel(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    var thread: MainThread

    init {
        holder.addCallback(this)

        thread = MainThread(holder, this)

        focusable = 1//required minimum sdk of 26
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = MainThread(holder, this)

        thread.running = true

        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while(retry) {
            try {
                thread.running = false
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {//for touch inputs
        return super.onTouchEvent(event)
    }

    fun update() {

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }
}