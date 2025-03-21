package org.example.service

import org.example.model.Aircraft
import org.example.repo.AircraftRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class AirlineServiceImpl(
    @Value("\${airline.name}") private val name: String,
    private val aircraftRepository: AircraftRepository
): AirlineService {

    @Transactional
    override fun addAircraft(aircraft: Aircraft) {
        aircraftRepository.save(aircraft).also {
            logger.info("${aircraft.model} добавлен во флот $name")
        }
    }

    override fun findAll(): List<Aircraft> {
        return aircraftRepository.findAll();
    }

    override fun showFleet() {
        val fleet = aircraftRepository.findAll()
        if (fleet.isEmpty()) {
            logger.info("Флот авиакомпании $name пуст.")
            return
        }
        fleet.forEach { println(it.getAircraftInfo()) }.also {
            logger.info("Флот авиакомпании $name:")
        }
    }

    override fun getAircraftInfo(model: String): String {
        return aircraftRepository.findAircraftByModel(model)?.getAircraftInfo() ?: "Самолет $model не найден"
    }

    override fun filterByFuelConsumption(maxFuelConsumption: Double): List<Aircraft> {
        return aircraftRepository.findAllByFuelConsumptionLessThanEqual(maxFuelConsumption).also {
            logger.info("Самолеты отфильтрованы")
        }
    }

    override fun sortByRange(): List<Aircraft> {
        return aircraftRepository.findAll(Sort.by("range").descending())
    }

    @Transactional
    override fun refuelAircraft(model: String, amount: Double) {
        val plane = aircraftRepository.findAircraftByModel(model)?: return;
        plane.refuel(amount)
        aircraftRepository.save(plane).also {
            logger.info("$model заправлен.")
        }
    }

    @Transactional
    override fun removeAircraft(model: String) {
        aircraftRepository.removeAircraftByModel(model).also {
            logger.info("$model удален из флота.")
        }
    }

    override fun calculateAverageFuelConsumption(): Double {
        val fleet = aircraftRepository.findAll()
        return if (fleet.isNotEmpty()) fleet.sumOf { it.fuelConsumption } / fleet.size else 0.0
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}