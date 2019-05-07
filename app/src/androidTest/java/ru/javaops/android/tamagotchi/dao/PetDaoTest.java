package ru.javaops.android.tamagotchi.dao;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import ru.javaops.android.tamagotchi.data.PetTestData;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;

import static org.junit.Assert.assertEquals;

public class PetDaoTest extends BaseDaoTest {

    @Test
    public void petDaoInsert() {
        List<Pet> pets = PetTestData.getPets();
        petDao.insert(pets.get(0));
        List<Pet> dbAllPets = petDao.getAll();
        pets.get(0).setId(dbAllPets.get(0).getId());
        assertEquals(1, dbAllPets.size());
        assertEquals(pets.get(0), dbAllPets.get(0));
    }

//    @Test
//    public void petDaoFindById() throws Exception {
//        List<Pet> pets = PetTestData.getPets();
//        petDao.insert(pets.get(0));
//        petDao.insert(pets.get(1));
//        List<Pet> dbAllPets = petDao.getAll();
//        long id = dbAllPets.get(1).getId();
//        Pet pet = petDao.findById(id);
//        pets.get(1).setId(id);
//        assertEquals(2, dbAllPets.size());
//        assertEquals(pets.get(1), pet);
//    }
//
//    @Test
//    public void petDaoFindByIdNull() throws Exception {
//        List<Pet> pets = PetTestData.getPets();
//        petDao.insert(pets.get(0));
//        petDao.insert(pets.get(1));
//        Pet pet = petDao.findById(10);
//        assertNull(pet);
//    }
//
//    @Test
//    public void petDaoFindAny() throws Exception {
//        List<Pet> pets = PetTestData.getPets();
//        petDao.insert(pets.get(0));
//        petDao.insert(pets.get(1));
//        Pet pet = petDao.findAny();
//        List<Pet> dbAllPets = petDao.getAll();
//        for (int i = 0; i < dbAllPets.size(); i++) {
//            pets.get(i).setId(dbAllPets.get(i).getId());
//        }
//        assertEquals(2, dbAllPets.size());
//        assertTrue(pets.contains(pet));
//    }
//
//    @Test
//    public void petDaoFindAnyNull() throws Exception {
//        Pet pet = petDao.findAny();
//        assertNull(pet);
//    }

    @Test
    public void petDaoGetAll() {
        List<Pet> pets = PetTestData.getPets();
        petDao.insert(pets.get(0));
        petDao.insert(pets.get(1));
        petDao.insert(pets.get(2));
        List<Pet> dbAllPets = petDao.getAll();
        for (int i = 0; i < dbAllPets.size(); i++) {
            pets.get(i).setId(dbAllPets.get(i).getId());
        }
        assertEquals(3, dbAllPets.size());
        assertEquals(pets, dbAllPets);
    }

    @Test
    public void petDaoGetAllNull() {
        List<Pet> dbAllPets = petDao.getAll();
        assertEquals(0, dbAllPets.size());
    }

    @Test
    public void petDaoDelete() {
        List<Pet> pets = PetTestData.getPets();
        petDao.insert(pets.get(0));
        petDao.insert(pets.get(1));
        List<Pet> dbAllPets = petDao.getAll();
        pets.get(0).setId(dbAllPets.get(0).getId());
        pets.get(1).setId(dbAllPets.get(1).getId());
        petDao.delete(Collections.singletonList(pets.get(0)));
        dbAllPets = petDao.getAll();
        assertEquals(1, dbAllPets.size());
        assertEquals(pets.get(1), dbAllPets.get(0));
    }

    @Test
    public void petDaoDeleteNotFound() {
        List<Pet> pets = PetTestData.getPets();
        petDao.insert(pets.get(0));
        petDao.insert(pets.get(1));
        petDao.delete(Collections.singletonList(pets.get(2)));
        List<Pet> dbAllPets = petDao.getAll();
        assertEquals(2, dbAllPets.size());
    }

    @Test
    public void petDaoUpdate() {
        List<Pet> pets = PetTestData.getPets();
        petDao.insert(pets.get(0));
        petDao.insert(pets.get(1));
        Pet pet = pets.get(0);
        List<Pet> dbAllPets = petDao.getAll();
        pet.setId(dbAllPets.get(0).getId());
        pet.setName("SuperCat");
        petDao.update(pet);
        dbAllPets = petDao.getAll();
        assertEquals(2, dbAllPets.size());
        assertEquals(pet, dbAllPets.get(0));
    }

    @Test
    public void petDaoUpdateNotFound() {
        List<Pet> pets = PetTestData.getPets();
        petDao.insert(pets.get(0));
        petDao.insert(pets.get(1));
        petDao.insert(pets.get(2));
        Pet pet = new Pet("SuperCat", PetsType.CAT);
        petDao.update(pet);
        List<Pet> dbAllPets = petDao.getAll();
        for (int i = 0; i < dbAllPets.size(); i++) {
            pets.get(i).setId(dbAllPets.get(i).getId());
        }
        assertEquals(3, dbAllPets.size());
        assertEquals(pets, dbAllPets);
    }
}
