package Menu;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Secret {

    public static Command fetchAsciiArtChunked() {
        String urlString = "http://ascii.live/can-you-hear-me";

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(urlString))
                    .header("User-Agent", "curl/7.79.1") // curl imitation
                    .GET()
                    .build();

            // reading chunked data

            client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            response.body().forEach(System.out::println);
                        } else {
                            System.err.println("Сервер вернул код ошибки: " + response.statusCode());
                        }
                    })
                    .join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
