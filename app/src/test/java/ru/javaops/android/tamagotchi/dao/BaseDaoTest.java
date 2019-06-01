package ru.javaops.android.tamagotchi.dao;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import ru.javaops.android.tamagotchi.db.DataBase;

@RunWith(RobolectricTestRunner.class)
public abstract class BaseDaoTest {

    private DataBase db;
    PetDao petDao;
    HistoryDao historyDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getContext(),
                DataBase.class)
                .allowMainThreadQueries()
                .build();
        petDao = db.petDao();
        historyDao = db.historyDao();
        populateDb();
    }

    @After
    public void closeDb() {
        db.close();
    }

    abstract void populateDb();

    static <T> T getValue(final LiveData<T> liveData) {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //noinspection unchecked
        return (T) data[0];
    }
}
