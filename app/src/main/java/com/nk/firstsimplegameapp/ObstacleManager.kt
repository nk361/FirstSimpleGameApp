package com.nk.firstsimplegameapp

import android.graphics.Canvas

class ObstacleManager constructor(playerGap: Int, obstacleGap: Int, obstacleHeight: Int, color: Int) {
    private var obstacles: ArrayList<Obstacle>
    private var playerGap: Int
    private var obstacleGap: Int
    private var obstacleHeight: Int
    private var color: Int

    private var startTime: Long

    init {
        this.playerGap = playerGap
        this.obstacleGap = obstacleGap
        this.obstacleHeight = obstacleHeight
        this.color = color

        this.startTime = System.currentTimeMillis()

        obstacles = ArrayList()

        populateObstacles()
    }

    private fun populateObstacles() {
        //higher obstacle index = lower on the screen = lower y value (negative)
        var currentY = -5 * Constants.SCREEN_HEIGHT / 4//making them start 5/4ths the size of the screen height
        while(currentY < 0/*obstacles[obstacles.size - 1].rectangle1.bottom < 0*/)//hasn't gotten onto the screen yet, keep generating
        {
            var currentX = (Math.random() * Constants.SCREEN_WIDTH - playerGap).toInt()
            obstacles.add(Obstacle(obstacleHeight, color, currentX, currentY, playerGap))

            currentY += obstacleHeight + obstacleGap
        }
    }

    fun update() {
        //add to the y value depending on time, not framerate, called framerate independent
        var elapsedTime: Int = (System.currentTimeMillis() - startTime).toInt()
        startTime = System.currentTimeMillis()

        var step: Float = Constants.SCREEN_HEIGHT / 10_000.toFloat()//one ten thousandth of the screen height, it is important that this 10k is a float
        for(ob: Obstacle in obstacles)
            ob.incrementY(step * elapsedTime)
        
        if(obstacles[obstacles.size - 1].rectangle1.top >= Constants.SCREEN_HEIGHT) {//if the current obstacle's rectangles have gone off screen, generate a new one and remove the old one
            var currentX = (Math.random() * Constants.SCREEN_WIDTH - playerGap).toInt()
            obstacles.add(0, Obstacle(obstacleHeight, color, currentX, obstacles[0].rectangle1.top - obstacleGap - obstacleHeight, playerGap))//adds a new obstacle with consideration to the obstacle gap above the current highest and the height of the obstacle
            obstacles.removeAt(obstacles.size - 1)//remove the obstacle that is off screen
        }
    }

    fun draw(canvas: Canvas?) {
        for(ob: Obstacle in obstacles)
            ob.draw(canvas)
    }
}