package org.example.repo

import org.example.model.Aircraft
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AircraftRepository: MongoRepository<Aircraft, String>{
    fun findAircraftByModel(model: String): Aircraft?
    fun findAllByFuelConsumptionLessThanEqual(fuelConsumptionIsLessThan: Double): MutableList<Aircraft>
    fun removeAircraftByModel(model: String)
}
