package lk.bsc212.pdsa.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QueenPlace {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String places;



    public QueenPlace(String places) {
        this.places = places;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }
}
