package me.chaoticva.heather;

/**
 * The enum Content type.
 */
public enum ContentType {
    /**
     * Text plain content type.
     */
    TEXT_PLAIN("text/plain"),
    /**
     * Text html content type.
     */
    TEXT_HTML("text/html"),
    /**
     * Text css content type.
     */
    TEXT_CSS("text/css"),
    /**
     * Text xml content type.
     */
    TEXT_XML("text/xml"),
    /**
     * Application javascript content type.
     */
    APPLICATION_JAVASCRIPT("application/javascript"),
    /**
     * Application json content type.
     */
    APPLICATION_JSON("application/json"),
    /**
     * Application xml content type.
     */
    APPLICATION_XML("application/xml"),
    /**
     * Application octet stream content type.
     */
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    /**
     * Application x www form urlencoded content type.
     */
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    /**
     * Application pdf content type.
     */
    APPLICATION_PDF("application/pdf"),
    /**
     * Application zip content type.
     */
    APPLICATION_ZIP("application/zip"),
    /**
     * Application vnd ms excel content type.
     */
    APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel"),
    /**
     * Application vnd openxmlformats officedocument spreadsheetml content type.
     */
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    /**
     * Application vnd openxmlformats officedocument wordprocessingml content type.
     */
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML(
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    /**
     * Image png content type.
     */
    IMAGE_PNG("image/png"),
    /**
     * Image jpeg content type.
     */
    IMAGE_JPEG("image/jpeg"),
    /**
     * Image gif content type.
     */
    IMAGE_GIF("image/gif"),
    /**
     * Image svg xml content type.
     */
    IMAGE_SVG_XML("image/svg+xml"),
    /**
     * Multipart form data content type.
     */
    MULTIPART_FORM_DATA("multipart/form-data"),
    /**
     * Audio mpeg content type.
     */
    AUDIO_MPEG("audio/mpeg"),
    /**
     * Video mp 4 content type.
     */
    VIDEO_MP4("video/mp4"),
    /**
     * Application http content type.
     */
    APPLICATION_HTTP("application/http");

    ContentType(String name) {
        this.name = name;
    }

    /**
     * the actual content type string
     */
    private final String name;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}