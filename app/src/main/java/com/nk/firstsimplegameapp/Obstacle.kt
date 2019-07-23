package com.nk.firstsimplegameapp

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Obstacle constructor(rectHeight: Int, color: Int, currentX: Int, currentY: Int, playerGap: Int) : GameObject {
    var rectangle1: Rect
    var rectangle2: Rect
    private var color: Int

    init {
        rectangle1 = Rect(0, currentY, currentX, currentY + rectHeight)
        rectangle2 = Rect(currentX + playerGap, currentY, Constants.SCREEN_WIDTH, currentY + rectHeight)
        this.color = color
    }

    fun incrementY(amount: Float) {
        rectangle1.top += amount.toInt()
        rectangle1.bottom += amount.toInt()
        rectangle2.top += amount.toInt()
        rectangle2.bottom += amount.toInt()
    }

    fun playerCollide(player: RectPlayer) : Boolean {
        if(rectangle1.contains(player.rectangle.left, player.rectangle.top)
            || rectangle1.contains(player.rectangle.right, player.rectangle.top)
            || rectangle1.contains(player.rectangle.left, player.rectangle.bottom)
            || rectangle1.contains(player.rectangle.right, player.rectangle.bottom))
            return true
        if(rectangle2.contains(player.rectangle.left, player.rectangle.top)
            || rectangle2.contains(player.rectangle.right, player.rectangle.top)
            || rectangle2.contains(player.rectangle.left, player.rectangle.bottom)
            || rectangle2.contains(player.rectangle.right, player.rectangle.bottom))
            return true
        return false
    }

    override fun draw(canvas: Canvas?) {
        var paint: Paint = Paint()
        paint.color = this.color
        canvas?.drawRect(this.rectangle1, paint)
        canvas?.drawRect(this.rectangle2, paint)
    }

    override fun update() {

    }
}