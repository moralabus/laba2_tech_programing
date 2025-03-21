package org.example.model

class Engine(private val type: String, private val power: Int) {


    private var isRunning: Boolean = false

    fun start() {
        if (!isRunning) {
            isRunning = true
            println("Двигатель $type (мощность: $power HP) запущен.")
        } else {
            println("Двигатель $type уже работает.")
        }
    }

    fun stop() {
        if (isRunning) {
            isRunning = false
            println("Двигатель $type остановлен.")
        } else {
            println("Двигатель $type уже выключен.")
        }
    }
}