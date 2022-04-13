package lk.bsc212.pdsa.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

public class QueenPlaceUser {

//    @Embedded
//    public QueenPlace queenPlace ;
//    @Relation(
//
//            parentColumn = "placeId",
//            entityColumn = "answeredUserId"
//    )
//    public User user;

//    @Embedded
//    public User user;
//    @Relation(
//
//            parentColumn = "userId",
//            entityColumn = "answeredUserId"
//    )
//    public QueenPlace queenPlace;


    @Embedded
    public User user;
    @Relation(

            parentColumn = "userId",
            entityColumn = "answeredUserId"
    )
    public List<QueenPlace> queenPlace;


    //    public QueenPlaceUser(List<QueenPlace> queenPlace, User user) {
//        this.queenPlace = queenPlace;
//        this.user = user;
//    }
}
