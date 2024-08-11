package base.plane;

import java.io.Serial;
import java.io.Serializable;

public class Boeing extends Passenger implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    public Boeing(String name) {
        super.name = name;
        this.flightDist = 1500;
        this.fuelConsumption = 18;
        this.passCapacity = 350;
        this.cargoCapacity = 15;
    }
}
