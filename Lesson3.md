
<img src="https://s8.hostingkartinok.com/uploads/images/2019/03/5c2af85e528fa97929f8ada8d52b0df3.jpg"/>

# Курс по разработке приложения для Android: Tamagotchi

---

## [Материалы занятия]()

---

# ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW_2

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. SharedPreferences, Change pet name, History in DataBase

- Применить HW_2_0_shared_preferences_sound.patch (apply + commit)
- Применить HW_2_1_change_pet_name.patch (apply + commit)
- Применить HW_2_2_db_history.patch (apply + commit)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. Delete pet, Sorting

- Применить HW_2_3_delete_pet.patch (apply + commit)
- Применить HW_2_4_optional_sort.patch (apply + commit)

---


# Lesson 3

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. Receiver and Alarm

- Применить 3_0_receiver_and_alarm.patch (apply + commit)

#### Материалы:

- [Broadcast Receiver](http://developer.alexanderklimov.ru/android/theory/broadcast.php)
- [BroadcastReceiver в Android](http://reseach-android.blogspot.com/2013/05/broadcastreceiver-android.html)
- [Alarm Service](http://developer.alexanderklimov.ru/android/alarmmanager.php)
- [PendingIntent – флаги, requestCode. AlarmManager](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/204-urok-119-pendingintent-flagi-requestcode-alarmmanager.html)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. Handler

- Применить 3_1_handler.patch (apply + commit)

#### Материалы:

- [Класс Handler](http://developer.alexanderklimov.ru/android/theory/handler.php)
- [Handler](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/143-urok-80-handler-nemnogo-teorii-nagljadnyj-primer-ispolzovanija.html)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. Notification

- Применить 3_2_ill.patch (apply + commit)
- Применить 3_3_notification.patch (apply + commit)

#### Материалы:

- [Уведомления](http://developer.alexanderklimov.ru/android/notification.php)
- [Оповещения через Status Bar](https://habr.com/ru/post/140928/)
- [Notification + PendingIntent на примере](https://javadevblog.com/uvedomleniya-v-android-notification-pendingintent-na-primere.html)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. Boot receiver

- Применить 3_4_eat.patch (apply + commit)
- Применить 3_5_boot_receiver.patch (apply + commit)

#### Материалы:

- [Автозапуск приложения при загрузке](http://developer.alexanderklimov.ru/android/theory/boot.php)
- [Автоматический запуск приложения вместе с устройством](http://learn-android.ru/news/uchimsja_avtomaticheski_zapuskat_prilozhenie_vmeste_s_ustrojstvom/2015-04-30-102.html)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. Progress bar

- Применить 3_6_progress_bar.patch (apply + commit + **push**)

#### Материалы:

- [ProgressBar - Индикатор прогресса](http://developer.alexanderklimov.ru/android/views/progressbar.php)
- [ProgressBar](https://android-tools.ru/dokumentaciya/palette/progressbar/)

---

# ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW_3

```
1. Изменить заполнение ProgressBar в MainActivity и в WalkActivity так, что бы оно происходило 
с лево на право.

2. Добавить в activity_main два ProgressBar для отображения уровня сытости и уровня опыта.
- Реализовать функционал для этих ProgressBar в MainActivity.

3. Реализовать желание питомца гулять.
- Если питомца не выгулять, то он заболевает (по аналогии с тем, как если за ним не убрать).
- Реализовать отправку уведомлений, о желании питомца гулять.

4. Optional
Реализовать желание питомца спать, и его пробуждение от сна.
- Если питомца не отправить спать, то он заболевает (по аналогии с желанием гулять).
- Реализовать отправку уведомлений, о желании питомца спать.
- Реализовать отправку уведомлений, о пробуждении питомца.
- Во время сна питомец не может заболеть, есть, гулять, ещё раз отправиться спать.
- Во время сна питомец продолжает поглащать пищу.
- Питомец не может уснуть если он болеет.
- Пробуждение питомца происходит автоматически через промежуток времени, пользователь 
не должен иметь возможности влиять на пробуждение питомца.

5. Optional
Реализовать систему получения опыта питомцем за выполненные пользователем действия 
(+10 опыта за каждое действие).
- Реализовать систему увеличения уровня питомца. Для расчета перехода на новый уровень 
используйте эту формулу:
experience >= (50 + 200 * pet.getLvl() + Math.pow(1.1, pet.getLvl() + 25)) / 6)
- Лишний опыт, после увеличения уровня, переносится на следующий уровень.

```

#### Подсказки к HW_3

- 
-  
- 
- 

---


### Дополнительные материалы:

- [Broadcast Receiver на наглядном примере](http://fans-android.com/ispolzovanie-broadcast-receiver-na-naglyadnom-primere/)
- [Эти забавные BroadcastReceiver'ы](https://habr.com/ru/post/149875/)
- [Уведомления с использованием AlarmManager](https://habr.com/ru/sandbox/34130/)
- [Как заставить Alarm Manager работать](http://www.ohandroid.com/alarm-manager-androi.html)
- [Doze Mode](http://developer.alexanderklimov.ru/android/theory/doze.php)
- [Добавляем текст на ProgressBar](https://habr.com/ru/post/124708/)