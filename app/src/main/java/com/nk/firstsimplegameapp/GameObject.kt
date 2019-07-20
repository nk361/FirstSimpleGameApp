package com.nk.firstsimplegameapp

import android.graphics.Canvas

interface GameObject {
    fun draw(canvas: Canvas?)
    fun update()
}