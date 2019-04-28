package ru.javaops.android.tamagotchi.enums;

import ru.javaops.android.tamagotchi.R;

public enum NameCheckStatus {
    EMPTY(R.string.error_empty_name),
    EXCEEDED_CHARACTERS_LIMIT(R.string.error_max_characters),
    EXCEEDED_CHARACTERS_MIN(R.string.error_min_characters),
    NOT_CORRECT(R.string.error_not_correct_name),
    CORRECT(-1);

    private int messageId;

    NameCheckStatus(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }
}
