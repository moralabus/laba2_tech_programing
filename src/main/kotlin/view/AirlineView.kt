package org.example.view

import org.example.model.Aircraft
import org.example.presenter.AirlinePresenter
import org.example.service.AirlineService
import org.springframework.stereotype.Component
import javax.swing.*

@Component
class AirlineView(
    private val airlineService: AirlineService
) : JFrame() {
    private val presenter = AirlinePresenter(this, airlineService)
    private val aircraftList = DefaultListModel<String>()
    private val list = JList(aircraftList)

    init {
        airlineService.findAll().forEach {
            aircraftList.addElement(it.model)
        }

        title = "Авиакомпания - Управление флотом"
        setSize(700, 700)
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = null

        // Поля ввода
        val modelLabel = JLabel("Модель самолета:")
        modelLabel.setBounds(20, 20, 150, 30)
        add(modelLabel)

        val modelField = JTextField()
        modelField.setBounds(180, 20, 150, 30)
        add(modelField)

        val fuelLabel = JLabel("Расход топлива (л/км):")
        fuelLabel.setBounds(20, 60, 150, 30)
        add(fuelLabel)

        val fuelField = JTextField()
        fuelField.setBounds(180, 60, 150, 30)
        add(fuelField)

        val rangeLabel = JLabel("Дальность полета (км):")
        rangeLabel.setBounds(20, 100, 180, 30)
        add(rangeLabel)

        val rangeField = JTextField()
        rangeField.setBounds(180, 100, 150, 30)
        add(rangeField)

        val dateLabel = JLabel("Дата создания (YYYY-MM-DD):")
        dateLabel.setBounds(20, 140, 200, 30)
        add(dateLabel)

        val dateField = JTextField()
        dateField.setBounds(220, 140, 110, 30)
        add(dateField)

        // 🔘 Выбор типа самолета (грузовой / пассажирский)
        val aircraftTypeLabel = JLabel("Тип самолета:")
        aircraftTypeLabel.setBounds(20, 180, 120, 30)
        add(aircraftTypeLabel)

        val cargoRadioButton = JRadioButton("Грузовой")
        cargoRadioButton.setBounds(140, 180, 100, 30)
        add(cargoRadioButton)

        val passengerRadioButton = JRadioButton("Пассажирский", true) // По умолчанию выбран пассажирский
        passengerRadioButton.setBounds(250, 180, 130, 30)
        add(passengerRadioButton)

        val aircraftTypeGroup = ButtonGroup()
        aircraftTypeGroup.add(cargoRadioButton)
        aircraftTypeGroup.add(passengerRadioButton)

        // Кнопки
        val addButton = JButton("Добавить самолет")
        addButton.setBounds(20, 220, 180, 30)
        addButton.addActionListener {
            val model = modelField.text.trim()
            val fuel = fuelField.text.toDoubleOrNull() ?: 0.0
            val range = rangeField.text.toDoubleOrNull() ?: 0.0
            val date = dateField.text

            val isCargo = cargoRadioButton.isSelected

            presenter.addAircraft(model, 150, range, fuel, date, isCargo)

            modelField.text = ""
            fuelField.text = ""
            rangeField.text = ""
            dateField.text = ""

            val aircraftType = if (isCargo) "грузовой" else "пассажирский"
            JOptionPane.showMessageDialog(this, "$model добавлен как $aircraftType самолет.", "Добавление", JOptionPane.INFORMATION_MESSAGE)
        }
        add(addButton)

        val sortButton = JButton("Сортировать по дальности")
        sortButton.setBounds(220, 220, 200, 30)
        sortButton.addActionListener {
            val sorted = presenter.sortFleetByRange()
            aircraftList.clear()
            sorted.forEach { aircraftList.addElement(it.model) }
        }
        add(sortButton)

        val filterButton = JButton("Фильтр по расходу топлива")
        filterButton.setBounds(20, 260, 200, 30)
        filterButton.addActionListener {
            val fuelMax = JOptionPane.showInputDialog(this, "Введите макс. расход топлива:").toDoubleOrNull() ?: return@addActionListener
            val filtered = presenter.filterByFuel(fuelMax)
            aircraftList.clear()
            filtered.forEach { aircraftList.addElement(it.model) }
        }
        add(filterButton)

        val refuelButton = JButton("Заправить самолет")
        refuelButton.setBounds(240, 260, 180, 30)
        refuelButton.addActionListener {
            val selected = list.selectedValue ?: return@addActionListener
            val amount = JOptionPane.showInputDialog(this, "Сколько литров заправить?").toDoubleOrNull() ?: return@addActionListener
            presenter.refuelAircraft(selected, amount)
        }
        add(refuelButton)

        val infoButton = JButton("Показать инфо о самолете")
        infoButton.setBounds(20, 300, 200, 30)
        infoButton.addActionListener {
            val selected = list.selectedValue ?: return@addActionListener
            val info = presenter.getAircraftInfo(selected)
            JOptionPane.showMessageDialog(this, info, "Информация о самолете", JOptionPane.INFORMATION_MESSAGE)
        }
        add(infoButton)

        val avgFuelButton = JButton("Средний расход топлива")
        avgFuelButton.setBounds(240, 300, 200, 30)
        avgFuelButton.addActionListener {
            val avg = presenter.calculateAverageFuel()
            JOptionPane.showMessageDialog(this, "Средний расход топлива: $avg л/км", "Средний расход", JOptionPane.INFORMATION_MESSAGE)
        }
        add(avgFuelButton)

        // Поле для поиска самолета
        val searchLabel = JLabel("Поиск самолета:")
        searchLabel.setBounds(20, 340, 150, 30)
        add(searchLabel)

        val searchField = JTextField()
        searchField.setBounds(180, 340, 150, 30)
        add(searchField)

        val searchButton = JButton("Найти самолет")
        searchButton.setBounds(340, 340, 150, 30)
        searchButton.addActionListener {
            val searchQuery = searchField.text.trim()
            if (searchQuery.isNotEmpty()) {
                val result = presenter.getAircraftInfo(searchQuery)
                JOptionPane.showMessageDialog(this, result, "Результат поиска", JOptionPane.INFORMATION_MESSAGE)
            } else {
                JOptionPane.showMessageDialog(this, "Введите название самолета", "Ошибка", JOptionPane.WARNING_MESSAGE)
            }
        }
        add(searchButton)

        // Список самолетов
        val listScrollPane = JScrollPane(list)
        listScrollPane.setBounds(20, 380, 650, 200)
        add(listScrollPane)

        isVisible = true
    }

    fun updateFleetList(aircraft: Aircraft) {
        aircraftList.addElement(aircraft.model)
    }
}
