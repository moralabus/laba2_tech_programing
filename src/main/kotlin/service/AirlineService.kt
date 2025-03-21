package org.example.service

import org.example.model.Aircraft

interface AirlineService {
    fun addAircraft(aircraft: Aircraft)
    fun findAll(): List<Aircraft>
    fun showFleet()
    fun getAircraftInfo(model: String): String
    fun filterByFuelConsumption(maxFuelConsumption: Double): List<Aircraft>
    fun sortByRange(): List<Aircraft>
    fun refuelAircraft(model: String, amount: Double)
    fun removeAircraft(model: String)
    fun calculateAverageFuelConsumption(): Double
}