package ru.javaops.android.tamagotchi.adapters;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;

public class PetDataBaseAdapter {

    private static final Object OBJECT = new Object();

    public static ExecutorService getService() {
        return service;
    }

    private static ExecutorService service;
    private DataBase db;

    private static volatile PetDataBaseAdapter instance;

    private PetDataBaseAdapter(Context context) {
        db = DataBase.getAppDatabase(context);
        service = Executors.newSingleThreadExecutor();
    }

    public static PetDataBaseAdapter getInstance(Context context) {
        PetDataBaseAdapter localInstance = instance;
        if (localInstance == null) {
            synchronized (OBJECT) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PetDataBaseAdapter(context.getApplicationContext());
                }
            }
        }
        return localInstance;
    }

    public List<Pet> getAll() {
        try {
            return service.submit(new Callable<List<Pet>>() {
                @Override
                public List<Pet> call() {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return db.petDao().getAll();
                }
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pet findById(final long id) {
        try {
            return service.submit(new Callable<Pet>() {
                @Override
                public Pet call() {
                    return db.petDao().findById(id);
                }
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long insert(final Pet pet) {
        try {
            return service.submit(new Callable<Long>() {
                @Override
                public Long call() {
                    return db.petDao().insert(pet);
                }
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(final Pet pet) {
        try {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    db.petDao().update(pet);
                }
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(final List<Pet> pets) {
        try {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    db.petDao().delete(pets);
                }
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
