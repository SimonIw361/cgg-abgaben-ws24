package cgg.a02;

import tools.*;

public interface Lichtquelle {
    Vec3 richtungLichtquelle(Vec3 x);

    Color intensitaet(Vec3 x);
}
