package base.plane;

import java.io.Serial;
import java.io.Serializable;

public class Airbus extends Cargo implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    public Airbus(String name) {
        super.name = name;
        this.flightDist = 2000;
        this.fuelConsumption = 21;
        this.cargoCapacity = 48;
        this.passCapacity = 0;
    }
}
