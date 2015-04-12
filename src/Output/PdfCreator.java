package Output;


import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;







import Data.DataReport;
import Math.Utility;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfCreator {

	private static String file = System.getProperty("user.home") + "/Desktop/Miinivalja_pass.pdf";
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
	private static Font superSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 5, Font.NORMAL);
	private static BaseColor GRAY = new BaseColor(200,200,200);
	
	private DataReport dataReport;
	private String filePath;
	
	public PdfCreator(DataReport dataReport, String filePath) {
		this.dataReport = dataReport;
		this.filePath = filePath;
		System.out.println(file);
	}

	public void makePDF(){
		try {

			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			addMetaData(document);
			document.add(createTable());
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private void addMetaData(Document document) throws BadElementException, DocumentException {
		document.addTitle("Miiniv�lja pass");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText, Minefield, Mine, Document");
		document.addAuthor("");
		document.addCreator("Generated by T�kkepass_v2"); 
	}
	
	private PdfPTable getMineSummaryTable() {
		PdfPTable subTableMinefield = new PdfPTable(9);
		
		PdfPCell cell = new PdfPCell();
		//Cell x=0-4, y=0
		cell = new PdfPCell(new Phrase("Tankit�rjemiinid"));
		cell.setBackgroundColor(GRAY);
		cell.setFixedHeight(20);
		cell.setColspan(5);
		subTableMinefield.addCell(cell);
		
		//Cell x=5, y=0-1
		cell = new PdfPCell(new Phrase("Eemal\ndusvasta\nselt",smallFont));
		cell.setBackgroundColor(GRAY);
		cell.setRowspan(2);
		subTableMinefield.addCell(cell);
		
		//Cell x=6-8, y=0
		cell = new PdfPCell(new Phrase("Kaitselaengud"));
		cell.setBackgroundColor(GRAY);
		cell.setFixedHeight(20);
		cell.setColspan(3);
		subTableMinefield.addCell(cell);
		
		//Cell x=0-9, y=1
		for (int i=0; i<6; i++){
			cell = new PdfPCell(new Phrase("T��p",smallFont));
			cell.setBackgroundColor(GRAY);
			cell.setFixedHeight(15);
			subTableMinefield.addCell(cell);
			if (i==3||i==5){
				cell = new PdfPCell(new Phrase("Kokku",smallFont));
				cell.setBackgroundColor(GRAY);
				cell.setFixedHeight(15);
				subTableMinefield.addCell(cell);
			}
		}
		
		//Cell x=0-9, y=2
//		for (int i=0; i<9; i++){
//			cell = new PdfPCell(new Phrase(" ",smallFont));
//			cell.setFixedHeight(15);
//			subTableMinefield.addCell(cell);
//		}
		
		cell = new PdfPCell(new Phrase("Norm",smallFont));
		cell.setFixedHeight(15);
		subTableMinefield.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Vrs",smallFont));
		cell.setFixedHeight(15);
		subTableMinefield.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Dbl",smallFont));
		cell.setFixedHeight(15);
		subTableMinefield.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Mag",smallFont));
		cell.setFixedHeight(15);
		subTableMinefield.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Kokku",smallFont));
		cell.setFixedHeight(15);
		subTableMinefield.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Demv",smallFont));
		cell.setFixedHeight(15);
		subTableMinefield.addCell(cell);
		
		for (int i=0; i<3; i++){
			cell = new PdfPCell(new Phrase(" ",smallFont));
			cell.setFixedHeight(15);
			subTableMinefield.addCell(cell);
		}
			
		//Cell x=0-9, y=3
		for (int i=0; i<9; i++){
			cell = new PdfPCell(new Phrase("Arv"));
			cell.setBackgroundColor(GRAY);
			cell.setFixedHeight(19);
			subTableMinefield.addCell(cell);
		}
		
		int size = dataReport.getMineSummary().size();
		
		// First empty row in table
		for (int j=0; j<9; j++){
			cell = new PdfPCell(new Phrase(" "));
			cell.setFixedHeight(15);
			subTableMinefield.addCell(cell);
		}


		//Cell x=0-9, y=3-13
		for (int i=0; i<size; i++){
			ArrayList<Integer> rowSummary = dataReport.getMineSummary().get(i);

			int demvAmount = rowSummary.get(0);

			for (int j=0; j<5; j++){
				int amount = rowSummary.get(j+2);

				cell = new PdfPCell(new Phrase(String.valueOf(amount), smallFont));
				cell.setFixedHeight(15);
				subTableMinefield.addCell(cell);
			}
			
			cell = new PdfPCell(new Phrase(String.valueOf(demvAmount), smallFont));
			cell.setFixedHeight(15);
			subTableMinefield.addCell(cell);

			for (int j=0; j<3; j++){
				cell = new PdfPCell(new Phrase(" "));
				cell.setFixedHeight(15);
				subTableMinefield.addCell(cell);
			}
		}

		//Empty rows
		for (int i=0; i<9-size; i++){
			for (int j=0; j<9; j++){
				cell = new PdfPCell(new Phrase(" "));
				cell.setFixedHeight(15);
				subTableMinefield.addCell(cell);
			}
		}

		//Total sum row
		for (int j=0; j<5; j++){
			
			int amount = dataReport.getFullSumArray().get(j+2);
			cell = new PdfPCell(new Phrase(String.valueOf(amount), smallFont));
			cell.setFixedHeight(15);
			subTableMinefield.addCell(cell);
		}
		
		int amount = dataReport.getFullSumArray().get(0);
		cell = new PdfPCell(new Phrase(String.valueOf(amount), smallFont));
		cell.setFixedHeight(15);
		subTableMinefield.addCell(cell);
		
		for (int j=0; j<4; j++){
			cell = new PdfPCell(new Phrase(" "));
			cell.setFixedHeight(15);
			subTableMinefield.addCell(cell);
		}
		
		return subTableMinefield;
	}
	  
	private PdfPTable createTable()
			throws DocumentException {
	    
	    PdfPTable mainTableTop = new PdfPTable(2);
	    mainTableTop.setTotalWidth(580);
	    mainTableTop.setWidths(new float[]{8, 5});
	    mainTableTop.setLockedWidth(true);
	    PdfPCell cell;
	    
	    //Table at position x=0-1, y=0
	    	PdfPTable subTable0x0 = new PdfPTable(3);
	    	
	    	//Cell x=0, y=0
	    	cell = new PdfPCell(new Phrase("MIINIV�LJA PASS",subFont));
	    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	cell.setBorderWidth((float) 1.5);
	    	subTable0x0.addCell(cell);
	    	//Cell x=1, y=0
	    	subTable0x0.addCell("Salastuse aste: ");
	    	//Cell x=2, y=0
	    	subTable0x0.addCell("Koopia:  /  , Leht:  /  ");
	    	
	    	//Cell x=0, y=1
	    	subTable0x0.addCell("K�su andja: ");
	    	
	    	//Cell x=1, y=1-2
	    		PdfPTable subTableStart = new PdfPTable(2);
	    		subTableStart.setWidths(new float[]{1, 2});
	    		//subCell x=0, y=0-1
	    		cell = new PdfPCell(new Phrase("Kuup�ev/ Ajagrupp"));
	    		cell.setRowspan(2);
	    		subTableStart.addCell(cell);
	    		//subCell x=1, y=0
	    		subTableStart.addCell("Algus: ");
	    		//subCell x=1, y=1
	    		subTableStart.addCell("Valmimine: ");
	    		cell = new PdfPCell(subTableStart);
	    		cell.setRowspan(2);
	    		subTable0x0.addCell(cell);
	    	
	    	//Cell x=2, y=1
	    	subTable0x0.addCell("Miiniv�lja nr: ");
	    	
	    	//Cell x=0, y=2
	    	subTable0x0.addCell("Paigaldav �ksus: ");

	    	//Cell x=2, y=2
	    	subTable0x0.addCell("Kaart: ");
	    	
	    	//Cell x=0, y=3
	    	subTable0x0.addCell("Paigaldava �ksuse/all�ksuse juht: ");
	    	//Cell x=1, y=3
	    	subTable0x0.addCell("Passi valmistaja: ");
	    	//Cell x=2, y=3
	    	subTable0x0.addCell("Leht: ");
	    	
	    cell = new PdfPCell(subTable0x0);
	    cell.setBorderWidth((float) 1.5);
	    cell.setColspan(2);
	    mainTableTop.addCell(cell);
	    
	    
	    
	    //Table at position x=0, y=1
			PdfPTable subTable0x1 = new PdfPTable(3);
			subTable0x1.setWidths(new float[]{1, 3, 3});
			
			//Cell x=0-2, y=0
			cell = new PdfPCell(new Phrase("Orientiirid"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			subTable0x1.addCell(cell);
			
			//Cell x=0, y=1
			subTable0x1.addCell("Nr");
			//Cell x=1, y=1
			subTable0x1.addCell("Koordinaadid");
			//Cell x=2, y=1
			subTable0x1.addCell("Kirjeldus");
			
			//Cell x=0, y=2
			cell = new PdfPCell(new Phrase("1"));
			cell.setBackgroundColor(GRAY);
			subTable0x1.addCell(cell);
			//Cell x=1, y=2
			subTable0x1.addCell(" ");
			//Cell x=2, y=2
			subTable0x1.addCell(" ");
			
			//Cell x=0, y=3
			cell = new PdfPCell(new Phrase("2"));
			cell.setBackgroundColor(GRAY);
			subTable0x1.addCell(cell);
			//Cell x=1, y=3
			subTable0x1.addCell(" ");
			//Cell x=2, y=3
			subTable0x1.addCell(" ");
			
			//Cell x=0, y=4
			cell = new PdfPCell(new Phrase("3"));
			cell.setBackgroundColor(GRAY);
			subTable0x1.addCell(cell);
			//Cell x=1, y=4
			subTable0x1.addCell(" ");
			//Cell x=2, y=4
			subTable0x1.addCell(" ");
			
			//Cell x=0, y=5
			cell = new PdfPCell(new Phrase("4"));
			cell.setBackgroundColor(GRAY);
			subTable0x1.addCell(cell);
			//Cell x=1, y=5
			subTable0x1.addCell(" ");
			//Cell x=2, y=5
			subTable0x1.addCell(" ");

		cell = new PdfPCell(subTable0x1);
		cell.setBorderWidth((float) 1.5);
		mainTableTop.addCell(cell);
		    
		    
		//Table at position x=1, y=1
			PdfPTable subTable1x1 = new PdfPTable(2);
			subTable1x1.setWidths(new float[]{1, 6});
			
			//Cell x=0-1, y=0
			cell = new PdfPCell(new Phrase("Vaheorientiirid"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			subTable1x1.addCell(cell);
			
			//Cell x=0, y=1
			subTable1x1.addCell("Nr");
			//Cell x=1, y=1
			subTable1x1.addCell("Kirjeldus");
			
			//Cell x=0, y=2
			cell = new PdfPCell(new Phrase("1"));
			cell.setBackgroundColor(GRAY);
			subTable1x1.addCell(cell);
			//Cell x=1, y=2
			subTable1x1.addCell(" ");
			
			//Cell x=0, y=3
			cell = new PdfPCell(new Phrase("2"));
			cell.setBackgroundColor(GRAY);
			subTable1x1.addCell(cell);
			//Cell x=1, y=3
			subTable1x1.addCell(" ");
		
			//Cell x=0, y=4
			cell = new PdfPCell(new Phrase("3"));
			cell.setBackgroundColor(GRAY);
			subTable1x1.addCell(cell);
			//Cell x=1, y=4
			subTable1x1.addCell(" ");
		
			//Cell x=0, y=5
			cell = new PdfPCell(new Phrase("4"));
			cell.setBackgroundColor(GRAY);
			subTable1x1.addCell(cell);
			//Cell x=1, y=5
			subTable1x1.addCell(" ");
		
		cell = new PdfPCell(subTable1x1);
		cell.setBorderWidth((float) 1.5);
		mainTableTop.addCell(cell);
		    
		    
		
		//Table at position x=0, y=2
			PdfPTable subTable0x2 = new PdfPTable(2);
			
			//Cell x=0-1, y=0
			cell = new PdfPCell(new Phrase("Tarastuse v�i markeerimise kirjeldus"));
			cell.setColspan(2);
			cell.setMinimumHeight(70);
			subTable0x2.addCell(cell);
			
			//Cell x=0, y=1
			subTable0x2.addCell("V��ndite/ridade arv");
			//Cell x=1, y=1
			subTable0x2.addCell("V��ndite/ridade t�histuse kirjeldus");
		
		cell = new PdfPCell(subTable0x2);
		cell.setBorderWidth((float) 1.5);
		mainTableTop.addCell(cell);
	    
		//Table at position x=1, y=2
			PdfPTable subTable1x2 = new PdfPTable(4);
			subTable1x2.setWidths(new float[]{1, 2, 2, 2});
			
			//Cell x=0-3, y=0
			cell = new PdfPCell(new Phrase("L�bip��s"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(4);
			subTable1x2.addCell(cell);
			
			//Cell x=0, y=1
			subTable1x2.addCell("Nr");
			//Cell x=1, y=1
			subTable1x2.addCell("Laius");
			//Cell x=2, y=1
			subTable1x2.addCell("M�rgistus");
			//Cell x=3, y=1
			subTable1x2.addCell("Sulgemismeetod");
			
			//Cell x=0, y=2
			cell = new PdfPCell(new Phrase("1"));
			cell.setBackgroundColor(GRAY);
			subTable1x2.addCell(cell);
			//Cell x=1, y=2
			subTable1x2.addCell(" ");
			//Cell x=2, y=2
			subTable1x2.addCell(" ");
			//Cell x=3, y=2
			subTable1x2.addCell(" ");
			
			//Cell x=0, y=3
			cell = new PdfPCell(new Phrase("2"));
			cell.setBackgroundColor(GRAY);
			subTable1x2.addCell(cell);
			//Cell x=1, y=3
			subTable1x2.addCell(" ");
			//Cell x=2, y=3
			subTable1x2.addCell(" ");
			//Cell x=3, y=3
			subTable1x2.addCell(" ");
			
			//Cell x=0, y=4
			cell = new PdfPCell(new Phrase("3"));
			cell.setBackgroundColor(GRAY);
			subTable1x2.addCell(cell);
			//Cell x=1, y=4
			subTable1x2.addCell(" ");
			//Cell x=2, y=4
			subTable1x2.addCell(" ");
			//Cell x=3, y=4
			subTable1x2.addCell(" ");
			
		
		cell = new PdfPCell(subTable1x2);
		cell.setBorderWidth((float) 1.5);
		mainTableTop.addCell(cell);
		    
		
		//Table at position x=0, y=2
				PdfPTable subTable0x3 = new PdfPTable(2);
				subTable0x3.setWidths(new float[]{1, 5});
				//Cell x=0, y=0
				cell = new PdfPCell(new Phrase("Miiniv�lja t��p"));
				cell.setBackgroundColor(GRAY);
				cell.setFixedHeight(69);
				subTable0x3.addCell(cell);
				
				//Table at subPosition x=1, y=0-3
					

					//End of mine summary
					cell = new PdfPCell(getMineSummaryTable());
					cell.setRowspan(4);
					subTable0x3.addCell(cell);
				
				
				
				//Table at subPosition x=0, y=1-3
					PdfPTable subTableinedata = new PdfPTable(3);
					subTableinedata.setWidths(new float[]{1, 4, 2});
					
					//Cell x=0, y=0-9
					cell = new PdfPCell(new Phrase("Miinid",smallFont));
					cell.setBackgroundColor(GRAY);
					cell.setRowspan(10);
					subTableinedata.addCell(cell);
					
					
					//Cell x=1-2, y=0-9
					cell = new PdfPCell(new Phrase("Pinnases Maapinnal",smallFont));
					cell.setBackgroundColor(GRAY);
					cell.setRowspan(5);
					subTableinedata.addCell(cell);
					String[] mineIds = {"EMV","A","B","C","D","E","F","G","H","I"};
					for( String i : mineIds){
						
						
						if (i.equals("EMV")){
							cell = new PdfPCell(new Phrase(i,superSmallFont));
						}
						else{
							cell = new PdfPCell(new Phrase(i,smallFont));
						}
						cell.setBackgroundColor(GRAY);
						cell.setFixedHeight(15);
						subTableinedata.addCell(cell);
						
						
						//Cell x=1, y=5
						if (i.equals("D")){
							cell = new PdfPCell(new Phrase("V��ndis Reas Ilma skeemita",smallFont));
							cell.setRowspan(5);
							cell.setBackgroundColor(GRAY);
							subTableinedata.addCell(cell);
						}
					}
					//Cell x=0, y=10
					subTableinedata.addCell(" ");
					//Cell x=1, y=10
					cell = new PdfPCell(new Phrase("Kokku",smallFont));
					cell.setColspan(2);
					cell.setFixedHeight(15);
					subTableinedata.addCell(cell);
				
				cell = new PdfPCell(subTableinedata);
				cell.setRowspan(4);
				subTable0x3.addCell(cell);
							
			
			cell = new PdfPCell(subTable0x3);
			cell.setBorderWidth((float) 1.5);
			mainTableTop.addCell(cell);

			
		
		//Table at position x=1, y=3
			PdfPTable subTable1x3 = new PdfPTable(2);
			
			//Cell x=0-1, y=0
			cell = new PdfPCell(new Phrase("M�rkused \n\nMiinide vahe     meetrit/sammu"));
			cell.setColspan(2);
			subTable1x3.addCell(cell);
			
			//Cells x=0-1, y=1-12
			for (int i=0; i<12; i++){
				cell = new PdfPCell(new Phrase(" "));
				cell.setColspan(2);
				subTable1x3.addCell(cell);
			}
			
			//Cell x=0, y=13
			subTable1x3.addCell("Allkiri (Paigaldava all�ksuse �lem) ");
			//Cell x=1, y=13
			subTable1x3.addCell("Kuup�ev");
		
		cell = new PdfPCell(subTable1x3);
		cell.setBorderWidth((float) 1.5);
		mainTableTop.addCell(cell);
		
		
		
	    return mainTableTop;

	  }

	public DataReport getDataClass() {
		return dataReport;
	}

	public void setDataClass(DataReport dataClass) {
		this.dataReport = dataClass;
	}
	  
	  

}
