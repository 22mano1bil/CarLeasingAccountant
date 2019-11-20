package de.chris.apps.cars.vehicle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import de.chris.apps.cars.entitiy.VehicleEntity;

public class Vehicle {
    private Handler dataHandler;

    private Vehicle(Handler dataHandler) {
        this.dataHandler = dataHandler;
    }

    Handler getDataHandler() {
        return dataHandler;
    }

    public void updateOdometer(int odometer) throws IOException {
        dataHandler.setOdometer(odometer);
    }

    public int getOdometer() throws IOException {
        return dataHandler.getOdometer();
    }

    public Data.CurrentOdometer getCurrentOdometer() throws IOException {
        return dataHandler.getCurrentOdometer();
    }

    public long getRemainingDays() throws IOException {
        return dataHandler.getData().getRemainingDays();
    }

    public int getRemainingDistance() throws IOException {
        return dataHandler.getData().getRemainingDistance();
    }

    public float getDistanceDifference() throws IOException {
        return dataHandler.getData().getDistanceDiff();
    }

    public float getDistancePerDay() throws IOException {
        return dataHandler.getData().getDistancePerDay();
    }

    public float getAverageDistancePerDay() throws IOException {
        return dataHandler.getData().getAverageDistancePerDay();
    }

    public long getPassedDays() throws IOException {
        return dataHandler.getData().getPassedDays();
    }

    public int getMaximumDistance() throws IOException {
        return dataHandler.getData().getMaximumDistance();
    }

    public LocalDate getPickupDate() throws IOException {
        return dataHandler.getData().getPickUpDay();
    }

    public LocalDate getReturnDate() throws IOException {
        return dataHandler.getData().getReturnDay();
    }

    public String getVin() throws IOException {
        return dataHandler.getJsonData().getVin();
    }

    public History[] getHistories() throws IOException {
        List<History> result = new ArrayList<>();

        History[] histories = dataHandler.getJsonData().getHistories();
        Arrays.stream(histories).sorted((h1, h2) -> h1.getDate().compareTo(h2.getDate()))
                .forEachOrdered(result::add);
        return result.toArray(new History[result.size()]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(dataHandler, vehicle.dataHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataHandler);
    }

    public static Vehicle addVehicle(String vin, LocalDate pickUpDay, LocalDate releaseDay,
            int maxDistance) throws IOException {
        return addVehicle(new JsonData(vin, new Data(pickUpDay, releaseDay, maxDistance)));
    }

    public static Vehicle addVehicle(JsonData data) throws IOException {
        Handler dataHandler = Handler.addNewVehicle(data);
        return new Vehicle(dataHandler);
    }

    public static Vehicle getVehicle(String vin) {
        return new Vehicle(Handler.getDataHandler(vin));
    }

    public static VehicleEntity[] listVehicles() {
        String[] vehicles = Handler.listVehicles();
        VehicleEntity[] result = new VehicleEntity[vehicles.length];
        for (int i = 0; i < vehicles.length; i++) {
            result[i] = new VehicleEntity(vehicles[i]);
        }
        return result;
    }
}
