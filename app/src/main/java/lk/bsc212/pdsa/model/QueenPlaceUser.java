package lk.bsc212.pdsa.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

public class QueenPlaceUser {

    @Embedded
    public QueenPlace queenPlace;
    @Relation(
            entityColumn = "userId",
            parentColumn = "placeId"
    )
    public User user;

    public QueenPlaceUser(QueenPlace queenPlace, User user) {
        this.queenPlace = queenPlace;
        this.user = user;
    }
}
