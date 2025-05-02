package org.example.model

import com.fasterxml.jackson.annotation.JsonIgnore // импорты аннотаций (тут JsonIgnore из jackson)
import org.example.model.builder.AircraftBuilder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Document("aircraft") // Аннотация Spring Data MongoDB, указывающая, что этот класс соответствует коллекции "aircraft" в MongoDB.
abstract class Aircraft(
    @Id
    var id: String?, // var - изменяемое поле, val - неизменяемое
    @Indexed(unique = true) // Аннотация Spring Data MongoDB, указывающая, что поле model должно быть проиндексировано в MongoDB и должно быть уникальным.  Это гарантирует, что в коллекции "aircraft" не будет двух самолетов с одинаковой моделью.
    var model: String,
    val capacity: Int,
    val range: Double,
    var fuelConsumption: Double,
    val creationDate: LocalDate,
): IFuelOperations{
    var fuelLevel: Double = 0.0

    @JsonIgnore  // Аннотация Jackson, указывающая, что метод getAircraftAge() должен быть проигнорирован при сериализации объекта Aircraft в JSON.  Это означает, что поле, вычисленное этим методом, не будет включено в JSON-представление объекта.
    fun getAircraftAge(): Long {
        return ChronoUnit.YEARS.between(creationDate, LocalDate.now()) // Возраст
    }

    @JsonIgnore
    open fun getAircraftInfo(): String { // open позволяет перреопределять
        return "$model: Вместимость: $capacity, Дальность: $range км, " +
                "Расход топлива: $fuelConsumption л/км, Возраст: ${getAircraftAge()} лет, топлива: $fuelLevel"
    }

    override fun refuel(amount: Double) {
        fuelLevel += amount
        println("$model заправлен на $amount литров. Текущий уровень: $fuelLevel л.")
    }

    override fun toString(): String {  // Переопределение метода toString() для представления объекта Aircraft в виде строки.
        return "$model, $capacity, $range, $fuelConsumption, $creationDate"
    }
    abstract fun fly() // абстрактный метод

}