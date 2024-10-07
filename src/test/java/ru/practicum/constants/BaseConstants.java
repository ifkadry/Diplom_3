package ru.practicum.constants;

import ru.practicum.utils.ConfigFileReader;

public class BaseConstants {
    public final static String BASE_TEST_URL = new ConfigFileReader().getApplicationUrl();
}
