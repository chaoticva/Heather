package me.chaoticva.heather;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.*;

class HeatherTest
{
    @Test
    public void testCreateInstance()
    {
        Heather heather = Heather.create();
        assertNotNull(heather);
    }

    @Test
    public void testSetMethod()
    {
        Heather heather = Heather.create()
                .method(HttpMethod.DELETE);

        assertEquals(heather.getMethod(), HttpMethod.DELETE);
    }

    @Test
    public void testSetUrl()
    {
        Heather heather = Heather.create()
                .url("https://www.google.com");

        assertEquals(heather.getUrl(), "https://www.google.com");
    }

    @Test
    public void testSetBody()
    {
        Heather heather = Heather.create()
                .body("Hello, World!");

        assertEquals(heather.getBody(), "Hello, World!");
    }

    @Test
    public void testAddHeader()
    {
        Heather heather = Heather.create()
                .header("Custom-Header", "CustomValue");

        assertNotNull(heather.getHeaders().get("Custom-Header"));
        assertEquals(heather.getHeaders().get("Custom-Header"), "CustomValue");
    }

    @Test
    public void testAddParam()
    {
        Heather heather = Heather.create()
                .param("limit", "1");

        assertNotNull(heather.getParams().get("limit"));
        assertEquals(heather.getParams().get("limit"), "1");
    }

    @Test
    public void testSetContentType()
    {
        Heather heather = Heather.create()
                .contentType(ContentType.APPLICATION_JSON);

        assertEquals(heather.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testSendGetRequestWithoutParams()
    {
        Heather heather = Heather.create()
                .header("User-Agent", "Opera/9.60 (Windows NT 6.0; U; en) Presto/2.1.1")
                .url("https://postman-echo.com/get");

        AtomicReference<Response<String>> response = new AtomicReference<>(null);
        assertDoesNotThrow(() -> response.set(heather.send(String.class)));

        assertNotNull(response.get());
        assertNotNull(response.get().getData());
        assertEquals(response.get().getStatusCode(), HttpStatusCode.OK.getCode());
    }

    @Test
    public void testSendGetRequestWithParams()
    {
        Heather heather = Heather.create()
                .header("User-Agent", "Opera/9.60 (Windows NT 6.0; U; en) Presto/2.1.1")
                .url("https://postman-echo.com/get?marco=polo")
                .param("q", "test");

        AtomicReference<Response<String>> response = new AtomicReference<>(null);
        assertDoesNotThrow(() -> response.set(heather.send(String.class)));

        assertNotNull(response.get());
        assertNotNull(response.get().getData());
        assertEquals(response.get().getStatusCode(), HttpStatusCode.OK.getCode());
    }

    @Test
    public void testSendPostRequestWithBody()
    {
        Heather heather = Heather.create()
                .method(HttpMethod.POST)
                .header("User-Agent", "Opera/9.60 (Windows NT 6.0; U; en) Presto/2.1.1")
                .url("https://postman-echo.com/post")
                .body("This is a test.");

        AtomicReference<Response<String>> response = new AtomicReference<>(null);
        assertDoesNotThrow(() -> response.set(heather.send(String.class)));

        assertNotNull(response.get());
        assertNotNull(response.get().getData());
        assertEquals(response.get().getStatusCode(), HttpStatusCode.OK.getCode());
    }

    @Test
    public void testSendPostRequestWithoutBody()
    {
        Heather heather = Heather.create()
                .method(HttpMethod.POST)
                .header("User-Agent", "Opera/9.60 (Windows NT 6.0; U; en) Presto/2.1.1")
                .url("https://postman-echo.com/post");

        assertThrows(IllegalStateException.class, () -> heather.send(String.class));
    }

    @Test
    public void testSendRequestWithCustomHeaders()
    {
        Heather heather = Heather.create()
                .header("User-Agent", "Opera/9.60 (Windows NT 6.0; U; en) Presto/2.1.1")
                .header("Custom-Header", "Custom Value")
                .url("https://postman-echo.com/get");

        AtomicReference<Response<String>> response = new AtomicReference<>(null);
        assertDoesNotThrow(() -> response.set(heather.send(String.class)));

        assertEquals(response.get().getStatusCode(), HttpStatusCode.OK.getCode());
    }

    @Test
    public void testSendRequestThrowsExceptionForInvalidUrl()
    {
        Heather heather = Heather.create()
                .header("User-Agent", "Opera/9.60 (Windows NT 6.0; U; en) Presto/2.1.1")
                .url("https://goggle.comm");

        assertThrows(Exception.class, () -> heather.send(String.class));
    }
}