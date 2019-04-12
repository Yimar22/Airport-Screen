package ui;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.Airport;
import model.Flight;

public class AirportScreenController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
    private BorderPane bpPane;

	@FXML
	private TableView<Flight> tv;

	@FXML
	private Label lbTime;

	@FXML
	private TextField tfNumberFlights;

	private ObservableList<Flight> list;

	private Airport airport;

	@FXML
	public void generateFlights(ActionEvent event) throws IOException {
		int flightsNumber = Integer.parseInt(tfNumberFlights.getText());
		airport.generateFlights(flightsNumber);
		ObservableList<Flight> list = obsevableFlights();
		tv.setItems(list);
	}

	@FXML
	public void initialize() throws IOException {
		airport = new Airport();
		airport.generateFlights(15);
		intializeTableView();
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void intializeTableView(){

		TableColumn<Flight, Date> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));


		TableColumn<Flight, String> timeColumn = new TableColumn<>("Time");
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("hour"));


		TableColumn<Flight, String> airlineColumn = new TableColumn<>("Airline");
		airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));


		TableColumn<Flight, String> flightColumn = new TableColumn<>("Flight Number");
		flightColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));;


		TableColumn<Flight, String> toColumn = new TableColumn<>("To");
		toColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));;


		TableColumn<Flight, String> gateColumn = new TableColumn<>("Gate");
		gateColumn.setCellValueFactory(new PropertyValueFactory<>("gate"));

		tv = new TableView<>();
		tv.setItems(obsevableFlights());
		tv.getColumns().addAll(dateColumn, timeColumn, airlineColumn, flightColumn, toColumn, gateColumn);
		bpPane.setCenter(tv);
	}

	public ObservableList<Flight> obsevableFlights(){

		list = FXCollections.observableArrayList();
		for (int i = 0; i < airport.getFlights().size(); i++) {
			list.add(airport.getFlights().get(i)); 
		}

		return list;
	}
	
	

}
