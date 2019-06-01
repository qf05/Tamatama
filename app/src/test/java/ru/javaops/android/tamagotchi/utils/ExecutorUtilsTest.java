package ru.javaops.android.tamagotchi.utils;

import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;


public class ExecutorUtilsTest {

    @Test
    public void getExecutor() {
        Object executor = ExecutorUtils.getExecutor();
        assertThat(executor, instanceOf(ExecutorService.class));
    }
}