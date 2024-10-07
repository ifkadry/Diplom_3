package ru.practicum.utils;

import org.apache.commons.lang3.RandomStringUtils;
import ru.practicum.pojos.UserRequest;

public class UsersUtils {
    public static UserRequest getUniqueUser() {
        return new UserRequest(
                RandomStringUtils.randomAlphanumeric(10) + "@test.com",
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10)
        );
    }
}
