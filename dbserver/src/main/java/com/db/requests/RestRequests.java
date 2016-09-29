package com.db.requests;

import com.db.entity.Images;
import com.db.entity.TagImageConnection;
import com.db.entity.Tags;
import com.db.repository.ImagesRepository;
import com.db.repository.TagImageConnectionRepository;
import com.db.repository.TagsRepository;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
GET /images/ (all image metedata)
GET /image/$id/ (returns image metadata)
GET /image/$id/png (returns actual image)
PUT /image/$id/  (returns image metadata)
POST /image (returns image metadata)

 */


@Component
@Path("/")
public class RestRequests {
    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private TagImageConnectionRepository tagImageConnectionRepository;

    @GET
    @Path("/images")
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Images> returnSomething() {
        Iterable<Images> customImages = imagesRepository.findAll();
        return customImages;
    }

    @GET
    @Path("/image/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Images getImageMetaData(@PathParam("id") Integer id) {
        return imagesRepository.findOne(id);
    }

    @GET
    @Produces("front/png")
    @Path("image/{imageId}/png")
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

    @PUT
    @Path("/image")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.APPLICATION_JSON)
    public Images uploadImage(@FormDataParam("file") InputStream imageInputStream, @FormDataParam("name") String name, @FormDataParam("tag") String tag) {

        File file;
        try {
            file = new File(name);
            OutputStream outputStream = new FileOutputStream(file);
            IOUtils.copy(imageInputStream, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        Images image = imagesRepository.save(new Images(name, file));
        int currentImageId = image.getId();

        StringTokenizer allTags = new StringTokenizer(tag);
        while (allTags.hasMoreTokens()) {
            String currentTagText = allTags.nextToken();
            Tags currentTag = tagsRepository.findByName(currentTagText);
            if (currentTag == null) {
                currentTag = createNewTag(currentTagText);
            }

            addNewTagImageConnection(currentImageId, currentTag.getId());
        }
        return image;
    }

    @GET
    @Path("/tags/image/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> returtTagsWhereImageId(@PathParam("id") Integer id) {
        List<String> tagsForCurrentImageId = new ArrayList<>();
        tagImageConnectionRepository.findByImageId(id).stream().forEach(tagConnection -> {
            tagsForCurrentImageId.add(tagsRepository.findOne(tagConnection.getTagId()).getName());
        });
        return tagsForCurrentImageId;
    }

    private Tags createNewTag(String name) {
        return tagsRepository.save(new Tags(name));
    }

    private int addNewTagImageConnection(Integer imageId, Integer tagId) {
        return tagImageConnectionRepository.save(new TagImageConnection(imageId, tagId)).getId();
    }
}
