package base.plane;

import java.io.Serial;
import java.io.Serializable;

public class Douglas extends Cargo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    public Douglas(String name) {
        super.name = name;
        this.flightDist = 3000;
        this.fuelConsumption = 15;
        this.cargoCapacity = 77;
        this.passCapacity = 0;
    }
}
