import java.io.*;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;

public class Test
{
    public static void main(String[] args)
    {
        try {
            File htmlSourceFile = new File("/Users/manavshakya/Downloads/rc_detail_1.html");// source path
            File file = new File("/Users/manavshakya/Desktop/test.pdf"); //output path
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
            pdfDoc.setDefaultPageSize(new PageSize(530, 750));
            HtmlConverter.convertToPdf(new FileInputStream(htmlSourceFile),pdfDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}