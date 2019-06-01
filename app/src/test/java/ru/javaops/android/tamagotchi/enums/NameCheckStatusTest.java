package ru.javaops.android.tamagotchi.enums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import ru.javaops.android.tamagotchi.R;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(RobolectricTestRunner.class)
public class NameCheckStatusTest {

    @Test
    public void getMessageId() {
        assertThat(NameCheckStatus.EMPTY.getMessageId(), is(R.string.error_empty_name));
        assertThat(NameCheckStatus.EXCEEDED_CHARACTERS_LIMIT.getMessageId(), is(R.string.error_max_characters));
        assertThat(NameCheckStatus.EXCEEDED_CHARACTERS_MIN.getMessageId(), is(R.string.error_min_characters));
        assertThat(NameCheckStatus.NOT_CORRECT.getMessageId(), is(R.string.error_not_correct_name));
        assertThat(NameCheckStatus.CORRECT.getMessageId(), is(-1));
    }
}