/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.eni.app.pdf;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author erb
 */
public class Pdf {

    public static final String DEST = "Factures/facture.pdf";

    /**
     *
     * @param dest
     * @param date
     * @param nomClient
     * @param designations
     * @param prixUnitaires
     * @param nombres
     * @throws IOException
     */
    public void createPdf(String dest, String date, String nomClient, ArrayList<String> designations, ArrayList<Integer> prixUnitaires, ArrayList<Integer> nombres) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        //Add paragraph to the document
        try ( // Initialize document
                Document document = new Document(pdf, PageSize.A5, false)) {
            //Add paragraph to the document
            document.add(new Paragraph("Date: "+date+"\nNom du Client : " + nomClient));
            document.setMargins(20, 20, 20, 20); // 20 points margin on each side

            // Add a table with 4 columns that takes all available width
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1}))
                    .useAllAvailableWidth();

            // Add header cells
            table.addHeaderCell(new Cell().add(new Paragraph("Désignation")).setBackgroundColor(ColorConstants.GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Prix Unitaire")).setBackgroundColor(ColorConstants.GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Nombre")).setBackgroundColor(ColorConstants.GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Total")).setBackgroundColor(ColorConstants.GRAY));

            // Variables to calculate total
            int totalGeneral = 0;

            // Add data rows
            for (int i = 0; i < designations.size(); i++) {
                int total = prixUnitaires.get(i) * nombres.get(i);
                totalGeneral += total;

                table.addCell(new Cell().add(new Paragraph(designations.get(i))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(prixUnitaires.get(i)))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(nombres.get(i)))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(total))));
            }

            // Add total general row
            table.addCell(new Cell(1, 3).add(new Paragraph("Total Général")).setBackgroundColor(ColorConstants.GRAY));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(totalGeneral))).setBackgroundColor(ColorConstants.GRAY));

            // Add table to document
            document.add(table);
            //Close document
            document.close();
        }
    }
}
