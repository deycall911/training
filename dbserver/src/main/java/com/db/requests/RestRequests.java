package com.db.requests;

import com.db.entity.Images;
import com.db.entity.TagImageConnection;
import com.db.entity.Tags;
import com.db.repository.ImagesRepository;
import com.db.repository.TagImageConnectionRepository;
import com.db.repository.TagsRepository;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.h2.util.IOUtils;

/*
GET /images/ (all image metedata)
GET /image/$id/ (returns image metadata)
GET /image/$id/png (returns actual image)
PUT /image/$id/  returns image metadata)
POST /image returns image metadata)

 */


@Component
@Path("/images/")
public class RestRequests {
    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private TagImageConnectionRepository tagImageConnectionRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
//    public Map<String,Iterable<Images>> returnSomething() {
    public Iterable<Images> returnSomething() {


        Iterable<Images> customImages = imagesRepository.findAll();
//        HashMap<String,Iterable<Images>> result= new HashMap<String,Iterable<Images>> ();
//        result.put("result", customImages);
        return customImages;
    }

    @GET
    @Produces("front/png")
    @Path("/{imageId}/png")
    public byte[] getImageById(@PathParam("imageId") int imageId) {
        BufferedImage img;
        File imageFromDb = imagesRepository.findOne(imageId).getImage();
        try {
            img = ImageIO.read(imageFromDb);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", bao);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bao.toByteArray();
    }

    @POST
    @Path("/upload/{name}/{tag}")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_FORM_URLENCODED})
    public boolean uploadImage(@FormDataParam("file") InputStream imageInputStream, @PathParam("name") String name, @PathParam("tag") String tag) {

        File file;
        try {
            file = new File(name);
            OutputStream outputStream = new FileOutputStream(file);
            IOUtils.copy(imageInputStream, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        int currentImageId = (new Long(imagesRepository.count())).intValue();

        imagesRepository.save(new Images(currentImageId, name, file));

        StringTokenizer allTags = new StringTokenizer(tag);
        while (allTags.hasMoreTokens()) {
            String currentTag = allTags.nextToken();
            Integer currentTagId = tagsRepository.findByName(currentTag).getId();
            if (currentTagId == null) {
                currentTagId = createNewTag(currentTag);
            }

            addNewTagImageConnection(currentImageId, currentTagId);
        }
        return true;
    }

    private int createNewTag(String name) {
        int currentTagId = (int) tagsRepository.count();

        tagsRepository.save(new Tags(currentTagId, name));
        return currentTagId;
    }

    private void addNewTagImageConnection(Integer imageId, Integer tagId) {
        tagImageConnectionRepository.save(new TagImageConnection(imageId, tagId));
    }
}
