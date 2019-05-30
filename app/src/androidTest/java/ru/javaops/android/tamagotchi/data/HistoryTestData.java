package ru.javaops.android.tamagotchi.data;

import java.util.ArrayList;
import java.util.List;

import ru.javaops.android.tamagotchi.enums.ActionType;
import ru.javaops.android.tamagotchi.model.History;

public class HistoryTestData {

    private static final History history1 = new History(1, ActionType.CREATE, 1);
    private static final History history2 = new History(2, ActionType.EAT, 1);
    private static final History history3 = new History(3, ActionType.SHIT, 1);
    private static final History history4 = new History(4, ActionType.SLEEP, 1);
    private static final History history5 = new History(2, ActionType.CREATE, 2);
    private static final History history6 = new History(3, ActionType.EAT, 2);

    public static List<History> getHistories() {
        List<History> histories = new ArrayList<>();
        histories.add(history1);
        histories.add(history2);
        histories.add(history3);
        histories.add(history4);
        histories.add(history5);
        histories.add(history6);
        for (int i = 0; i < histories.size(); ) {
            histories.get(i).setId(++i);
        }
        return histories;
    }

    public static History getCreated() {
        return new History(5, ActionType.CURE, 1);
    }
}
