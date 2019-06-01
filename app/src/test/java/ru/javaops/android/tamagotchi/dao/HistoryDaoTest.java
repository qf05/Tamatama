package ru.javaops.android.tamagotchi.dao;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.javaops.android.tamagotchi.data.PetTestData;
import ru.javaops.android.tamagotchi.enums.ActionType;
import ru.javaops.android.tamagotchi.model.History;
import ru.javaops.android.tamagotchi.model.Pet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static ru.javaops.android.tamagotchi.data.HistoryTestData.getCreated;
import static ru.javaops.android.tamagotchi.data.HistoryTestData.getHistories;

public class HistoryDaoTest extends BaseDaoTest {

    @Test
    public void historyDaoInsert() {
        History actual = getCreated();
        long id = historyDao.insert(actual);
        actual.setId(id);
        List<History> dbAllHistories = historyDao.getAll();
        assertThat(dbAllHistories, hasSize(7));
        assertThat(actual, is(getById(id)));
        assertThat(dbAllHistories, hasItem(actual));
    }

    @Test
    public void historyDaoDelete() {
        History history = getHistories().get(0);
        int count = historyDao.delete(Collections.singletonList(history));
        List<History> dbAllHistories = historyDao.getAll();
        assertThat(count, is(1));
        assertThat(dbAllHistories, hasSize(5));
        assertThat(dbAllHistories, hasItem(not(history)));
    }

    @Test
    public void historyDaoDeleteNotFound() {
        History history = getCreated();
        history.setId(10);
        historyDao.delete(Collections.singletonList(history));
        List<History> dbAllHistories = historyDao.getAll();
        assertThat(dbAllHistories, hasSize(6));
    }

    @Test
    public void historyDaoGetAllData() {
        List<History> histories = getHistories();
        List<Long> dbDates = historyDao.getAllDate();
        List<Long> testDates = new ArrayList<>();
        for (int i = 0; i < histories.size(); i++) {
            testDates.add(histories.get(i).getDate());
        }
        assertThat(dbDates, hasSize(6));
        assertThat(testDates, is(dbDates));
    }

    @Test
    public void historyDaoGetAll() {
        List<History> dbAllHistories = historyDao.getAll();
        assertThat(dbAllHistories, hasSize(6));
        assertThat(getHistories(), is(dbAllHistories));
    }

    @Test
    public void historyDaoGetByDate() {
        List<History> dbAllHistories = historyDao.getAllFromDate(2, 3);
        List<History> histories = getHistories();
        histories.remove(3);
        histories.remove(0);
        assertThat(histories, is(dbAllHistories));
    }

    @Test
    public void historyDaoGetByDateNull() {
        List<History> dbAllHistories = historyDao.getAllFromDate(20, 30);
        assertThat(dbAllHistories, emptyCollectionOf(History.class));
    }

    @Test
    public void historyDaoGetByAction() {
        List<History> histories = new ArrayList<>();
        histories.add(getHistories().get(0));
        histories.add(getHistories().get(4));

        List<History> dbAllHistories = historyDao.getAllFromAction(ActionType.CREATE);
        assertThat(dbAllHistories, hasSize(2));
        assertThat(histories, is(dbAllHistories));
    }

    @Test
    public void historyDaoGetByActionNull() {
        List<History> dbAllHistories = historyDao.getAllFromAction(ActionType.DIE);
        assertThat(dbAllHistories, emptyCollectionOf(History.class));
    }

    @Test
    public void historyDaoGetByPetId() {
        List<History> histories = getHistories();
        histories.remove(5);
        histories.remove(4);
        List<History> dbAllHistories = historyDao.getAllFromPetId(1);
        assertThat(dbAllHistories, hasSize(4));
        assertThat(histories, is(dbAllHistories));
    }

    @Test
    public void historyDaoGetByPetIdNull() {
        List<History> dbAllHistories = historyDao.getAllFromPetId(10);
        assertThat(dbAllHistories, emptyCollectionOf(History.class));
    }

    @Override
    void populateDb() {
        List<Pet> pets = PetTestData.getPets();
        petDao.insert(pets.get(0));
        petDao.insert(pets.get(1));
        List<History> histories = getHistories();
        for (int i = 0; i < histories.size(); i++) {
            historyDao.insert(histories.get(i));
        }
    }

    private History getById(long id) {
        List<History> dbAllHistories = historyDao.getAll();
        History history = null;
        for (int i = 0; i < dbAllHistories.size(); i++) {
            if (dbAllHistories.get(i).getId() == id) {
                history = dbAllHistories.get(i);
            }
        }
        return history;
    }
}
