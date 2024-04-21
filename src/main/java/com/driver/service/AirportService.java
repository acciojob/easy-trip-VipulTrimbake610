package com.driver.service;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service
public class AirportService {

    @Autowired
AirportRepository airportRepository;
    public String addAirport(Airport airport){
        return airportRepository.addAirport(airport);
    }
    public String addFlight(Flight flight){
        return airportRepository.addFlight(flight);
    }
    public String addPassenger(Passenger passenger){
        return airportRepository.addPassenger(passenger);
    }

    public String bookATicket(Integer flightId,Integer passengerId){
        return airportRepository.bookATicket(flightId,passengerId);
    }
    public String cancelATicket(Integer flightId,Integer passengerId){
        return airportRepository.cancelATicket(flightId,passengerId);
    }
    public String getLargestAirportName() {
        return airportRepository.getLargestAirportName();
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }
    public int getNumberOfPeopleOn(Date dateeques, String airportName){
        return airportRepository.getNumberOfPeopleOn(dateeques,airportName);
    }
    public int calculateFlightFare(Integer flightId){
        return airportRepository.calculateFlightFare(flightId);

    }
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){

        //Tell the count of flight bookings done by a passenger: This will tell the total count of flight bookings done by a passenger :
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }
}

