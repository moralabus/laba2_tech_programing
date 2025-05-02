package org.example.view

//Файл с кнопками всякими
import org.example.model.Aircraft
import org.example.presenter.AirlinePresenter
import org.example.presenter.AirlinePresenterImpl
import org.example.presenter.proxy.AirlinePresenterProxy
import org.example.repo.AircraftRepository
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import javax.swing.* // графический интерфейс

@Component
class AirlineView(
    private val repository: AircraftRepository
) : JFrame() {
    private val presenter: AirlinePresenter = AirlinePresenterProxy(this, AirlinePresenterImpl(repository, this))
    private val aircraftList = DefaultListModel<String>()
    private val list = JList(aircraftList)

    init {
        presenter.showFleet().forEach {
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
            JOptionPane.showMessageDialog(
                this,
                "$model добавлен как $aircraftType самолет.",
                "Добавление",
                JOptionPane.INFORMATION_MESSAGE
            )
        }
        add(addButton)

        val sortButton = JButton("Сортировать по дальности")
        sortButton.setBounds(220, 220, 200, 30)
        sortButton.addActionListener {
            presenter.sortFleetByRange()
        }
        add(sortButton)

        val filterButton = JButton("Фильтр по расходу топлива")
        filterButton.setBounds(20, 260, 200, 30)
        filterButton.addActionListener {
            val fuelMax = JOptionPane.showInputDialog(this, "Введите макс. расход топлива:").toDoubleOrNull()
                ?: return@addActionListener
            presenter.filterByFuel(fuelMax)

        }
        add(filterButton)

        val refuelButton = JButton("Заправить самолет")
        refuelButton.setBounds(240, 260, 180, 30)
        refuelButton.addActionListener {
            val selected = list.selectedValue ?: return@addActionListener
            val amount = JOptionPane.showInputDialog(this, "Сколько литров заправить?").toDoubleOrNull()
                ?: return@addActionListener
            presenter.refuelAircraft(selected, amount)
        }
        add(refuelButton)

        val infoButton = JButton("Показать инфо о самолете")
        infoButton.setBounds(20, 300, 200, 30)
        infoButton.addActionListener {
            val selected = list.selectedValue ?: return@addActionListener
            presenter.getAircraftInfo(selected)
        }
        add(infoButton)

        val avgFuelButton = JButton("Средний расход топлива")
        avgFuelButton.setBounds(240, 300, 200, 30)
        avgFuelButton.addActionListener {
            val avg = presenter.calculateAverageFuel()
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
                presenter.getAircraftInfo(searchQuery)
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

    fun updateFleetList(aircraft: List<Aircraft>) {
        aircraftList.clear()
        aircraft.forEach { aircraft -> aircraftList.addElement(aircraft.model) }
    }

}
