package http.route;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class RoutingMap {

    private static final String DOT = "\\.";

    /**
     * scan all controller packages and save them into routing map
     */
    private List readAllControllers(String packages) throws IOException {

        final String actualPackage = packages.replaceAll(DOT, File.separator);
        final File directory = new File(getRoot().getPath() + File.separator + actualPackage);
        if (directory.exists()) {
            final File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                return Arrays.stream(files)
                        .map(File::getAbsolutePath)
                        .map(RoutingMap::loadAllClassFromFolder)
                        .collect(Collectors.toList());
            }
        }
        throw new RuntimeException(packages + "is not able to find any classes !");
    }

    private static URL getRoot() throws IOException {
        final Enumeration<URL> roots = ClassLoader.getSystemClassLoader().getResources("");
        return roots.nextElement();
    }

    /**
     * get uri from entry point and find them from the routing map
     */
    private void loadRoutingMap() {

    }

    private void loadFilters() {

    }

    private void loadAuthenticationInterception() {

    }

    private void processMimeTypeFromHeader() {

    }

    private static Class loadAllClassFromFolder(String file) {
        return fileToClass(new File(file));
    }

    private static Class fileToClass(File file) {
        Throwable throwable;
        try {
            final String className = file.getAbsolutePath()
                    .replaceAll(getRoot().getPath(), "")
                    .replaceAll(".class", "")
                    .replaceAll(File.separator, DOT);
            return Class.forName(className);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throwable = e;
        }
        throw new RuntimeException(throwable);
    }

    public static void main(String[] args) {
        RoutingMap routingMap = new RoutingMap();
        try {
            System.out.println(routingMap.readAllControllers("service"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
