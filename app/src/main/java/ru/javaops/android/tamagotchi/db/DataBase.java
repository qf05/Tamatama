package ru.javaops.android.tamagotchi.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ru.javaops.android.tamagotchi.dao.PetDao;
import ru.javaops.android.tamagotchi.model.Pet;

// http://qaru.site/questions/152627/room-schema-export-directory-is-not-provided-to-the-annotation-processor-so-we-cannot-export-the-schema
@Database(entities = {Pet.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    private static final Object OBJECT = new Object();

    private static volatile DataBase DB_INSTANCE;

    public abstract PetDao petDao();
// http://qaru.site/questions/14467653/android-room-persistent-appdatabaseimpl-does-not-exist

    // миграция базы
    // https://startandroid.ru/ru/courses/dagger-2/27-course/architecture-components/540-urok-12-migracija-versij-bazy-dannyh.html
    public static DataBase getAppDatabase(Context context) {
        DataBase localInstance = DB_INSTANCE;
        if (localInstance == null) {
            synchronized (OBJECT) {
                localInstance = DB_INSTANCE;
                if (localInstance == null) {
                    DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, "database-name")
                            .fallbackToDestructiveMigration()
                            .build();
                    //Room.inMemoryDatabaseBuilder(context.getApplicationContext(),AppDatabase.class).allowMainThreadQueries().build();
                }
            }
        }
        return DB_INSTANCE;
    }

    public static void destroyInstance() {
        if (DB_INSTANCE.isOpen()) {
            DB_INSTANCE.close();
        }
        DB_INSTANCE = null;
    }
}
