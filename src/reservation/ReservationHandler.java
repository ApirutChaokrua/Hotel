package reservation;

import Hotel.Customer;
import Hotel.Hotel;
import Hotel.CustomerDatabase;
import Hotel.OneDayHotel;
import reservation.room.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationHandler {
    public static void booking(Customer customer, int roomIndex, int currentFloorNum, int currentDay) {
        Customer databaseCustomer =  CustomerDatabase.customerDatabase.get(customer.getFirstName()+customer.getLastName());
        for (int i = 0; i < customer.getNightNum()+1; i++) {
            Room room = Hotel.hotel.get(currentDay-1+i).getFloors()[currentFloorNum-1].getRooms()[roomIndex];
            room.setCustomer(customer);
            room.setStatus("Reserved");
        }
        databaseCustomer.setTotalNightStay(databaseCustomer.getTotalNightStay()+customer.getNightNum());
        databaseCustomer.setTotalReserve(databaseCustomer.getTotalReserve()+1);
        /*System.out.println(databaseCustomer.getTotalReserve());
        System.out.println(databaseCustomer.getTotalRevenue());
        System.out.println(databaseCustomer.getTotalNightStay());
        System.out.println(databaseCustomer.getLastVisit());
        System.out.println("------------------------------");*/
    }

    public static void cancelBooking(int roomIndex, int currentFloorNum, int currentDay) {
        Customer customer = Hotel.hotel.get(currentDay-1).getFloors()[currentFloorNum-1].getRooms()[roomIndex].getCustomer();
        int index =0;
        for (OneDayHotel e:Hotel.hotel) {

            if(e.getDate().equals(customer.getCheckInDate())){
             for (int i = 0; i < customer.getNightNum()+1; i++) {
                Room room = Hotel.hotel.get(index + i).getFloors()[currentFloorNum - 1].getRooms()[roomIndex];
                room.setCustomer(null);
                room.setStatus("Vacant");
              }
                break;
            }
            index++;
        }

    }


    public static void cleaning(Room room,int cleanigTimeMin){
        room.setPreStatus(room.getStatus());
        room.setStatus("Cleaning");
        room.setCleaningTimeMinute(cleanigTimeMin);
        room.setCountDownClock(new CountDownClock(room));
    }
    public static void doneCleaning(Room room){
        room.setStatus(room.getPreStatus());
        room.getCountDownClock().skipCleaning();
    }
    public static void checkOut(int roomIndex, int currentFloorNum, int currentDay){
        Customer customer = Hotel.hotel.get(currentDay-1).getFloors()[currentFloorNum-1].getRooms()[roomIndex].getCustomer();
        CustomerDatabase.customerDatabase.get(customer.getFirstName()+customer.getLastName()).setLastVisit(LocalDateTime.now());
        int index =0;
        for (OneDayHotel e:Hotel.hotel) {
            if(e.getDate().equals(LocalDate.now())){
                for (int i = 0; i < customer.getCheckOutDate().getDayOfYear()-LocalDateTime.now().getDayOfYear()+1; i++) {
                    Room room = Hotel.hotel.get(index + i).getFloors()[currentFloorNum - 1].getRooms()[roomIndex];
                    room.setCustomer(null);
                    room.setStatus("Vacant");
                    room.setPreStatus("Vacant");
                }
            }
            index++;
        }
        Room room = Hotel.hotel.get(currentDay-1).getFloors()[currentFloorNum - 1].getRooms()[roomIndex];
        room.setCountDownClock(new CountDownClock(room));

    }
    public static void outOfService(Room room){
        room.setStatus("Out Of Service");
    }
    public static void cancleOutOfService(Room room){
        room.setStatus("Vacant");
    }
    public static void roomBlock(Room room){
        room.setStatus("Room Block");
    }
    public static void payment(Room room){
        Customer customer = room.getCustomer();
        customer.setPayment(true);
        Customer databaseCustomer =  CustomerDatabase.customerDatabase.get(customer.getFirstName()+customer.getLastName());
        databaseCustomer.setTotalRevenue(databaseCustomer.getTotalRevenue()+customer.getPaymerntPrice());
    }

    public static void checkIn(Customer customer, int roomIndex, int currentFloorNum, int currentDay){
        for (int i = 0; i < customer.getNightNum()+1; i++) {
            Room room = Hotel.hotel.get(currentDay-1+i).getFloors()[currentFloorNum-1].getRooms()[roomIndex];
            room.setStatus("In House");
            if(Hotel.hotel.get(currentDay-1+i).getDate().equals(customer.getCheckOutDate()))
                break;
        }

    }

    public static void setOutOfService (Room room){
        room.setStatus("OutOfService");
    }
}