package base.plane;

import java.io.Serial;
import java.io.Serializable;

public class Mriya extends Passenger implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    public Mriya(String name) {
        super.name = name;
        this.flightDist = 2500;
        this.fuelConsumption = 23;
        this.passCapacity = 100;
        this.cargoCapacity = 5;
    }
}
