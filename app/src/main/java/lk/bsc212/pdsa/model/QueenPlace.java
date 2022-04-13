package lk.bsc212.pdsa.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QueenPlace {

    @PrimaryKey(autoGenerate = true)
    public long placeId;

    public String places;

    public long answeredUserId;




    public QueenPlace() {
    }

    public QueenPlace(String places ) {
        this.places = places;
    }

    public QueenPlace(long placeId, String places, long answeredUserId) {
        this.placeId = placeId;
        this.places = places;
        this.answeredUserId = answeredUserId;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }
}
