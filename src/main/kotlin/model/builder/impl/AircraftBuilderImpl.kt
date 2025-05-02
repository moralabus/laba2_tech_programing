package org.example.model.builder.impl

import org.example.model.Aircraft
import org.example.model.builder.AircraftBuilder
import java.time.LocalDate
import java.util.*

abstract class AircraftBuilderImpl<T: Aircraft>: AircraftBuilder<T> {
    protected var cargoCapacity: Double = 0.0
    protected var capacity: Int = 0
    protected var id: String = UUID.randomUUID().toString()
    protected var creationDate: LocalDate = LocalDate.now()
    protected var range: Double = 0.0
    protected var fuelConsumption: Double = 0.0
    protected var model: String = "model"

    override fun setId(value: String): AircraftBuilder<T> {
        this.id = value
        return this
    }

    override fun setFuelConsumption(value: Double): AircraftBuilder<T> {
        this.fuelConsumption = value
        return this
    }

    override fun setCreationDate(value: LocalDate): AircraftBuilder<T> {
        this.creationDate = value
        return this
    }

    override fun setCapacity(value: Int): AircraftBuilder<T> {
        this.capacity = value
        return this
    }

    override fun setRange(value: Double): AircraftBuilder<T> {
        this.range = value
        return this
    }

    override fun setModel(value: String): AircraftBuilder<T> {
        this.model = value
        return this
    }
}