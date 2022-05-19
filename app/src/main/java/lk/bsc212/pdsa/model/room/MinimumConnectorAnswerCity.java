package lk.bsc212.pdsa.model.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MinimumConnectorAnswerCity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long cityId;

    public long answerId;

    public MinimumConnectorAnswerCity(long cityId, long answerId) {
        this.cityId = cityId;
        this.answerId = answerId;
    }
}
