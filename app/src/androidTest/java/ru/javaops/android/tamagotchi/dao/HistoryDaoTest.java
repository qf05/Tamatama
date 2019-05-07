package ru.javaops.android.tamagotchi.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.javaops.android.tamagotchi.data.HistoryTestData;
import ru.javaops.android.tamagotchi.data.PetTestData;
import ru.javaops.android.tamagotchi.enums.ActionType;
import ru.javaops.android.tamagotchi.model.History;
import ru.javaops.android.tamagotchi.model.Pet;

import static org.junit.Assert.assertEquals;

public class HistoryDaoTest extends BaseDaoTest {

    @Before
    public void insertPet() {
        List<Pet> pets = PetTestData.getPets();
        petDao.insert(pets.get(0));
        petDao.insert(pets.get(1));
    }

    @Test
    public void historyDaoInsert() {
        List<History> histories = HistoryTestData.getHistories();
        historyDao.insert(histories.get(0));
        List<History> dbAllHistories = historyDao.getAll();
        histories.get(0).setId(dbAllHistories.get(0).getId());
        assertEquals(1, dbAllHistories.size());
        assertEquals(histories.get(0), dbAllHistories.get(0));
    }

    @Test
    public void historyDaoDelete() {
        List<History> histories = HistoryTestData.getHistories();
        historyDao.insert(histories.get(0));
        historyDao.insert(histories.get(1));
        List<History> dbAllHistories = historyDao.getAll();
        histories.get(0).setId(dbAllHistories.get(0).getId());
        histories.get(1).setId(dbAllHistories.get(1).getId());
        historyDao.delete(Collections.singletonList(histories.get(0)));
        dbAllHistories = historyDao.getAll();
        assertEquals(1, dbAllHistories.size());
        assertEquals(histories.get(1), dbAllHistories.get(0));
    }

    @Test
    public void historyDaoDeleteNotFound() {
        List<History> histories = HistoryTestData.getHistories();
        historyDao.insert(histories.get(0));
        historyDao.insert(histories.get(1));
        historyDao.delete(Collections.singletonList(histories.get(2)));
        List<History> dbAllHistories = historyDao.getAll();
        assertEquals(2, dbAllHistories.size());
    }

    @Test
    public void historyDaoGetAllData() {
        List<History> histories = HistoryTestData.getHistories();
        insertAll(histories);
        List<Long> dbDates = historyDao.getAllDate();
        List<Long> testDates = new ArrayList<>();
        for (int i = 0; i < histories.size(); i++) {
            testDates.add(histories.get(i).getDate());
        }
        assertEquals(6, dbDates.size());
        assertEquals(testDates, dbDates);
    }

    @Test
    public void historyDaoGetAll() {
        List<History> histories = HistoryTestData.getHistories();
        insertAll(histories);
        List<History> dbAllHistories = historyDao.getAll();
        for (int i = 0; i < dbAllHistories.size(); i++) {
            histories.get(i).setId(dbAllHistories.get(i).getId());
        }
        assertEquals(6, dbAllHistories.size());
        assertEquals(histories, dbAllHistories);
    }

    @Test
    public void historyDaoGetAllNull() {
        List<History> dbAllHistories = historyDao.getAll();
        assertEquals(0, dbAllHistories.size());
    }

    @Test
    public void historyDaoGetByDate() {
        List<History> histories = HistoryTestData.getHistories();
        insertAll(histories);
        List<History> dbAllHistories = historyDao.getAllFromDate(2, 3);
        List<History> dbAllHistoriesAll = historyDao.getAll();
        List<History> resultHistories = new ArrayList<>();
        for (int i = 0; i < dbAllHistoriesAll.size(); i++) {
            if (histories.get(i).getDate() >= 2 && histories.get(i).getDate() <= 3) {
                histories.get(i).setId(dbAllHistoriesAll.get(i).getId());
                resultHistories.add(histories.get(i));
            }
        }
        assertEquals(4, dbAllHistories.size());
        assertEquals(resultHistories, dbAllHistories);
    }

    @Test
    public void historyDaoGetByDateNull() {
        List<History> histories = HistoryTestData.getHistories();
        insertAll(histories);
        List<History> dbAllHistories = historyDao.getAllFromDate(20, 30);
        assertEquals(0, dbAllHistories.size());
    }

    @Test
    public void historyDaoGetByAction() {
        List<History> histories = HistoryTestData.getHistories();
        insertAll(histories);
        List<History> dbAllHistories = historyDao.getAllFromAction(ActionType.CREATE);
        List<History> dbAllHistoriesAll = historyDao.getAll();
        List<History> resultHistories = new ArrayList<>();
        for (int i = 0; i < dbAllHistoriesAll.size(); i++) {
            if (ActionType.CREATE.equals(histories.get(i).getActionType())) {
                histories.get(i).setId(dbAllHistoriesAll.get(i).getId());
                resultHistories.add(histories.get(i));
            }
        }
        assertEquals(2, dbAllHistories.size());
        assertEquals(resultHistories, dbAllHistories);
    }

    @Test
    public void historyDaoGetByActionNull() {
        List<History> histories = HistoryTestData.getHistories();
        insertAll(histories);
        List<History> dbAllHistories = historyDao.getAllFromAction(ActionType.DIE);
        assertEquals(0, dbAllHistories.size());
    }

    @Test
    public void historyDaoGetByPetId() {
        List<History> histories = HistoryTestData.getHistories();
        insertAll(histories);
        List<History> dbAllHistories = historyDao.getAllFromPetId(2);
        List<History> dbAllHistoriesAll = historyDao.getAll();
        List<History> resultHistories = new ArrayList<>();
        for (int i = 0; i < dbAllHistoriesAll.size(); i++) {
            if (histories.get(i).getPetId() == 2) {
                histories.get(i).setId(dbAllHistoriesAll.get(i).getId());
                resultHistories.add(histories.get(i));
            }
        }
        assertEquals(2, dbAllHistories.size());
        assertEquals(resultHistories, dbAllHistories);
    }

    @Test
    public void historyDaoGetByPetIdNull() {
        List<History> histories = HistoryTestData.getHistories();
        insertAll(histories);
        List<History> dbAllHistories = historyDao.getAllFromPetId(10);
        assertEquals(0, dbAllHistories.size());
    }

    private void insertAll(List<History> histories) {
        historyDao.insert(histories.get(0));
        historyDao.insert(histories.get(1));
        historyDao.insert(histories.get(2));
        historyDao.insert(histories.get(3));
        historyDao.insert(histories.get(4));
        historyDao.insert(histories.get(5));
    }
}
