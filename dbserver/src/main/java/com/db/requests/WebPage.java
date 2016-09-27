package com.db.requests;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Component
@Path("/")
public class WebPage {

//
//    @Autowired
//    private ImagesRepository imagesRepository;
//
//    @Autowired
//    private TagsRepository tagsRepository;
//
//    @Autowired
//    private TagImageConnectionRepository tagImageConnectionRepository;
//
//



    @GET
    @Path("/")
    public Response testServer() {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("uploadForm.html").getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }




        return Response.ok(result.toString()).build();
    }
//    @GET
//    @Path("/")
//    @Produces(MediaType.TEXT_HTML)
//    public String home() {
//
//        StringBuilder result = new StringBuilder("");
//        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource("uploadForm.html").getFile());
//
//        try (Scanner scanner = new Scanner(file)) {
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                result.append(line).append("\n");
//            }
//            scanner.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            e.printStackTrace();
//        }
//
//        StringBuilder imageData = new StringBuilder("");
//        for (Integer ImageId : imagesRepository.getAllIds()) {
//            imageData.append("{name: '" + imagesRepository.getNameForId(ImageId) + "' , ");
//            imageData.append("id: '" + String.valueOf(ImageId) + "' , ");
//
//            imageData.append("tags: [");
//            for (Integer tagId : tagImageConnectionRepository.getTagIdsFromImageId(ImageId)) {
//                imageData.append("'" + tagsRepository.getNameById(tagId) + "',");
//            }
//            if (imageData.substring(imageData.length() - 1).equals(",")) {
//                imageData.deleteCharAt(imageData.length() - 1);
//            }
//            imageData.append("]},");
//        }
//
//        if (imageData.length() > 0) {
//            if (imageData.substring(imageData.length() - 1).equals(",")) {
//                imageData.deleteCharAt(imageData.length() - 1);
//            }
//        }
//
//        return result.toString().replace("{imagesArrayDataToRecive}", imageData.toString());
//    }
//
//    @GET
//    @Produces("front/png")
//    @Path("/images/{imageId}")
//    public byte[] getImageById(@PathParam("imageId") int imageId) {
//        BufferedImage img;
//        File imageFromDb = imagesRepository.getImageWhereId(imageId);
//        try {
//            img = ImageIO.read(imageFromDb);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(img, "png", bao);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return bao.toByteArray();
//    }
//
    @GET
    @Produces("application/json; charset=utf-8")
    @Path("/script")
    public String getJS() {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource("script.js").getFile());

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
//
//    @POST
//    @Path("/upload/{name}/{tag}")
//    @Consumes({MediaType.MULTIPART_FORM_DATA,MediaType.APPLICATION_FORM_URLENCODED})
//    public String uploadImage(@FormDataParam("file") InputStream imageInputStream, @PathParam("name") String name, @PathParam("tag") String tag) {
//
//        File file;
//        try {
//            file = new File(name);
//            OutputStream outputStream = new FileOutputStream(file);
//            IOUtils.copy(imageInputStream, outputStream);
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "redirect:/upload_fail";
//        }
//        Integer highestValue = imagesRepository.getHighestIdValue();
//        int currentImageId = (highestValue == null ? 0 : highestValue) + 1;
//
//        imagesRepository.save(new Images(currentImageId, name, file));
//        System.out.println("CurrentImageID: " + currentImageId);
//
//        StringTokenizer allTags = new StringTokenizer(tag);
//        while (allTags.hasMoreTokens()) {
//            String currentTag = allTags.nextToken();
//            Integer currentTagId = tagsRepository.getIdByName(currentTag);
//            if (currentTagId == null) {
//                currentTagId = createNewTag(currentTag);
//            }
//
//            addNewTagImageConnection(currentImageId, currentTagId);
//        }
//        return "redirect:/";
//    }
//
//    private int createNewTag(String name) {
//        Integer highestTagIdValue = tagsRepository.getHighestIdValue();
//        int currentTagId = (highestTagIdValue == null ? 0 : highestTagIdValue) + 1;
//
//        tagsRepository.save(new Tags(currentTagId, name));
//        return currentTagId;
//    }
//
//    private void addNewTagImageConnection(Integer imageId, Integer tagId) {
//        tagImageConnectionRepository.save(new TagImageConnection(imageId, tagId));
//
//    }
}
