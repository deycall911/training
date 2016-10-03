package com.db.requests;

import com.db.entity.Images;
import com.db.entity.TagImageRelation;
import com.db.entity.Tags;
import com.db.exception.RestRequestException;
import com.db.repository.ImagesRepository;
import com.db.repository.TagImageConnectionRepository;
import com.db.repository.TagsRepository;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/*
GET /images/ (all image metedata)
GET /image/$id/ (returns image metadata)
GET /image/$id/png (returns actual image)
PUT /image/$id/  (returns image metadata)
POST /image (returns image metadata)
GET /image/$id/tags
DELETE /image/$id

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
        return imagesRepository.findAll();
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
    public byte[] getImageById(@PathParam("imageId") int imageId) throws IOException {
        File imageFromDb = imagesRepository.findOne(imageId).getImage();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(imageFromDb), "png", bao);
        return bao.toByteArray();
    }

    @PUT
    @Path("/image")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@FormDataParam("file") InputStream imageInputStream, @FormDataParam("name") String name, @FormDataParam("tag") String tag, @FormDataParam("file") FormDataContentDisposition fileDisposition) {

        File file;
        try {
            if (new File(fileDisposition.getFileName()).exists()) {
                file = new File(name + String.valueOf(new Random().nextInt()) + fileDisposition.getFileName());
            } else {
                file = new File(fileDisposition.getFileName());
            }
            OutputStream outputStream = new FileOutputStream(file);
            IOUtils.copy(imageInputStream, outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new RestRequestException();
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
        return Response.ok(image).build();
    }

    @GET
    @Path("/tags/image/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> returtTagsWhereImageId(@PathParam("id") Integer id) {
        List<String> tagsForCurrentImageId = new ArrayList<>();
        tagImageConnectionRepository.findByImageId(id).forEach(tagConnection -> {
            tagsForCurrentImageId.add(tagsRepository.findOne(tagConnection.getTagId()).getName());
        });
        return tagsForCurrentImageId;
    }

    private Tags createNewTag(String name) {
        return tagsRepository.save(new Tags(name));
    }

    private int addNewTagImageConnection(Integer imageId, Integer tagId) {
        return tagImageConnectionRepository.save(new TagImageRelation(imageId, tagId)).getId();
    }

    @DELETE
    @Path("top/secret/drop/all")
    public void dropTables() {
        imagesRepository.findAll().forEach(image -> {
            image.getImage().delete();
        });

        imagesRepository.deleteAll();
        tagsRepository.deleteAll();
        tagImageConnectionRepository.deleteAll();
    }

    @DELETE
    @Path("/image/{id}")
    public void deleteImage(@PathParam("id") Integer id) {
        imagesRepository.findOne(id).getImage().delete();
        imagesRepository.delete(id);
    }
}
