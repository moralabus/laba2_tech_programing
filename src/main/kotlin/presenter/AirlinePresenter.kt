package org.example.presenter

import org.example.model.Aircraft

interface AirlinePresenter {
    fun addAircraft(
        model: String,
        capacity: Int,
        range: Double,
        fuelConsumption: Double,
        date: String,
        isCargo: Boolean
    )

    fun showFleet(): MutableList<Aircraft>
    fun getAircraftInfo(model: String)
    fun filterByFuel(maxFuel: Double)
    fun sortFleetByRange()
    fun refuelAircraft(model: String, amount: Double): Aircraft
    fun calculateAverageFuel()
    fun deletePlane(model: String)
}