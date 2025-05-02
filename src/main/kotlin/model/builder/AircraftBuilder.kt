package org.example.model.builder

import org.example.model.Aircraft
import org.example.model.PassengerAirplane
import java.time.LocalDate

interface AircraftBuilder<T: Aircraft> {
    fun getResult(): T
    fun setId(value: String): AircraftBuilder<T>
    fun setFuelConsumption(value: Double): AircraftBuilder<T>
    fun setCreationDate(value: LocalDate): AircraftBuilder<T>
    fun setCapacity(value: Int): AircraftBuilder<T>
    fun setRange(value: Double): AircraftBuilder<T>
    fun setModel(value: String): AircraftBuilder<T>
}