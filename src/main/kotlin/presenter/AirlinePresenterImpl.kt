package org.example.presenter

import org.example.view.AirlineView // класс с графикой
import org.example.model.PassengerAirplane
import org.example.model.Aircraft
import org.example.model.CargoPlane
import org.example.repo.AircraftRepository
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate // для работы с датами
import java.util.UUID // генерирует 16значные рандомные числа для айди (гарантированно уникальную строку)
import java.util.logging.Logger
import javax.swing.JOptionPane

open class AirlinePresenterImpl(
    private val aircraftRepository: AircraftRepository,
    private val view: AirlineView
) : AirlinePresenter {

    @Transactional
    override fun addAircraft(
        model: String,
        capacity: Int,
        range: Double,
        fuelConsumption: Double,
        date: String,
        isCargo: Boolean
    ) {

        val airplane = getAirplane(model, capacity, range, fuelConsumption, date, isCargo)
        aircraftRepository.save(airplane).also {
            logger.info("${airplane.model} добавлен во флот")
        }
        view.updateFleetList(airplane)
    }

    override fun showFleet(): MutableList<Aircraft> {
        return aircraftRepository.findAll().also {
            logger.info("Самолеты показаны")
        }
    }

    override fun getAircraftInfo(model: String){
        val info = aircraftRepository
            .findAircraftByModel(model)?.getAircraftInfo() ?:"Самолет $model не найден"
        JOptionPane.showMessageDialog(view, info, "Информация о самолете", JOptionPane.INFORMATION_MESSAGE)
    }

    override fun filterByFuel(maxFuel: Double) {
        val fleet = aircraftRepository.findAllByFuelConsumptionLessThanEqual(maxFuel).also {
            logger.info("Самолеты отфильтрованы")
        }
        view.updateFleetList(fleet)
    }

    override fun sortFleetByRange() {
        val fleet = aircraftRepository.findAll(Sort.by("range").descending())
        view.updateFleetList(fleet)
    }

    @Transactional
    override fun refuelAircraft(model: String, amount: Double): Aircraft {
        val plane = aircraftRepository.findAircraftByModel(model) ?: throw RuntimeException("Самолет $model не найден")
        plane.refuel(amount)
        return aircraftRepository.save(plane).also {
            logger.info("$model заправлен.")
        }
    }

    override fun calculateAverageFuel() {
        val fleet = aircraftRepository.findAll()
        val avg = if (fleet.isNotEmpty()) fleet.sumOf { it.fuelConsumption } / fleet.size else 0.0
        JOptionPane.showMessageDialog(view, "Средний расход топлива: $avg л/км", "Средний расход", JOptionPane.INFORMATION_MESSAGE)
    }

    @Transactional
    override fun deletePlane(model: String) {
        aircraftRepository.removeAircraftByModel(model).also {
            logger.info("самолет $model удален")
        }
    }

    fun getAirplane(
        model: String,
        capacity: Int,
        range: Double,
        fuelConsumption: Double,
        date: String,
        isCargo: Boolean
    ): Aircraft {
        val formattedModel = model.replaceFirstChar { it.uppercaseChar() }
        val creationDate = LocalDate.parse(date)
        return if (isCargo) CargoPlane.builder()
            .setId(UUID.randomUUID().toString())
            .setModel(formattedModel)
            .setCapacity(capacity)
            .setRange(range)
            .setFuelConsumption(fuelConsumption)
            .setCreationDate(creationDate)
            .getResult()
        else PassengerAirplane.builder()
            .setId(UUID.randomUUID().toString())
            .setModel(formattedModel)
            .setCapacity(capacity)
            .setRange(range)
            .setFuelConsumption(fuelConsumption)
            .setCreationDate(creationDate)
            .getResult()

    }

    companion object {
        private val logger = Logger.getLogger(this::class.java.name)
    }

}