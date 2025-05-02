package org.example.model

import org.example.model.builder.impl.PassengerAirplaneBuilderImpl
import org.springframework.data.mongodb.core.mapping.Document
import java.lang.Thread.sleep
import java.time.LocalDate

@Document("aircraft")
class PassengerAirplane( //для пассажирского самолета
    id: String? = null,
    model: String,
    capacity: Int,
    range: Double,
    fuelConsumption: Double,
    creationDate: LocalDate,
) : Aircraft(id, model, capacity, range, fuelConsumption, creationDate) {


    private val engine = Engine("V8", 100);
    override fun fly() {
        engine.start()
        sleep(1000)
        println("$model выполняет пассажирский рейс на $range км.")
    }

    companion object{
        fun builder() = PassengerAirplaneBuilderImpl();
    }
}