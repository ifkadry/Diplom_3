package ru.practicum.api_steps;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.practicum.pojos.SignInRequest;
import ru.practicum.pojos.UserRequest;

import static io.restassured.RestAssured.given;
import static ru.practicum.constants.BaseConstants.BASE_TEST_URL;
import static ru.practicum.constants.UserConstants.*;

public class UsersSteps {
    public static final RequestSpecification REQUEST_SPECIFICATION =
            new RequestSpecBuilder()
                    .setBaseUri(BASE_TEST_URL + "/api")
                    .setBasePath(BASE_AUTH_URL)
                    .setContentType(ContentType.JSON)
                    .build();

    @Step("Создаём уникального юзера")
    public static Response createUniqueUser(UserRequest body) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .body(body)
                .when()
                .post(BASE_REGISTER_URL);
    }

    @Step("Удаляем пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .header("Authorization", accessToken)
                .when()
                .delete(BASE_USER_URL);
    }

    @Step("Выполняем авторизацию с помощью тела запроса на авторизацию")
    public static Response signInWithSignInRequest(SignInRequest signInRequest) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .body(signInRequest)
                .when()
                .post(BASE_LOGIN_URL);
    }
}
