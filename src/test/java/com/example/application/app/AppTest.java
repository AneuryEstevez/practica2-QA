package com.example.application.app;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        page.close();
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
        context.close();
    }

    @Test
    void testLogin() {
        page.navigate("http://localhost:8080/login");
        assertThat(page).hasTitle(Pattern.compile("Login"));

        // Asegurar que los inputs y botón están visibles
        assertThat(page.locator("vaadin-login-form input[name='username']")).isVisible();
        assertThat(page.locator("vaadin-login-form input[name='password']")).isVisible();
        assertThat(page.locator("vaadin-login-form vaadin-button[slot='submit']")).isVisible();

        // Llenar los campos de nombre de usuario y contraseña
        page.fill("vaadin-login-form input[name='username']", "user");
        page.fill("vaadin-login-form input[name='password']", "user");

        // Hacer clic en el botón de inicio de sesión
        page.click("vaadin-login-form vaadin-button[slot='submit']");

        // Asegurar que se redirigió a la página principal de la app
        assertEquals("http://localhost:8080/", page.url());
    }

    @Test
    void testFailLogin() {
        page.navigate("http://localhost:8080/login");
        assertThat(page).hasTitle(Pattern.compile("Login"));

        // Asegurar que los inputs y botón están visibles
        assertThat(page.locator("vaadin-login-form input[name='username']")).isVisible();
        assertThat(page.locator("vaadin-login-form input[name='password']")).isVisible();
        assertThat(page.locator("vaadin-login-form vaadin-button[slot='submit']")).isVisible();

        // Llenar los campos de nombre de usuario y contraseña incorrectos
        page.fill("vaadin-login-form input[name='username']", "user");
        page.fill("vaadin-login-form input[name='password']", "user1");

        // Hacer clic en el botón de inicio de sesión
        page.click("vaadin-login-form vaadin-button[slot='submit']");

        // Asegurar que se muestra el mensaje de error
        assertThat(page.locator("h5[part='error-message-title']")).containsText("Incorrect username or password");
    }

    @Test
    void testSendFailMessage() {
        page.navigate("http://localhost:8080/login");
        page.fill("vaadin-login-form input[name='username']", "user");
        page.fill("vaadin-login-form input[name='password']", "user");
        page.click("vaadin-login-form vaadin-button[slot='submit']");

        assertEquals("http://localhost:8080/", page.url());

        // Asegurar que los inputs y botón están visibles
        assertThat(page.locator("#nameInput")).isVisible();
        assertThat(page.locator("#emailInput")).isVisible();
        assertThat(page.locator("#messageInput")).isVisible();
        assertThat(page.locator("#sendButton")).isVisible();

        // Enviar form vacía
        page.click("#sendButton");

        // Asegurar que se muestran mensajes de error
        assertThat(page.locator("#error-message-vaadin-text-field-3")).hasText("Name is required");
        assertThat(page.locator("#error-message-vaadin-email-field-6")).hasText("Email is required");
        assertThat(page.locator("#error-message-vaadin-text-area-9")).hasText("Message is required");

    }

    @Test
    void testSendMessage() {
        page.navigate("http://localhost:8080/login");
        page.fill("vaadin-login-form input[name='username']", "user");
        page.fill("vaadin-login-form input[name='password']", "user");
        page.click("vaadin-login-form vaadin-button[slot='submit']");

        assertEquals("http://localhost:8080/", page.url());

        // Asegurar que los inputs y botón están visibles
        assertThat(page.locator("#nameInput")).isVisible();
        assertThat(page.locator("#emailInput")).isVisible();
        assertThat(page.locator("#messageInput")).isVisible();
        assertThat(page.locator("#sendButton")).isVisible();

        // Llenar y enviar form
        page.fill("#nameInput > input", "Aneury");
        page.fill("#emailInput > input", "example@email.com");
        page.fill("#messageInput > textarea", "Hola, como está?");
        page.click("#sendButton");

        // Asegurar que se muestra la notificación con el mensaje de success
        assertThat(page.locator("vaadin-notification-card")).isVisible();
        assertThat(page.locator("vaadin-notification-card")).hasText("Message sent successfully!");

    }

    @Test
    void testProfileView() {
        page.navigate("http://localhost:8080/login");
        page.fill("vaadin-login-form input[name='username']", "user");
        page.fill("vaadin-login-form input[name='password']", "user");
        page.click("vaadin-login-form vaadin-button[slot='submit']");

        page.navigate("http://localhost:8080/profile-view");
        assertEquals("http://localhost:8080/profile-view", page.url());

        // Asegurar que los componentes están visibles
        assertThat(page.locator("#avatar")).isVisible();
        assertThat(page.locator("#name")).isVisible();

        // Asegurar que los componentes tengan el contenido deseado
        assertThat(page.locator("h3")).containsText("Personal Information");
        assertThat(page.locator("#avatar")).containsText("AE");
        assertThat(page.locator("#name")).containsText("Aneury Estevez");

    }


}
