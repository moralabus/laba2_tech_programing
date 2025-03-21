package org.example.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Document("aircraft")
abstract class Aircraft(
    @Id
    var id: String?,
    @Indexed(unique = true)
    var model: String,
    val capacity: Int,
    val range: Double,
    var fuelConsumption: Double, // Добавил расход топлива
    val creationDate: LocalDate,
) {
    var fuelLevel: Double = 0.0

    @JsonIgnore
    fun getAircraftAge(): Long {
        return ChronoUnit.YEARS.between(creationDate, LocalDate.now())
    }

    @JsonIgnore
    open fun getAircraftInfo(): String {
        return "$model: Вместимость: $capacity, Дальность: $range км, " +
                "Расход топлива: $fuelConsumption л/км, Возраст: ${getAircraftAge()} лет, топлива: $fuelLevel"
    }

    fun refuel(amount: Double) {
        fuelLevel += amount
        println("$model заправлен на $amount литров. Текущий уровень: $fuelLevel л.")
    }

    override fun toString(): String {
        return "$model, $capacity, $range, $fuelConsumption, $creationDate"
    }
    abstract fun fly()
}