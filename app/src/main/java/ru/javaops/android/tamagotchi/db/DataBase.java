package ru.javaops.android.tamagotchi.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import ru.javaops.android.tamagotchi.dao.HistoryDao;
import ru.javaops.android.tamagotchi.dao.PetDao;
import ru.javaops.android.tamagotchi.db.converters.Converter;
import ru.javaops.android.tamagotchi.model.History;
import ru.javaops.android.tamagotchi.model.Pet;

// http://qaru.site/questions/152627/room-schema-export-directory-is-not-provided-to-the-annotation-processor-so-we-cannot-export-the-schema
@Database(entities = {Pet.class, History.class}, version = 2, exportSchema = false)
@TypeConverters(Converter.class)
public abstract class DataBase extends RoomDatabase {

    private static DataBase DB_INSTANCE;
    private static final Object LOCK = new Object();

    // http://qaru.site/questions/14467653/android-room-persistent-appdatabaseimpl-does-not-exist
    // https://startandroid.ru/ru/courses/dagger-2/27-course/architecture-components/540-urok-12-migracija-versij-bazy-dannyh.html
    public static DataBase getAppDatabase(Context context) {
        if (DB_INSTANCE == null) {
            synchronized (LOCK) {
                DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, "database-name")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return DB_INSTANCE;
    }

    public abstract PetDao petDao();

    public abstract HistoryDao historyDao();
}
