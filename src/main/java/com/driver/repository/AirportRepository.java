package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Repository
public class AirportRepository {

    HashMap<String, Airport> airportMap = new HashMap<>();
    HashMap<Integer, Flight> flightMap = new HashMap<>();;
    HashMap<Integer, Passenger> passengerMap = new HashMap<>();
    HashMap<Integer, HashSet<Integer>> ticketBookingMap = new HashMap<>();



    public String addAirport(Airport airport){
        //Simply add airport details to your database
        //Return a String message "SUCCESS"
        try{
            String AirportName = airport.getAirportName();
            airportMap.put(AirportName,airport);
            return "SUCCESS";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public String addPassenger(Passenger passenger){
        //Add a passenger to the database
        //And return a "SUCCESS" message if the passenger has been added successfully
        try{
            passengerMap.put(passenger.getPassengerId(),passenger);
            return "Success";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public String addFlight(Flight flight){
        //Return a "SUCCESS" message string after adding a flight.
        try{
            flightMap.put(flight.getFlightId(),flight);
            return "Success!";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String bookATicket(Integer flightId,Integer passengerId){
        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"
        if(ticketBookingMap.get(flightId).size() == flightMap.get(flightId).getMaxCapacity()){
            return "FAILURE";
        }else if(ticketBookingMap.get(flightId).contains(passengerId)){
            return "FAILURE";
        }else{
            if(ticketBookingMap.containsKey(flightId)){
                HashSet<Integer> hs = ticketBookingMap.get(flightId);
                hs.add(passengerId);
                ticketBookingMap.put(flightId,hs);
            }else{
                HashSet<Integer> hs = new HashSet<>();
                hs.add(passengerId);
                ticketBookingMap.put(flightId,hs);
            }
            return "SUCCESS";
        }
    }

    public String cancelATicket(Integer flightId,Integer passengerId){

        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId
        if(ticketBookingMap.containsKey(flightId)){
            if(ticketBookingMap.get(flightId).contains(passengerId)){
                ticketBookingMap.get(flightId).remove(passengerId);
                return "SUCCESS";
            }else{
                return "FAILURE";
            }
        }else{
            return "FAILURE";
        }
    }

    public String getLargestAirportName(){
        //Largest airport is in terms of terminals. 3 terminal airport is larger than 2 terminal airport
        //Incase of a tie return the Lexicographically smallest airportName
        int max = Integer.MIN_VALUE;
        String name = "";
        try{
            TreeMap<String,Airport> airTreeMap = new TreeMap<>(airportMap);
            for(String key : airTreeMap.keySet()){
                Airport TempAirPort = airTreeMap.get(key);
                if(TempAirPort.getNoOfTerminals() > max){
                    max = TempAirPort.getNoOfTerminals();
                    name = TempAirPort.getAirportName();
                }
            }
        }catch(Exception e){
            return e.getMessage();
        }
        return name;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        //Find the duration by finding the shortest flight that connects these 2 cities directly
        //If there is no direct flight between 2 cities return -1.
        double duration = Double.MAX_VALUE;
        for(int flightId : flightMap.keySet()){
            Flight tempFlight = flightMap.get(flightId);
            if(tempFlight.getFromCity().equals(fromCity) && tempFlight.getToCity().equals(toCity)){
                duration = Math.min(tempFlight.getDuration(),duration);
            }
        }
        if(duration == Double.MAX_VALUE){
            return -1;
        }
        return duration;
    }

    public int getNumberOfPeopleOn(Date dateeques, String airportName){
        //Calculate the total number of people who have flights on that day on a particular airport
        //This includes both the people who have come for a flight and who have landed on an airport after their flight

        City airportCity = airportMap.get(airportName).getCity();
        int people = 0;
            for (Flight flight : flightMap.values()){
                if(flight.getFlightDate().equals(dateeques) || flight.getFlightDate() == dateeques){
                    if(flight.getFromCity().equals(airportCity) || flight.getToCity().equals(airportCity)){
                        people += ticketBookingMap.get(flight.getFlightId()).size();
                    }
                }
            }
        return people;
    }
    public int calculateFlightFare(Integer flightId){
        //Calculation of flight prices is a function of number of people who have booked the flight already.
        //Price for any flight will be : 3000 + noOfPeopleWhoHaveAlreadyBooked*50
        //Suppose if 2 people have booked the flight already : the price of flight for the third person will be 3000 + 2*50 = 3100
        //This will not include the current person who is trying to book, he might also be just checking price

        int noOfPeopleWhoHaveAlreadyBooked = ticketBookingMap.get(flightId).size();
        int price = 3000 + noOfPeopleWhoHaveAlreadyBooked*50;
        return price;
    }
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){

        //Tell the count of flight bookings done by a passenger: This will tell the total count of flight bookings done by a passenger :
        int count  = 0;
        for(HashSet<Integer> hs : ticketBookingMap.values()){
            if(hs.contains(passengerId)){
                count++;
            }
        }
        return count;
    }


}
