package com.kashuba.petproject.builder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.kashuba.petproject.model.entity.Policy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfBuilder {


public static void createPdf(Policy policy) {
    Document document = new Document();

    try {
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Policy.pdf"));
        System.out.println("Hello, boy");
        document.open();
        document.add(new Paragraph("Welcome"));
        document.close();
        writer.close();
    } catch (
            DocumentException e) {
        e.printStackTrace();
    } catch (
            FileNotFoundException e) {
        e.printStackTrace();
    }
}
}
