package base.plane;

import java.io.Serial;
import java.io.Serializable;

public abstract class Plane implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    protected String name;
    protected int flightDist;
    protected int fuelConsumption;
    protected int cargoCapacity;
    protected int passCapacity;

    public int getFlightDist() {
        return flightDist;
    }

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public int getPassCapacity() {
        return passCapacity;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  "Name: " + name +
                ", Flight distance: " + flightDist +
                ", Fuel Consumption: " + fuelConsumption +
                ", Cargo Capacity: " + cargoCapacity +
                ", Passengers Capacity: " + passCapacity;
    }
}
