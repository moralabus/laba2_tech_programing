package org.example.presenter

import org.example.view.AirlineView
import org.example.model.PassengerAirplane
import org.example.model.Aircraft
import org.example.model.CargoPlane
import org.example.service.AirlineService
import java.time.LocalDate
import java.util.UUID

class AirlinePresenter(private val view: AirlineView, private val airline: AirlineService) {

    fun addAircraft(model: String, capacity: Int, range: Double, fuelConsumption: Double, date: String, isCargo: Boolean) {
        val formattedModel = model.replaceFirstChar { it.uppercaseChar() }
        val creationDate = LocalDate.parse(date)
        val passengerAirplane = if (isCargo) CargoPlane(UUID.randomUUID().toString(), formattedModel, capacity, range, fuelConsumption, creationDate, 100.0)
            else PassengerAirplane(UUID.randomUUID().toString(), formattedModel, capacity, range, fuelConsumption, creationDate)
        airline.addAircraft(passengerAirplane)
        view.updateFleetList(passengerAirplane)
    }

    fun showFleet() = airline.showFleet()

    fun getAircraftInfo(model: String): String = airline.getAircraftInfo(model)

    fun filterByFuel(maxFuel: Double): List<Aircraft> = airline.filterByFuelConsumption(maxFuel)

    fun sortFleetByRange(): List<Aircraft> = airline.sortByRange()

    fun refuelAircraft(model: String, amount: Double) = airline.refuelAircraft(model, amount)

    fun calculateAverageFuel(): Double = airline.calculateAverageFuelConsumption()

}