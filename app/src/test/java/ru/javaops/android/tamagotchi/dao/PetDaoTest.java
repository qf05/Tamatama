package ru.javaops.android.tamagotchi.dao;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import ru.javaops.android.tamagotchi.data.PetTestData;
import ru.javaops.android.tamagotchi.model.Pet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class PetDaoTest extends BaseDaoTest {

    @Test
    public void petDaoInsert() {
        Pet actual = PetTestData.getCreated();
        long id = petDao.insert(actual);
        List<Pet> dbAllPets = getValue(petDao.getAll());
        assertThat(dbAllPets, hasSize(4));
        actual.setId(id);
        assertThat(actual, is(getValue(petDao.findById(id))));
        assertThat(dbAllPets, hasItem(actual));
    }

    @Test
    public void petDaoFindById() {
        List<Pet> dbAllPets = getValue(petDao.getAll());
        Pet actual = dbAllPets.get(1);
        assertThat(actual, is(getValue(petDao.findById(actual.getId()))));
    }

    @Test
    public void petDaoFindByIdNull() {
        Pet pet = getValue(petDao.findById(100));
        assertThat(pet, is(nullValue()));
    }

    @Test
    public void petDaoFindAny() {
        Pet pet = getValue(petDao.findAny());
        List<Pet> dbAllPets = getValue(petDao.getAll());
        assertThat(dbAllPets, hasItem(pet));
    }

    @Test
    public void petDaoFindAnyNull() {
        List<Pet> dbAllPets = getValue(petDao.getAll());
        petDao.delete(dbAllPets);
        Pet pet = getValue(petDao.findAny());
        assertThat(pet, is(nullValue()));
    }

    @Test
    public void petDaoGetAll() {
        List<Pet> petList = PetTestData.getPets();
        List<Pet> dbAllPets = getValue(petDao.getAll());
        for (int i = 0; i < petList.size(); i++) {
            petList.get(i).setId(dbAllPets.get(i).getId());
        }
        assertThat(petList, is(dbAllPets));
    }

    @Test
    public void petDaoDelete() {
        List<Pet> petList = getValue(petDao.getAll());
        Pet pet = petList.get(0);
        int count = petDao.delete(Collections.singletonList(pet));
        petList.remove(pet);
        List<Pet> dbAllPets = getValue(petDao.getAll());
        assertThat(count, is(1));
        assertThat(dbAllPets, hasSize(2));
        assertThat(petList, is(dbAllPets));
        assertThat(dbAllPets, hasItem(not(pet)));
    }

    @Test
    public void petDaoDeleteNotFound() {
        List<Pet> petList = getValue(petDao.getAll());
        Pet pet = PetTestData.getCreated();
        pet.setId(10);
        int count = petDao.delete(Collections.singletonList(pet));
        List<Pet> dbAllPets = getValue(petDao.getAll());
        assertThat(count, is(0));
        assertThat(dbAllPets, hasSize(3));
        assertThat(petList, is(dbAllPets));
    }

    @Test
    public void petDaoUpdate() {
        List<Pet> dbAllPets = getValue(petDao.getAll());
        Pet pet = PetTestData.getUpdated(dbAllPets.get(0));
        petDao.update(pet);
        dbAllPets = getValue(petDao.getAll());
        assertThat(dbAllPets, hasSize(3));
        assertThat(pet, is(dbAllPets.get(0)));
        assertThat(dbAllPets, hasItem(pet));
    }

    @Test
    public void petDaoUpdateNotFound() {
        Pet pet = PetTestData.getCreated();
        pet.setId(10);
        petDao.update(pet);
        List<Pet> dbAllPets = getValue(petDao.getAll());
        assertThat(dbAllPets, hasSize(3));
        assertThat(dbAllPets, hasItem(not(pet)));
    }

    @Override
    void populateDb() {
        List<Pet> pets = PetTestData.getPets();
        petDao.insert(pets.get(0));
        petDao.insert(pets.get(1));
        petDao.insert(pets.get(2));
    }
}
