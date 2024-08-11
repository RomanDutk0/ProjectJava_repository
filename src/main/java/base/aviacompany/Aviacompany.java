package base.aviacompany;

import base.plane.Plane;
import base.utilities.PlaneUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aviacompany implements Serializable {
    protected List<Plane> planes = new ArrayList<>();
    private int totalCargoCap;
    private int totalPassCap;

    @Serial
    private static final long serialVersionUID = 1L;

    public void addPlane(Plane plane) {
        planes.add(plane);
        totalCargoCap += plane.getCargoCapacity();
        totalPassCap += plane.getPassCapacity();
    }

    public void printPlaneByName(String name) {
        for (Plane plane : this.planes) {
            if (plane.getName().equals(name)) {
                System.out.printf(
                        "Name: %s\n" +
                                "Type: %s\n" +
                                "Model: %s\n" +
                                "Passenger Capacity: %d\n" +
                                "Cargo Capacity: %d\n" +
                                "Flight Distance: %d\n" +
                                "Fuel Consumption: %d\n",
                        plane.getName(), plane.getClass().getSuperclass().getSimpleName(),
                        plane.getClass().getSimpleName(), plane.getPassCapacity(), plane.getCargoCapacity(),
                        plane.getFlightDist(), plane.getFuelConsumption()
                );
            }
        }
    }
    public void printPlanes() {
        if (planes.isEmpty()) {
            System.out.println("\t  This base.aviacompany have no planes!");
        } else {
            PlaneUtils.sortPlanesByDist(planes);
            for (int i = 0; i < planes.size(); i++) {
                System.out.printf(
                        "%d) Plane: %s, Flight Distance: %s\n",
                        i + 1, planes.get(i).getName(), planes.get(i).getFlightDist()
                );
            }
        }
    }
    public void deletePlaneByName(String name) {
        planes.removeIf(plane -> plane.getName().equals(name));
    }
    public void calcCapacities() {
        int tempCargo = 0;
        int tempPass = 0;
        for (Plane plane : planes) {
            tempCargo += plane.getCargoCapacity();
            tempPass += plane.getPassCapacity();
        }
        System.out.println("Total Cargo Capacity: " + tempCargo);
        System.out.println("Total Passengers Capacity: " + tempPass);
    }
    public void findPlaneByFuelCons(int minFuelCons, int maxFuelCons) {
        for (Plane plane : this.planes) {
            if (plane.getFuelConsumption() < maxFuelCons && plane.getFuelConsumption() > minFuelCons ) {
                System.out.printf(
                        "Name: %s\nFuel Consumption: %d\n",
                        plane.getName(), plane.getFuelConsumption()
                );
            }else{
                System.out.println("There`s is no base.plane with this interval!");
            }
        }
    }
    public void copyAviaCompany(Aviacompany other) {
        this.planes = other.planes;
    }
}
