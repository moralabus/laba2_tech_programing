package org.example.model

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("aircraft")
@TypeAlias("cargo")
class CargoPlane(
    id:String? = null,
    model: String,
    capacity: Int,
    range: Double,
    fuelConsumption: Double,
    creationDate: LocalDate,
    val cargoCapacity: Double,
) : Aircraft(id, model, capacity, range, fuelConsumption, creationDate) {
    private val engine = Engine("V8", 100);

    fun loadCargo(weight: Double) {
        if (weight > cargoCapacity) {
            println("Невозможно загрузить $weight тонн груза. Максимальная вместимость: $cargoCapacity тонн.")
        } else {
            println("Загружено $weight тонн груза в $model.")
        }
    }

    override fun getAircraftInfo(): String {
        return "${super.getAircraftInfo()}, Максимальная вместимость $cargoCapacity тонн"
    }


    override fun fly() {
        println("$model перевозит груз весом $cargoCapacity тонн на $range км.")
    }
}
