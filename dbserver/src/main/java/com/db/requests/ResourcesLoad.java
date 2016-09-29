package com.db.requests;


import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Component
@Path("/resources/")
public class ResourcesLoad {
    @GET
    @Produces("application/json; charset=utf-8")
    @Path("/script.js")
    public String getJS() {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource("scripts/script.min.js").getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @GET
    @Produces("application/json; charset=utf-8")
    @Path("/{scriptName}/{scriptFileName}")
    public String getBowerJS(@PathParam("scriptName") String scriptName, @PathParam("scriptFileName") String scriptFileName) {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();


        File file = new File(classLoader.getResource("bower_components/" + scriptName + "/" + scriptFileName).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @GET
    @Produces("application/json; charset=utf-8")
    @Path("/bootstrap.min.css")
    public String getBowerJS() {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();


        File file = new File(classLoader.getResource("bower_components/bootstrap/dist/css/bootstrap.min.css").getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
