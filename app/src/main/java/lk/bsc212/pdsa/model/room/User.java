package lk.bsc212.pdsa.model.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public long userId;

    public String name;


    public User() {
    }

    public User(String name) {

        this.name = name;
    }

}
