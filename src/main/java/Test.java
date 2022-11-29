import java.io.*;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class Test
{
    static void mergePdfFiles(List<InputStream> inputPdfList,
                              OutputStream outputStream) throws Exception{

        //Create document and pdfReader objects.
        Document document = new Document();
        List<PdfReader> readers = new ArrayList<>();
        int totalPages = 0;

        //Create pdf Iterator object using inputPdfList.
        Iterator<InputStream> pdfIterator = inputPdfList.iterator();

        // Create reader list for the input pdf files.
        while (pdfIterator.hasNext()) {
            InputStream pdf = pdfIterator.next();
            PdfReader pdfReader = new PdfReader(pdf);
            readers.add(pdfReader);
            totalPages = totalPages + pdfReader.getNumberOfPages();
        }

        // Create writer for the outputStream
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        //Open document.
        document.open();

        //Contain the pdf data.
        PdfContentByte pageContentByte = writer.getDirectContent();

        PdfImportedPage pdfImportedPage;
        int currentPdfReaderPage = 1;
        Iterator<PdfReader> iteratorPDFReader = readers.iterator();

        // Iterate and process the reader list.
        while (iteratorPDFReader.hasNext()) {
            PdfReader pdfReader = iteratorPDFReader.next();
            //Create page and add content.
            while (currentPdfReaderPage <= pdfReader.getNumberOfPages()) {
                document.newPage();
                pdfImportedPage = writer.getImportedPage(
                        pdfReader,currentPdfReaderPage);
                pageContentByte.addTemplate(pdfImportedPage, 1, 1);
                currentPdfReaderPage++;
            }
            currentPdfReaderPage = 1;
        }

        //Close document and outputStream.
        outputStream.flush();
        document.close();
        outputStream.close();

        System.out.println("Pdf files merged successfully.");
    }

    public static void main(String[] args)
    {
        try {
            File htmlSourceFile1 = new File("src/main/resources/rc_detail_1.html");// source path
            File file1 = new File("src/main/resources/test1.pdf"); //output path
            PdfDocument pdfDoc1 = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(file1));
            pdfDoc1.setDefaultPageSize(new PageSize(530, 750));
            HtmlConverter.convertToPdf(new FileInputStream(htmlSourceFile1),pdfDoc1);

            File htmlSourceFile2 = new File("src/main/resources/rc_detail_2.html");// source path
            File file2 = new File("src/main/resources/test2.pdf"); //output path
            PdfDocument pdfDoc2 = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(file2));
            pdfDoc2.setDefaultPageSize(new PageSize(530, 750));
            HtmlConverter.convertToPdf(new FileInputStream(htmlSourceFile2),pdfDoc2);

            File htmlSourceFile3 = new File("src/main/resources//rc_detail_3.html");// source path
            File file3 = new File("src/main/resources/test3.pdf"); //output path
            PdfDocument pdfDoc3 = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(file3));
            pdfDoc3.setDefaultPageSize(new PageSize(530, 750));
            HtmlConverter.convertToPdf(new FileInputStream(htmlSourceFile3),pdfDoc3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //Prepare input pdf file list as list of input stream.
            List<InputStream> inputPdfList = new ArrayList<InputStream>();
            inputPdfList.add(new FileInputStream("src/main/resources/test1.pdf"));
            inputPdfList.add(new FileInputStream("src/main/resources/test2.pdf"));
            inputPdfList.add(new FileInputStream("src/main/resources/test3.pdf"));

            //Prepare output stream for merged pdf file.
            OutputStream outputStream =
                    new FileOutputStream("src/main/resources/Output.pdf");

            //call method to merge pdf files.
            mergePdfFiles(inputPdfList, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}