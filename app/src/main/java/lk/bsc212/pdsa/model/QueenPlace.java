package lk.bsc212.pdsa.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QueenPlace {

    @PrimaryKey(autoGenerate = true)
    public int placeId;

    public String places;

//    public String placeAnswerUser;


    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public QueenPlace(String places) {
        this.places = places;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }
}
