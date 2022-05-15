package lk.bsc212.pdsa.model.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MinimumConnectorAnswer {
    @PrimaryKey(autoGenerate = true)
    public long answerId;

    public long userId;

    public int systemSelectedCityName;

    public MinimumConnectorAnswer(long userId, int systemSelectedCityName) {
        this.userId = userId;
        this.systemSelectedCityName = systemSelectedCityName;
    }
}
