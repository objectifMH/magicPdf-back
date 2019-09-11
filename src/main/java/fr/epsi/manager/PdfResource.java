/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.manager;

import static com.itextpdf.text.Annotation.URL;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import static com.itextpdf.text.pdf.PdfName.URL;
import com.sun.jersey.multipart.FormDataParam;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import static javax.ws.rs.HttpMethod.POST;


import java.io.OutputStream;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response.Status;
import org.springframework.web.bind.annotation.CrossOrigin;


/**
 * REST Web Service
 *
 * @author epsi
 */
@Path("pdf")
@CrossOrigin("*")
public class PdfResource {

    static int inc =  1 ; 
    
    @Context
    private UriInfo context;

    private String DATA = "C:\\pdf\\test1.pdf";

    private String DEST = "C:\\pdf\\Dest\\";
    private String CHEMIN = "C:\\pdf\\Dest\\split\\";
    private String CHEMIN_MERGE = "C:\\pdf\\Dest\\";

    private String TEST_PDF = "C:\\pdf\\HelloWorld.pdf";
    private String MERGE_PDF = "C:\\pdf\\merge.pdf";
    private String TEST_IMG = "C:\\pdf\\img\\bd.jpg";
    private String TEST_IMG2 = "C:\\pdf\\img\\donald.jpg";

    /**
     * Creates a new instance of GenericResource
     */
    public PdfResource() {
    }

    /**
     * Retrieves representation of an instance of
     * fr.epsi.manager.GenericResource
     *
     * @param pdfFile
     * @param path
     * @param uploadedInputStream
     * @return an instance of java.lang.String
     * @throws java.io.IOException
     */
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPdf() {
        String monget = " ca fonction json" ; 
             
        System.out.println("On dans le get ");

    Response response = Response.ok(monget).build();
    return response ;

}*/
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)  
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(  @FormDataParam("fileUp") File pdfFile    ) throws IOException {
        
        inc++;
        String nomPdf = "upload"+inc+".pdf";
        // @FormParam("fileUp") File fileUpload , @FormParam("fileUp") String nomFile 
        
        if ( pdfFile.length() > 0 )
        {
            System.out.println("Nom temp "+ pdfFile.getName());
            copyFiles(pdfFile, nomPdf);
        }
        System.out.println("Nom sur le serveur : "+ nomPdf);
        return Response
                .status(Status.OK)
                .entity("{\"nomPdf\":\""+nomPdf+"\"}")
                .build();
    }

    public  void copyFiles(File sourceLocation, String nomPdf )
    throws IOException {

        {
           
                InputStream in = new FileInputStream(sourceLocation);
                OutputStream out = new FileOutputStream(DEST+ nomPdf);

                // Copy the bits from input stream to output stream
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                   
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        System.out.println(" je suis dans le get json front bien recu ");
        return "{ \"name\": \"nom-pdf\" }";
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putPdf(String content
    ) {
    }

    @GET // M�thode HTTP utilis�e pour d�clencher cette m�thode
    @Path("/hello")
    public Response hello() throws IOException, DocumentException {
        System.out.println("Hello  ");
        String monSt = "creation de Hello ";

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(CHEMIN + "HelloWorld.pdf"));
            document.open();
            document.add(new Paragraph("A Hello World PDF document."));
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return Response
                .status(Status.OK)
                .build();

    }

    @GET // M�thode HTTP utilis�e pour d�clencher cette m�thode
    @Path("/image")
    public String addImage() throws IOException, DocumentException {
        System.out.println("Image  ");
        String monSt = "Ajout d'une image ";

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(TEST_PDF));
            document.open();
            document.add(new Paragraph("Image Example"));

            //Add Image
            Image image1 = Image.getInstance(TEST_IMG2);
            //Fixed Positioning
            image1.setAbsolutePosition(100f, 550f);
            //Scale to new height and new width of image
            image1.scaleAbsolute(200, 200);
            //Add to document
            document.add(image1);

            //String imageUrl = "http://www.eclipse.org/xtend/images/java8_logo.png";
            //Image image2 = Image.getInstance(new URL(imageUrl));
            //document.add(image2);
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return monSt;

    }

    @GET // M�thode HTTP utilis�e pour d�clencher cette m�thode
    @Path("/merge")
    public String merge() throws IOException, DocumentException {
        System.out.println("merge   ");
        String monSt = "merge de pdf  ";

        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(MERGE_PDF));

        document.open();
        // for (URL file : files){

        PdfReader reader = new PdfReader(CHEMIN + "merge1.pdf");
        copy.addDocument(reader);
        copy.freeReader(reader);
        reader.close();

        reader = new PdfReader(CHEMIN + "merge2.pdf");
        copy.addDocument(reader);
        copy.freeReader(reader);
        reader.close();

        reader = new PdfReader(CHEMIN + "merge3.pdf");
        copy.addDocument(reader);
        copy.freeReader(reader);
        reader.close();
        //}
        document.close();

        return monSt;
    }

    @GET // M�thode HTTP utilis�e pour d�clencher cette m�thode
    @Path("/split")
    public Response split( @QueryParam("pdfSplit") String pdfSplit) throws IOException, DocumentException {
        System.out.println("Je suis dans le split  ");
        

        // read original pdf file
        String filename = pdfSplit; //"pdf1.pdf";
        System.out.println(" le chemin {" + CHEMIN_MERGE + pdfSplit +"} [" + pdfSplit + "]");
        //PdfReader reader = new PdfReader(CHEMIN + "merge1.pdf");
        PdfReader reader = new PdfReader(CHEMIN_MERGE + filename); //CHEMIN = "C:\\pdf\\Dest\\";

        // get number of pages
                int n = reader.getNumberOfPages();
                System.out.println("Number of pages: " + n);

        // loop over all pages
         /*
        int i = 0;
        while (i < n) {

            // create destination file name
            String destination = CHEMIN_MERGE + filename.substring(0, filename.indexOf(".pdf")) + "-" + String.format("%03d", i + 1) + ".pdf";
            System.out.println("Writing " + destination);

            // create new document with corresponding page size
            Document document = new Document(reader.getPageSizeWithRotation(1));

            // create writer and assign document and destination
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(destination));
            document.open();

            // read original page and copy to writer
            PdfImportedPage page = copy.getImportedPage(reader, ++i);
            copy.addPage(page);

            // close and write the document
            document.close();
        }
         */
        return Response
                .status(Status.OK)
                .build();

    }

    public void upload() {

    }
}
