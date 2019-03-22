package ru.javaops.android.tamagotchi.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import ru.javaops.android.tamagotchi.dao.PetDao;
import ru.javaops.android.tamagotchi.db.converters.PetsTypeConverter;
import ru.javaops.android.tamagotchi.model.Pet;

// http://qaru.site/questions/152627/room-schema-export-directory-is-not-provided-to-the-annotation-processor-so-we-cannot-export-the-schema
@Database(entities = {Pet.class}, version = 1, exportSchema = false)
@TypeConverters(PetsTypeConverter.class)
public abstract class DataBase extends RoomDatabase {

    private static DataBase DB_INSTANCE;

    public abstract PetDao petDao();
// http://qaru.site/questions/14467653/android-room-persistent-appdatabaseimpl-does-not-exist

    // миграция базы
    // https://startandroid.ru/ru/courses/dagger-2/27-course/architecture-components/540-urok-12-migracija-versij-bazy-dannyh.html
    public static DataBase getAppDatabase(Context context) {
        if (DB_INSTANCE == null) {
            DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, "database-name")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
            //Room.inMemoryDatabaseBuilder(context.getApplicationContext(),AppDatabase.class).allowMainThreadQueries().build();
        }
        return DB_INSTANCE;
    }

//    public static void destroyInstance() {
//        if (DB_INSTANCE != null && DB_INSTANCE.isOpen()) {
//            DB_INSTANCE.close();
//        }
//        DB_INSTANCE = null;
//    }
}
