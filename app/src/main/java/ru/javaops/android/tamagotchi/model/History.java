package ru.javaops.android.tamagotchi.model;

import androidx.annotation.NonNull;

import java.util.Objects;

import ru.javaops.android.tamagotchi.enums.ActionType;

public class History {
    private long id;
    private long date;
    private ActionType actionType;
    private long petId;

    public History() {
    }

    public History(long date, ActionType actionType, long petId) {
        this.date = date;
        this.actionType = actionType;
        this.petId = petId;
    }

    public long getDate() {
        return date;
    }

    public long getPetId() {
        return petId;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return date == history.date &&
                petId == history.petId &&
                Objects.equals(actionType, history.actionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, actionType, petId);
    }

    @NonNull
    @Override
    public String toString() {
        return "History{" +
                "date=" + date +
                ", type=" + actionType +
                ", petId=" + petId +
                '}';
    }
}
