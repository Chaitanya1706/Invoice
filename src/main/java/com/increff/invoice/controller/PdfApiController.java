package com.increff.invoice.controller;
import com.increff.invoice.model.PdfData;
import com.increff.invoice.service.PDF_Generator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api
@RestController
public class PdfApiController {

    @Autowired
    private PDF_Generator pdfGenerator;


    @ApiOperation(value = "generate pdf")
    @RequestMapping(path = "/api/pdf/generate", method = RequestMethod.POST)
    public ResponseEntity<byte[]> downloadInvoice(@RequestBody PdfData pdfData) throws IOException {

        pdfGenerator.pdf_generator(pdfData);

        Path pdf = Paths.get("./src/main/resources/pdfFiles/invoice.pdf");

        byte[] contents = Files.readAllBytes(pdf);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, invoicet-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;

    }

}
