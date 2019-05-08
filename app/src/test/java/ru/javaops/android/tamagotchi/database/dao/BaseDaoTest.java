package ru.javaops.android.tamagotchi.database.dao;

import androidx.room.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import ru.javaops.android.tamagotchi.dao.HistoryDao;
import ru.javaops.android.tamagotchi.dao.PetDao;
import ru.javaops.android.tamagotchi.db.DataBase;

@RunWith(RobolectricTestRunner.class)
public abstract class BaseDaoTest {

    protected DataBase db;
    protected PetDao petDao;
    protected HistoryDao historyDao;

    @Before
    public void createDb() {
        db = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.systemContext,
                DataBase.class)
                .allowMainThreadQueries()
                .build();
        petDao = db.petDao();
        historyDao = db.historyDao();
    }

    @After
    public void closeDb() {
        db.close();
    }
}
