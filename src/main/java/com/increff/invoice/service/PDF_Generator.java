package com.increff.invoice.service;


import com.increff.invoice.model.PdfData;
import org.apache.fop.apps.*;
import org.springframework.stereotype.Service;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PDF_Generator {

//    public static void main(String args[]){
//        pdf_generator();
//    }
    public void pdf_generator(PdfData d) {
        try {
            JavaToXML javaToXML = new JavaToXML();
            javaToXML.xmlGenerator(d);
            String xmlfile = new File("src/main/resources/xml\\invoice.xml").toURI().getPath();
            String xsltfile = new File("src/main/resources/xsl\\invoice.xsl").toURI().getPath();
            File pdfDir = new File("src/main/resources./pdfFiles");
            pdfDir.mkdirs();
            File pdfFile = new File(pdfDir, "invoice.pdf");
//            System.out.println(pdfFile.getAbsolutePath());
            // configure fopFactory as desired
            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // configure foUserAgent as desired
            // Setup output
            OutputStream out = new FileOutputStream(pdfFile);
            out = new java.io.BufferedOutputStream(out);
            try {
                // Construct fop with desired output format
                Fop fop;
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

                // Setup XSLT
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));

                // Setup input for XSLT transformation
                Source src = new StreamSource(xmlfile);

                // Resulting SAX events (the generated FO) must be piped through to FOP
                Result res = new SAXResult(fop.getDefaultHandler());

                // Start XSLT transformation and FOP processing
                transformer.transform(src, res);


            } catch (FOPException | TransformerException e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        }catch(IOException exp){
            exp.printStackTrace();
        }

    }
}
