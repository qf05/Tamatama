package ru.javaops.android.tamagotchi.model;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import ru.javaops.android.tamagotchi.enums.PetsType;

//http://qaru.site/questions/16042145/sqlite-requires-having-a-unique-constraint-android-room-annotations
@Entity(tableName = "pet",
        indices = {@Index(value = "id", unique = true)})
public class Pet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    @ColumnInfo(name = "pets_type")
    private PetsType petsType;

    private int lvl;
    private int hp;
    private int satiety;
    private int experience;

    @ColumnInfo(name = "is_live")
    private boolean isLive;

    @ColumnInfo(name = "is_ill")
    private boolean isIll;

    @ColumnInfo(name = "is_slip")
    private boolean isSlip;

    @ColumnInfo(name = "next_walk")
    private long nextWalk;

    @ColumnInfo(name = "next_sleep")
    private long nextSlip;

    @ColumnInfo(name = "next_shit")
    private long nextShit;

    @ColumnInfo(name = "wake_up")
    private long wakeUp;

    public Pet() {
    }

    @Ignore
    public Pet(String name, PetsType petsType) {
        this.name = name;
        this.petsType = petsType;
        this.lvl = 1;
        this.hp = 100;
        this.satiety = 90;
        this.experience = 0;
        this.isLive = true;
        this.isIll = false;
        this.isSlip = false;
        this.nextWalk = 0;
        this.nextSlip = 0;
        this.nextShit = 0;
        this.wakeUp = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetsType getPetsType() {
        return petsType;
    }

    public void setPetsType(PetsType petsType) {
        this.petsType = petsType;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        this.isLive = live;
    }

    public boolean isIll() {
        return isIll;
    }

    public void setIll(boolean ill) {
        isIll = ill;
    }

    public boolean isSlip() {
        return isSlip;
    }

    public void setSlip(boolean slip) {
        isSlip = slip;
    }

    public long getNextWalk() {
        return nextWalk;
    }

    public void setNextWalk(long nextWalk) {
        this.nextWalk = nextWalk;
    }

    public long getNextSlip() {
        return nextSlip;
    }

    public void setNextSlip(long nextSlip) {
        this.nextSlip = nextSlip;
    }

    public long getNextShit() {
        return nextShit;
    }

    public void setNextShit(long nextShit) {
        this.nextShit = nextShit;
    }

    public long getWakeUp() {
        return wakeUp;
    }

    public void setWakeUp(long wakeUp) {
        this.wakeUp = wakeUp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id &&
                lvl == pet.lvl &&
                hp == pet.hp &&
                satiety == pet.satiety &&
                experience == pet.experience &&
                isLive == pet.isLive &&
                isIll == pet.isIll &&
                isSlip == pet.isSlip &&
                nextWalk == pet.nextWalk &&
                nextSlip == pet.nextSlip &&
                nextShit == pet.nextShit &&
                wakeUp == pet.wakeUp &&
                Objects.equals(name, pet.name) &&
                petsType.equals(pet.petsType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, petsType, lvl, hp, satiety, experience, isLive, isIll, isSlip, nextWalk, nextSlip, nextShit, wakeUp);
    }

    @NonNull
    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + petsType +
                ", lvl=" + lvl +
                ", hp=" + hp +
                ", satiety=" + satiety +
                ", experience=" + experience +
                ", isLive=" + isLive +
                ", isIll=" + isIll +
                ", isSlip=" + isSlip +
                ", nextWalk=" + nextWalk +
                ", nextSlip=" + nextSlip +
                ", nextShit=" + nextShit +
                ", wakeUp=" + wakeUp +
                '}';
    }
}