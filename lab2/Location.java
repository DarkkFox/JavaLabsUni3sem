package ru.izebit.second;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
@Data
@RequiredArgsConstructor
public class Location {
    /** X coordinate of this location. **/
    public final int xCoord;

    /** Y coordinate of this location. **/
    public final int yCoord;

    /** Creates a new location with coordinates (0, 0). **/
    public Location()
    {
        this(0, 0);
    }
}
