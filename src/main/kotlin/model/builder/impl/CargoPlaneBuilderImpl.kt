package org.example.model.builder.impl

import org.example.model.CargoPlane

class CargoPlaneBuilderImpl: AircraftBuilderImpl<CargoPlane>() {

    override fun getResult(): CargoPlane {
        return CargoPlane(
            id = id,
            creationDate = creationDate,
            range = range,
            fuelConsumption = fuelConsumption,
            model = model,
            capacity = capacity,
        )
    }
}