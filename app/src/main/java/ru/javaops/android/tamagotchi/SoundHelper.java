package ru.javaops.android.tamagotchi;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.SparseBooleanArray;

import java.util.EnumMap;

import ru.javaops.android.tamagotchi.enums.PetsType;

public class SoundHelper {
    private static SoundPool soundPool;
    private static EnumMap<PetsType, Integer> soundMap = new EnumMap<>(PetsType.class);
    private static SparseBooleanArray soundCheckMap = new SparseBooleanArray();

    public static void initialSoundPool(Context context) {
        if (soundPool == null) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(20)
                    .setAudioAttributes(attributes)
                    .build();
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    if (status == 0) {
                        soundCheckMap.put(sampleId, true);
                    } else {
                        soundCheckMap.put(sampleId, false);
                    }
                }
            });
            soundMap.put(PetsType.CAT, soundPool.load(context, R.raw.cat, 0));
            soundMap.put(PetsType.DOG, soundPool.load(context, R.raw.dog, 0));
            soundMap.put(PetsType.CTHULHU, soundPool.load(context, R.raw.cthulhu, 0));
        }
    }

    public static void play(PetsType petsType) {
        Integer sampleId = soundMap.get(petsType);
        if (sampleId != null && soundCheckMap.get(sampleId)) {
            soundPool.play(sampleId, 1, 1, 2, 0, 1);
        }
    }

}
