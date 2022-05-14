package lk.bsc212.pdsa.model;

public class PlacePredict {
    int cityName;
    int predictedDistance = 0;

    public PlacePredict(int cityName) {
        this.cityName = cityName;
    }

    public PlacePredict(int cityName, int predictedDistance) {
        this.cityName = cityName;
        this.predictedDistance = predictedDistance;
    }

    public int getCityName() {
        return cityName;
    }

    public void setCityName(int cityName) {
        this.cityName = cityName;
    }

    public int getPredictedDistance() {
        return predictedDistance;
    }

    public void setPredictedDistance(int predictedDistance) {
        this.predictedDistance = predictedDistance;
    }
}
