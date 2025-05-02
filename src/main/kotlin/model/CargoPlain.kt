package org.example.model

import org.example.model.builder.impl.CargoPlaneBuilderImpl
import org.example.model.builder.impl.PassengerAirplaneBuilderImpl
import org.springframework.data.mongodb.core.mapping.Document
import java.lang.Thread.sleep
import java.time.LocalDate

@Document("aircraft") // храним в коллекции aircraft
class CargoPlane( //Добавление самолетика грузового
    id:String? = null,
    model: String,
    capacity: Int,
    range: Double,
    fuelConsumption: Double,
    creationDate: LocalDate,
) : Aircraft(id, model, capacity, range, fuelConsumption, creationDate) {
    private val engine = Engine("V8", 100);

    override fun getAircraftInfo(): String {
        return "${super.getAircraftInfo()}"
    }


    override fun fly() {
        engine.start()
        sleep(1000)
        println("$model выполняет грузовой рейс на $range км.")
    }

    companion object{
        fun builder():CargoPlaneBuilderImpl = CargoPlaneBuilderImpl();
    }
}
