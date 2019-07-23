package com.nk.firstsimplegameapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception

class GamePanel(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    var thread: MainThread

    var player: RectPlayer
    var playerPoint: Point
    var obstacleManager: ObstacleManager

    init {
        this.holder.addCallback(this)

        this.thread = MainThread(this.holder, this)

        this.player = RectPlayer(Rect(100, 100, 100 + 100 / 10, 100 + 100 / 10), Color.rgb(255, 0, 0))
        this.playerPoint = Point(this.player.rectangle.left + this.player.rectangle.right / 2, this.player.rectangle.top + this.player.rectangle.bottom / 2)//center
        this.obstacleManager = ObstacleManager((this.player.rectangle.right - this.player.rectangle.left) * 2, ((this.player.rectangle.bottom - this.player.rectangle.top) * 3.5).toInt(), ((this.player.rectangle.bottom - this.player.rectangle.top) * 0.75).toInt(), Color.BLACK)
        //this.obstacleManager = ObstacleManager(200, 350, 75, Color.BLACK)

        this.focusable = 1//required minimum sdk of 26
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        this.thread = MainThread(holder, this)

        this.thread.running = true

        this.thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while(retry) {
            try {
                this.thread.running = false
                this.thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {//for touch inputs
        when(event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> this.playerPoint.set(event.getX().toInt(), event.getY().toInt())
        }


        return true//this is saying true to detecting touch input, if there is a time where you want to stop touch input, this needs to become false
        //return super.onTouchEvent(event)
    }

    fun update() {
        this.player.update(this.playerPoint)
        this.obstacleManager.update()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawColor(Color.WHITE)

        this.player.draw(canvas)
        this.obstacleManager.draw(canvas)
    }
}