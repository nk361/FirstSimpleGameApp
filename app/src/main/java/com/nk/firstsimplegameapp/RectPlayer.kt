package com.nk.firstsimplegameapp

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect

class RectPlayer constructor(rectangle: Rect, color: Int) : GameObject {
    var rectangle: Rect
    private var color: Int

    init {
        this.rectangle = rectangle
        this.color = color
    }

    override fun draw(canvas: Canvas?) {
        var paint: Paint = Paint()
        paint.color = this.color
        canvas?.drawRect(this.rectangle, paint)
    }

    override fun update() {

    }

    fun update(point: Point) {
        this.rectangle.set(point.x - this.rectangle.width() / 2, point.y - this.rectangle.height() / 2, point.x + this.rectangle.width() / 2, point.y + this.rectangle.height() / 2)
    }
}