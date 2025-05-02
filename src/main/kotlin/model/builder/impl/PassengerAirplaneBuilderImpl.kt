package org.example.model.builder.impl

import org.example.model.PassengerAirplane

class PassengerAirplaneBuilderImpl : AircraftBuilderImpl<PassengerAirplane>() {

    override fun getResult(): PassengerAirplane {
        return PassengerAirplane(
            id = id,
            model = model,
            fuelConsumption = fuelConsumption,
            creationDate = creationDate,
            range = range,
            capacity = capacity
        )
    }

}