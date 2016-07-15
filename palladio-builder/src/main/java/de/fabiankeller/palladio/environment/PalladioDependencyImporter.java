package de.fabiankeller.palladio.environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Fetch Palladio dependencies from the update site.
 */
public class PalladioDependencyImporter {
    public static final String UPDATE_SITE_BASE = "https://sdqweb.ipd.kit.edu/eclipse/palladiosimulator";

    public static void main(final String[] args) throws Exception {
        new PalladioDependencyImporter().importLocal("pathing/.m2", "1.0.7");
    }

    private void importLocal(final String mavenRepo, final String release) throws Exception {
        final Map<String, String> available = jarsFromDirectoryListing(pathToPlugins(release));
        available.putAll(jarsFromDirectoryListing(new URL("http://download.eclipse.org/eclipse/updates/4.4/R-4.4.2-201502041700/plugins/?d")));

        final List<String> required = dependenciesFromPom();
        for (final String dep : required) {
            if (available.containsKey(dep)) {
                System.out.println("  >>> Contains: " + dep);
            } else {
                System.out.println("  >>> Missing: " + dep);
            }
        }
    }


    // // DEPENDENCIES // //

    private Map<String, String> jarsFromDirectoryListing(final URL url) throws IOException {
        final String pluginsDL = httpGET(url, true);
        System.out.println(pluginsDL);
        final Map<String, String> allMatches = new HashMap<>();
        final Matcher m = Pattern.compile("<a href=[\"']{1}([^\"']*?/?)([^\"'/]+)(_[^\"']+\\.jar(\\.pack\\.gz)?)[\"']{1}>").matcher(pluginsDL);
        while (m.find()) {
            assert !allMatches.containsKey(m.group(2));
            allMatches.put(m.group(2), m.group(1) + m.group(2) + m.group(3));
        }
        return allMatches;
    }

    private List<String> dependenciesFromPom() throws IOException {
        final List<String> dependencies = new ArrayList<>();
        final String pom = Files.readAllLines(Paths.get("pom.xml")).stream().collect(Collectors.joining("\n"));
        final Matcher m = Pattern.compile("<groupId>de\\.fabiankeller\\.palladio</groupId>\\s*<artifactId>([^<]+)</artifactId>").matcher(pom);
        while (m.find()) {
            dependencies.add(m.group(1));
        }
        return dependencies;
    }


    // // UPDATE SITE URLS // //

    private URL pathToPlugins(final String release) throws MalformedURLException {
        return new URL(String.format("%s/releases/%s/aggregate/plugins", UPDATE_SITE_BASE, release));
    }

    private URL pathToPlugin(final String release, final String pluginFile) throws MalformedURLException {
        return new URL(String.format("%s/%s", pathToPlugins(release), pluginFile));
    }


    // // HTTP HELPERS // //

    private String httpGET(final URL url) throws IOException {
        return httpGET(url, false);
    }

    private String httpGET(final URL url, final boolean forceText) throws IOException {
        // open
        final URLConnection conn = url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("User-Agent", "runscope/0.1");
        if (!(conn instanceof HttpURLConnection)) {
            throw new IOException("Bad protocol for URL: " + url + ". Must be HTTP.");
        }

        // get response
        final InputStream body = conn.getInputStream();
        final HttpURLConnection response = (HttpURLConnection) conn;
        if (response.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new IOException(String.format("URL %s not found!", url));
        }

        // determine charset
        final String contentType = conn.getHeaderField("Content-Type");
        String charset = null;
        for (final String param : contentType.replace(" ", "").split(";")) {
            if (param.startsWith("charset=")) {
                charset = param.split("=", 2)[1];
                break;
            }
        }

        if (charset != null || forceText) {
            if (charset == null) {
                charset = "UTF-8";
            }
            // text content
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(body, charset))) {
                final StringBuilder appender = new StringBuilder();
                for (String line; (line = reader.readLine()) != null; ) {
                    appender.append(line);
                }
                reader.close();
                return appender.toString();
            }
        } else {
            // It's likely binary content, use InputStream/OutputStream.
            throw new RuntimeException("NYI");
        }
    }
}
