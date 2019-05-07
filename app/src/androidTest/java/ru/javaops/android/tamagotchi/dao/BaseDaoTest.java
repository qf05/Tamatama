package ru.javaops.android.tamagotchi.dao;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import ru.javaops.android.tamagotchi.db.DataBase;

@RunWith(AndroidJUnit4.class)
public abstract class BaseDaoTest {

    protected DataBase db;
    protected PetDao petDao;

    @Before
    public void createDb() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getContext(),
                DataBase.class)
                .build();
        petDao = db.petDao();

    }

    @After
    public void closeDb() {
        db.close();
    }
}
