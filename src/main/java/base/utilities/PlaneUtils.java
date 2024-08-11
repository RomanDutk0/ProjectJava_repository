package base.utilities;

import base.plane.*;

import java.util.Comparator;
import java.util.List;

public class PlaneUtils {
    public static Plane createPlane(String type, String name) {
        switch(type) {
            case ("Airbus") -> {
                return new Airbus(name);
            }
            case ("Boeing") -> {
                return new Boeing(name);
            }
            case ("Douglas") -> {
                return new Douglas(name);
            }
            case ("Mriya") -> {
                return new Mriya(name);
            }
            default -> {
                System.out.println("Incorrect type, created Airbus plane");
                return new Airbus(name);
            }
        }
    }

    public static void sortPlanesByDist(List<Plane> planes) {
        planes.sort(Comparator.comparing(Plane::getFlightDist));
    }
}
