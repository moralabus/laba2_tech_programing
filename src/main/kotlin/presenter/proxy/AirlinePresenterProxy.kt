package org.example.presenter.proxy

import org.example.cache.SimpleCache
import org.example.model.Aircraft
import org.example.model.CargoPlane
import org.example.model.PassengerAirplane
import org.example.presenter.AirlinePresenter
import org.example.presenter.AirlinePresenterImpl
import org.example.repo.AircraftRepository
import org.example.view.AirlineView
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*
import javax.swing.JOptionPane

class AirlinePresenterProxy(
    private val view: AirlineView,
    private val airlinePresenter: AirlinePresenterImpl,
) : AirlinePresenter {
    private val cache = SimpleCache<String, Aircraft>();
    override fun addAircraft(
        model: String,
        capacity: Int,
        range: Double,
        fuelConsumption: Double,
        date: String,
        isCargo: Boolean
    ) {
        airlinePresenter.addAircraft(model, capacity, range, fuelConsumption, date, isCargo)
        val airplane = airlinePresenter.getAirplane(model, capacity, range, fuelConsumption, date, isCargo)
        cache[model] = airplane
    }

    override fun showFleet(): MutableList<Aircraft> {
        return airlinePresenter.showFleet()
    }

    override fun getAircraftInfo(model: String) {
        if(cache.containsKey(model)) {
            JOptionPane.showMessageDialog(
                view,
                cache[model],
                "Информация о самолете", JOptionPane.INFORMATION_MESSAGE
            )
        }
        else airlinePresenter.getAircraftInfo(model)
    }

    override fun filterByFuel(maxFuel: Double) {
        airlinePresenter.filterByFuel(maxFuel)
    }

    override fun sortFleetByRange() {
        airlinePresenter.sortFleetByRange()
    }

    override fun refuelAircraft(model: String, amount: Double): Aircraft {
        return airlinePresenter.refuelAircraft(model, amount)
    }

    override fun calculateAverageFuel() {
        airlinePresenter.calculateAverageFuel()
    }

    override fun deletePlane(model: String) {
        cache.remove(model)
        airlinePresenter.deletePlane(model)
    }
}