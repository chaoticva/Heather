package me.chaoticva.heather;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.net.http.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Heather
{
    private Heather()
    {
    }

    public static Heather create()
    {
        return new Heather();
    }

    public Heather method(HttpMethod method)
    {
        this.method = method;
        return this;
    }

    public Heather url(String url)
    {
        this.url = url;
        return this;
    }

    public Heather body(Object body)
    {
        this.body = body;
        return this;
    }

    public Heather header(String key, String value)
    {
        this.headers.put(key, value);
        return this;
    }

    public Heather param(String key, String value)
    {
        this.params.put(key, value);
        return this;
    }

    public Heather contentType(ContentType contentType)
    {
        this.contentType = contentType;
        return this;
    }

    public Heather acceptContentType(ContentType contentType)
    {
        this.acceptContentType = contentType;
        return this;
    }

    public <T> Response<T> send() throws URISyntaxException, IOException, InterruptedException
    {
        return send(null);
    }

    public <T> Response<T> send(Type responseType)
            throws URISyntaxException, IOException, InterruptedException
    {
        if (!params.isEmpty())
        {
            url += "?" + params.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));
        }

        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(new URI(url));

        if (body == null)
        {
            if (List.of(HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT).contains(method))
            {
                throw new IllegalStateException("Body cannot be null on request type: %s".formatted(method.name()));
            }

            builder.method(method.name(), HttpRequest.BodyPublishers.noBody());
        } else
        {
            if (body instanceof File file)
            {
                builder.method(method.name(), HttpRequest.BodyPublishers.ofFile(file.toPath()));
                contentType(ContentType.APPLICATION_OCTET_STREAM);
            } else
            {
                builder.method(method.name(), HttpRequest.BodyPublishers.ofString(gson.toJson(body)));
            }
        }

        addHeaders(builder);

        if (responseType == File.class)
        {
            File tempFile = File.createTempFile("http-response-", ".tmp");
            tempFile.deleteOnExit();

            HttpResponse<Path> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofFile(tempFile.toPath()));

            return new Response<T>().statusCode(response.statusCode()).data((T) tempFile);
        }

        HttpResponse<String> response = client.send(builder.build(),
                HttpResponse.BodyHandlers.ofString());

        if (responseType == null)
        {
            return new Response<T>().statusCode(response.statusCode()).data(null);
        }

        if (responseType == String.class)
        {
            return new Response<T>().statusCode(response.statusCode()).data((T) response.body());
        }

        return new Response<T>().statusCode(response.statusCode()).data(gson.fromJson(response.body(), responseType));
    }

    private void addHeaders(HttpRequest.Builder builder)
    {
        headers.forEach(builder::header);
        builder.header("Content-Type", contentType.getName());

        if (contentType != null)
        {
            builder.header("Accept", acceptContentType.getName());
        }
    }

    public Map<String, String> getHeaders()
    {
        return headers;
    }

    public Map<String, String> getParams()
    {
        return params;
    }

    public HttpMethod getMethod()
    {
        return method;
    }

    public String getUrl()
    {
        return url;
    }

    public Object getBody()
    {
        return body;
    }

    public ContentType getContentType()
    {
        return contentType;
    }

    private static final HttpClient client = HttpClient.newHttpClient();

    private static final Gson gson = new Gson();

    private final Map<String, String> headers = new HashMap<>();

    private final Map<String, String> params = new HashMap<>();

    private HttpMethod method = HttpMethod.GET;

    private String url;

    private Object body;

    private ContentType contentType = ContentType.APPLICATION_JSON;

    private ContentType acceptContentType = ContentType.TEXT_HTML;
}
