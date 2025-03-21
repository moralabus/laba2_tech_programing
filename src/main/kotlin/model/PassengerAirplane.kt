package org.example.model

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("aircraft")
@TypeAlias("airplane")
class PassengerAirplane(
    id: String? = null,
    model: String,
    capacity: Int,
    range: Double,
    fuelConsumption: Double, // Добавил расход топлива
    creationDate: LocalDate,
) : Aircraft(id, model, capacity, range, fuelConsumption, creationDate) {

    val engine = Engine("V8", 100);
    override fun fly() {
        println("$model выполняет пассажирский рейс на $range км.")
    }
}