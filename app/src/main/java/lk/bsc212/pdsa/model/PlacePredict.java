package lk.bsc212.pdsa.model;

public class PlacePredict {
    int cityName;
    double predictedDistance = 0.0;

    public PlacePredict(int cityName) {
        this.cityName = cityName;
    }

    public PlacePredict(int cityName, double predictedDistance) {
        this.cityName = cityName;
        this.predictedDistance = predictedDistance;
    }

    public int getCityName() {
        return cityName;
    }

    public void setCityName(int cityName) {
        this.cityName = cityName;
    }

    public double getPredictedDistance() {
        return predictedDistance;
    }

    public void setPredictedDistance(double predictedDistance) {
        this.predictedDistance = predictedDistance;
    }
}
