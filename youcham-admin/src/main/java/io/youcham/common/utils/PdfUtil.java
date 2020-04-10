package io.youcham.common.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;

public class PdfUtil {
	//生成单元格
	public static PdfPCell createCell(Object value,Font font) {
		PdfPCell cell = new PdfPCell(new Paragraph(String.valueOf(value),font));
	        cell.setBorderColor(BaseColor.BLACK);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        return cell;
	}
	//生成单元格
	public static PdfPCell createTitleCell(Object value,Font font) {
			PdfPCell cell = new PdfPCell(new Paragraph(value.toString(),font));
		        cell.setBorderColor(BaseColor.BLACK);
		        cell.setBackgroundColor(BaseColor.GRAY);
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		        return cell;
	}
}
