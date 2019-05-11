package ru.javaops.android.tamagotchi.ui;

import android.content.res.Resources;
import android.view.Menu;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.databinding.ActivityMainBinding;
import ru.javaops.android.tamagotchi.viewmodel.MainViewModel;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private static MainActivity activity;
    @Mock
    private BasePetActivity basePetActivity;
    @Mock
    private ActivityMainBinding binding;
//    @Mock
//    private static DataBase db;
//    @Mock
//    private static PetDao dao;
//

//    @Before
//    public static void init() {
////        db = mock(DataBase.class);
////        dao = mock(PetDao.class);
//        final Pet pet = new Pet("Cat", PetsType.CAT);
//        pet.setId(1);
////        Answer<PetDao> daoAnswer = new Answer<PetDao>() {
////            @Override
////            public PetDao answer(InvocationOnMock invocation) throws Throwable {
////                return dao;
////            }
////        };
//        Answer<Pet> petAnswer = new Answer<Pet>() {
//            @Override
//            public Pet answer(InvocationOnMock invocation) throws Throwable {
//                return pet;
//            }
//        };
//
////        Mockito.when(db.petDao()).then(daoAnswer);
//        Mockito.when(dao.findById(anyLong())).then(petAnswer);
//    }
//
//    @AfterClass
//    public static void destroy() {
//        activity.finish();
//        activity = null;
//    }

    @After
    public void init() {
        ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        controller.create().start().resume();
        activity = controller.get();
    }

    @Test
    public void onCreate() {


//        MainActivity activity = mock(MainActivity.class);
//        Mockito.when(activity.createModel(MainViewModel.class)).thenReturn(null);
//        activity.onCreate(null);
        Mockito.verify(activity).createModel(MainViewModel.class);
        Mockito.verify(binding).setModel(Mockito.any(MainViewModel.class));
        Mockito.verify(binding).setLifecycleOwner(activity);
    }

    @Test
    public void onCreateOptionsMenu() {
        final Menu menu = shadowOf(activity).getOptionsMenu();
        Resources resources = activity.getResources();
        assertEquals(menu.findItem(R.id.pet_settings).getTitle(),
                resources.getString(R.string.pets));
        assertEquals(menu.findItem(R.id.alarm_settings).getTitle(),
                resources.getString(R.string.notification));
        assertEquals(menu.findItem(R.id.show_history).getTitle(),
                resources.getString(R.string.history));
        assertEquals(menu.findItem(R.id.off_sound).getTitle(),
                resources.getString(R.string.sound));
    }
}
