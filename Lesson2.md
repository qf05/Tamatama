
<img src="https://cdn1.savepice.ru/uploads/2019/2/10/99d546adac94ae499204b58108b3805b-full.jpg"/>

# Курс по разработке приложения для Android: Tamagotchi

---

## [Материалы занятия]()

---

# ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW_1

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. Позиционирование View, WalkActivity

- Применить HW_1_0_add_resources.patch (apply + commit)
- Применить HW_1_1_buttons.patch (apply + commit)
- Применить HW_1_2_walk_activity_and_animation.patch (apply + commit)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. Rotate, ObjectAnimator, TouchListener

- Применить HW_1_3_optional_rotate.patch (apply + commit)
- Применить HW_1_4_optional_sound_on_click.patch (apply + commit)
- Применить HW_1_5_on_touch_listener.patch (apply + commit)

---


#Lesson 2

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. Меню

- Применить 2_0_lifecycle_and_create_model.patch (apply + commit)

#### Материалы:

- [Жизненный цикл приложения на Android](http://developer.alexanderklimov.ru/android/theory/lifecycle.php)
- [Методы активности](http://developer.alexanderklimov.ru/android/theory/activity_methods.php)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. Жизненный цикл Activity

- Применить 2_1_menu.patch (apply + commit)

#### Материалы:

- [Menu](https://developer.android.com/guide/topics/ui/menus?hl=ru)
- [Меню](http://developer.alexanderklimov.ru/android/menu.php)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. Диалог

- Применить 2_2_settings_activity.patch (apply + commit)
- Применить 2_3_create_pet_dialog.patch (apply + commit)

#### Материалы:

- [Dialogs](https://developer.android.com/guide/topics/ui/dialogs?hl=ru)
- [Диалоговое окно AlertDialog](http://developer.alexanderklimov.ru/android/alertdialog.php)
- [Диалоговые окна](http://developer.alexanderklimov.ru/android/theory/dialog.php)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. Room DataBase

- Применить 2_4_database.patch (apply + commit)
> После Apply Patch может потребоваться перезапуск Android Studio

#### Материалы:

- [Save data in a local database using Room](https://developer.android.com/training/data-storage/room/)
- [Room. Основы](https://startandroid.ru/ru/courses/architecture-components/27-course/architecture-components/529-urok-5-room-osnovy.html)
- [SQLite - Синтаксис](http://unetway.com/tutorial/sqlite-syntax/)
- [SQL As Understood By SQLite](https://www.sqlite.org/lang.html)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. Shared Preferences

- Применить 2_5_shared_preferences.patch (apply + commit)

#### Материалы:

- [SharedPreferences](http://developer.alexanderklimov.ru/android/theory/sharedpreferences.php)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. Recycler View

- Применить 2_6_recycler_view_change_pet.patch (apply + commit + **push**)
> После Apply Patch может потребоваться перезапуск Android Studio

#### Материалы:

- [Create a List with RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)
- [Пример использования CardView и RecyclerView в Android](http://www.fandroid.info/primer-ispolzovaniya-cardview-i-recyclerview-v-android/)
- [RecyclerView и CardView](https://habr.com/ru/post/237101/)

---

# ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW_2

```
1. Реализовать сохранение настройки звука on/off.

2. Создать диалоговое окно для смены имени питомца.
- Изменения должны сохраняться в базе данных.
- Дизайн должен быть в стиле dialog_create_pet.

3. Реализовать возможность сохранения объектов History в базу данных.
- Сделять связь многие к одному для history с помощью foreignKeys (Pet.id = History.petId).
- В HistoryDao реализовать методы: getAll, insert, delete.

4. Создать активити: DeletePetActivity.
- Дизайн должен быть в стиле activity_change_pet.
- В этом активити должен отображаться список всех питомцев.
- Добавить в каждую строку CheckBox.
- Добавить кнопку для удаления питомцев.
- По нажатию этой кнопки должно вызываться диалоговое окно для подтверждения действия. 
    - Диалоговое окно должно содержать две кнопки Ok и Cancel.
    - Дизайн диалогового окна должен быть в стиле dialog_create_pet.
- По нажатию кнопки Ok в диалоговом окне все отмеченные питомцы должны быть удалены, а список обновлен.
- Изменения должны сохраняться в базе данных.

5. Сделать так, что бы находясь в ChangePetActivity или DeletePetActivity при нажатии аппаратной кнопки "назад" 
пользователь попадал в SettingsActivity.
- Не допустить возможности множественного инстанса SettingsActivity.

6. Optional
Реализовать сортировку питомцев по имени и уровню для ChangePetActivity и DeletePetActivity.
```

#### Подсказки к HW2

- В задании 1 использовать SharedPreferences
- Взаимосвязи - [Room: Хранение данных на Android для всех и каждого](https://habr.com/ru/post/336196/) 
- [CheckBox (Флажок)](http://developer.alexanderklimov.ru/android/views/checkbox.php)
- [How does notifyDataSetChanged() method work](https://stackoverflow.com/questions/12229817/android-how-does-notifydatasetchanged-method-and-listviews-work)

---


### Дополнительные материалы:

- [Back up user data with Auto Backup](https://developer.android.com/guide/topics/data/autobackup)
- [SQLiteOpenHelper](https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase))
- [7 Steps To Room](https://medium.com/androiddevelopers/7-steps-to-room-27a5fe5f99b2)
- [Saving data using Room in Android](https://en.proft.me/2017/11/15/saving-data-using-room-android/)