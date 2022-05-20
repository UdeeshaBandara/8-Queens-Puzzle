package lk.bsc212.pdsa.room;

import static com.google.common.truth.Truth.assertThat;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswer;
import lk.bsc212.pdsa.model.room.CityDistanceShortestPath;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswerCity;
import lk.bsc212.pdsa.model.room.CityDistanceMinimumConnector;
import lk.bsc212.pdsa.model.room.MinimumConnectorAnswer;
import lk.bsc212.pdsa.model.room.QueenPlace;
import lk.bsc212.pdsa.room.dao.MinimumConnectorDao;
import lk.bsc212.pdsa.room.dao.QueenPlaceDao;
import lk.bsc212.pdsa.room.dao.ShortestPathDao;
import lk.bsc212.pdsa.utils.Queens;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest extends TestCase {

    public AppDatabase appDatabase;
    public QueenPlaceDao queenPlaceDao;
    public ShortestPathDao shortestPathDao;
    public MinimumConnectorDao minimumConnectorDao;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        appDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase.class).allowMainThreadQueries().build();

        queenPlaceDao = appDatabase.placeDao();
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void writeAndReadQueenPlaces() {


        QueenPlace queenPlace = new QueenPlace(1, "1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0", 12);
        queenPlaceDao.insertAll(queenPlace);
        List<QueenPlace> queenPlaces = queenPlaceDao.getQueenPlaces();
        assertThat(queenPlaces.stream().anyMatch(o -> o.places.equals(queenPlace.places) && o.placeId == queenPlace.placeId && o.answeredUserId == queenPlace.answeredUserId)).isTrue();

    }


    @Test
    public void checkAllPossibleSolutionsAreGenerated() {

        Queens.enumerate(8).forEach(place -> queenPlaceDao.insertAll(new QueenPlace(place)));

        assertThat(queenPlaceDao.combinationCount()).isEqualTo(92);

    }

    @Test
    public void shortestDistanceCheck() {

        ShortestDistanceAnswer shortdistance = new ShortestDistanceAnswer(12,9);
        shortestPathDao.insertAll(shortdistance);

        CityDistanceShortestPath cityDistanceShortestPath = new CityDistanceShortestPath(9,0,7,12);
        shortestPathDao.insertDistanceBetweenCities(cityDistanceShortestPath);

        ShortestDistanceAnswerCity shortestDistanceAnswerCity = new ShortestDistanceAnswerCity(0,7,12);
        shortestPathDao.insertShortestPaths(shortestDistanceAnswerCity);

        assertThat(cityDistanceShortestPath.equals(shortestDistanceAnswerCity)).isEqualTo(true);

    }

    @Test
    public void shortestDistanceInCorrect() {

        ShortestDistanceAnswer shortdistance = new ShortestDistanceAnswer(12,9);
        shortestPathDao.insertAll(shortdistance);

        CityDistanceShortestPath cityDistanceShortestPath = new CityDistanceShortestPath(9,0,7,12);
        shortestPathDao.insertDistanceBetweenCities(cityDistanceShortestPath);

        ShortestDistanceAnswerCity shortestDistanceAnswerCity = new ShortestDistanceAnswerCity(0,51,12);
        shortestPathDao.insertShortestPaths(shortestDistanceAnswerCity);

        assertThat(cityDistanceShortestPath.equals(shortestDistanceAnswerCity)).isFalse();

    }

    @Test
    public void minimumConnects(){
        MinimumConnectorAnswer minimumConnectorAnswer = new MinimumConnectorAnswer(0,2);
        long[] insertAll = minimumConnectorDao.insertAll(minimumConnectorAnswer);

        CityDistanceMinimumConnector cityDistanceMinimumConnector = new CityDistanceMinimumConnector(2,8,18,12);
        minimumConnectorDao.insertDistanceBetweenCities(cityDistanceMinimumConnector);

//        MinimumConnectorAnswerCity minimumConnectorAnswerCity = new MinimumConnectorAnswerCity(2,8,18,12);
////        minimumConnectorDao.insertShortestPaths(minimumConnectorAnswerCity);
//        long[] insertShortestPaths = minimumConnectorDao.insertAll(minimumConnectorAnswer);
//
//        assertThat(cityDistanceMinimumConnector.equals(minimumConnectorAnswerCity)).isEqualTo(true);
    }

    @Test
    public void minimumConnectsInCorrect(){
        MinimumConnectorAnswer minimumConnectorAnswer = new MinimumConnectorAnswer(0,2);
        long[] insertAll = minimumConnectorDao.insertAll(minimumConnectorAnswer);

        CityDistanceMinimumConnector cityDistanceMinimumConnector = new CityDistanceMinimumConnector(2,8,18,12);
        minimumConnectorDao.insertDistanceBetweenCities(cityDistanceMinimumConnector);

//        MinimumConnectorAnswerCity minimumConnectorAnswerCity = new MinimumConnectorAnswerCity(2,8,21,12);
////        minimumConnectorDao.insertShortestPaths(minimumConnectorAnswerCity);
//        long[] insertShortestPaths = minimumConnectorDao.insertAll(minimumConnectorAnswer);
//
//        assertThat(cityDistanceMinimumConnector.equals(minimumConnectorAnswerCity)).isEqualTo(false);
    }




}