package ru.javaops.android.tamagotchi.dao;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import ru.javaops.android.tamagotchi.model.Pet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static ru.javaops.android.tamagotchi.data.PetTestData.getCreated;
import static ru.javaops.android.tamagotchi.data.PetTestData.getPets;
import static ru.javaops.android.tamagotchi.data.PetTestData.getUpdated;

public class PetDaoTest extends BaseDaoTest {

    @Test
    public void petDaoInsert() {
        Pet actual = getCreated();
        long id = petDao.insert(actual);
        List<Pet> dbAllPets = petDao.getAll();
        assertThat(dbAllPets, hasSize(4));
        actual.setId(id);
        assertThat(actual, is(petDao.findById(id)));
        assertThat(dbAllPets, hasItem(actual));
    }

    @Test
    public void petDaoFindById() {
        List<Pet> dbAllPets = petDao.getAll();
        Pet actual = dbAllPets.get(1);
        assertThat(actual, is(petDao.findById(actual.getId())));
    }

    @Test
    public void petDaoFindByIdNull() {
        Pet pet = petDao.findById(100);
        assertThat(pet, is(nullValue()));
    }

    @Test
    public void petDaoFindAny() {
        Pet pet = petDao.findAny();
        List<Pet> dbAllPets = petDao.getAll();
        assertThat(dbAllPets, hasItem(pet));
    }

    @Test
    public void petDaoFindAnyNull() {
        List<Pet> dbAllPets = petDao.getAll();
        petDao.delete(dbAllPets);
        Pet pet = petDao.findAny();
        assertThat(pet, is(nullValue()));
    }

    @Test
    public void petDaoGetAll() {
        List<Pet> petList = getPets();
        List<Pet> dbAllPets = petDao.getAll();
        for (int i = 0; i < petList.size(); i++) {
            petList.get(i).setId(dbAllPets.get(i).getId());
        }
        assertThat(petList, is(dbAllPets));
    }

    @Test
    public void petDaoDelete() {
        List<Pet> petList = petDao.getAll();
        Pet pet = petList.get(0);
        int count = petDao.delete(Collections.singletonList(pet));
        petList.remove(pet);
        List<Pet> dbAllPets = petDao.getAll();
        assertThat(count, is(1));
        assertThat(dbAllPets, hasSize(2));
        assertThat(petList, is(dbAllPets));
        assertThat(dbAllPets, hasItem(not(pet)));
    }

    @Test
    public void petDaoDeleteNotFound() {
        List<Pet> petList = petDao.getAll();
        Pet pet = getCreated();
        pet.setId(10);
        int count = petDao.delete(Collections.singletonList(pet));
        List<Pet> dbAllPets = petDao.getAll();
        assertThat(count, is(0));
        assertThat(dbAllPets, hasSize(3));
        assertThat(petList, is(dbAllPets));
    }

    @Test
    public void petDaoUpdate() {
        List<Pet> dbAllPets = petDao.getAll();
        Pet pet = getUpdated(dbAllPets.get(0));
        petDao.update(pet);
        dbAllPets = petDao.getAll();
        assertThat(dbAllPets, hasSize(3));
        assertThat(pet, is(dbAllPets.get(0)));
        assertThat(dbAllPets, hasItem(pet));
    }

    @Test
    public void petDaoUpdateNoFound() {
        Pet pet = getCreated();
        pet.setId(10);
        petDao.update(pet);
        List<Pet> dbAllPets = petDao.getAll();
        assertThat(dbAllPets, hasSize(3));
        assertThat(dbAllPets, hasItem(not(pet)));
    }

    @Override
    void populateDb() {
        List<Pet> pets = getPets();
        petDao.insert(pets.get(0));
        petDao.insert(pets.get(1));
        petDao.insert(pets.get(2));
    }
}
