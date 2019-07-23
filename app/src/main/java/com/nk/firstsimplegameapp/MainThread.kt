package com.nk.firstsimplegameapp

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class MainThread constructor(surfaceHolder: SurfaceHolder, gamePanel: GamePanel) : Thread() {
    companion object {//put all static things in a companion object
        val MAX_FPS: Int = 30//this frame rate is a guess for 2D games on most phones
        var canvas: Canvas? = null
    }
    private var averageFPS: Double
    private var surfaceHolder: SurfaceHolder
    private var gamePanel: GamePanel
    var running: Boolean = true//you cannot have public getters and setters on a private variable, but having getters and setters will require the info to go through them anyway
        get() = field
        set(value) {
            field = value
        }

    init {
        this.averageFPS = 0.0
        this.surfaceHolder = surfaceHolder
        this.gamePanel = gamePanel
        this.running = true
    }

    override fun run() {
        var startTime: Long
        var timeMillis: Long = 1_000 / MAX_FPS.toLong()
        var waitTime: Long
        var frameCount: Int = 0
        var totalTime: Long = 0
        var targetTime: Long = 1_000 / MAX_FPS.toLong()

        while(this.running) {
            startTime = System.nanoTime()//more precise than millisecond time, but more taxing on the phone
            canvas = null

            try {
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(this.surfaceHolder) {
                    this.gamePanel.update()
                    this.gamePanel.draw(canvas)
                }
            }
            catch(e: Exception) {
                e.printStackTrace()
            }
            finally {//this happens no matter what after a try or catch block happen
                if(canvas != null)
                    try {
                        this.surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                    catch(e: Exception) {
                        e.printStackTrace()
                    }
            }

            timeMillis = (System.nanoTime() - startTime) / 1_000_000//how much time it took
            waitTime = targetTime - timeMillis//how much time we should wait

            try {
                if(waitTime > 0) {
                    sleep(waitTime)
                }
            }
            catch(e: Exception) {
                e.printStackTrace()
            }

            totalTime += System.nanoTime() - startTime
            frameCount++

            if(frameCount == MAX_FPS) {
                this.averageFPS = 1_000 / (totalTime / frameCount / 1_000_000).toDouble()
                frameCount = 0
                totalTime = 0
                println(this.averageFPS)
            }
        }
    }
}