package supply_manager;

import java.io.File;
import java.io.IOException;
import java.util.*;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;


public class Documento {

	static final int NUMFORN=50;
	static Map<Integer,String> fornitori=new HashMap();
	WritableWorkbook workbook;
	
	public Documento(boolean ric,ArrayList<Integer>[] dati) {
		//MAPPA DEI FORNITORI:
		fornitori.put(0, "PICOTRONIK SRL"); fornitori.put(1, "ACTRONIC-GECO"); fornitori.put(2, "ADIMPEX"); fornitori.put(3, "ARROW-LASI"); fornitori.put(4, "AVNET-ABACUS"); fornitori.put(5, "AVNET-MEMEC"); fornitori.put(6, "AVNET-SILICA"); fornitori.put(7, "BERTON"); fornitori.put(8, "BETTER"); fornitori.put(9, "COSMOS"); fornitori.put(10, "DDS"); fornitori.put(11, "DELCO"); fornitori.put(12, "DIGIKEY"); fornitori.put(13, "DISTRELEC"); fornitori.put(14, "DONEL"); fornitori.put(15, "EBV"); fornitori.put(16, "ELECTRONIC CENTER"); fornitori.put(17, "ELSTORE"); fornitori.put(18, "ELTEC"); fornitori.put(19, "EMCO"); fornitori.put(20, "FARNELL"); fornitori.put(21, "GEMITECH"); fornitori.put(22, "GOLDONI-DONDI"); fornitori.put(23, "GRAFOS"); fornitori.put(24, "HELLIS"); fornitori.put(25, "HUMMEL"); fornitori.put(26, "LA TECNIKA 2"); fornitori.put(27, "LART-TECNICADUE"); fornitori.put(28, "MITSUBISHI"); fornitori.put(29, "MOUSER"); fornitori.put(30, "PAC PCB"); fornitori.put(31, "PHOENIX MECANO (BOPLA)"); fornitori.put(32, "REILINK OMEGA"); fornitori.put(33, "REPCOM"); fornitori.put(34, "RS COMPONENTS"); fornitori.put(35, "SERITAL"); fornitori.put(36, "TARGHE MALAGOLI"); fornitori.put(37, "TD ELEKTRONICS"); fornitori.put(38, "TECNOLASA"); fornitori.put(39, "TECNOMASTER"); fornitori.put(40, "TISCI"); fornitori.put(41, "TORRICELLI ROLANDO"); fornitori.put(42, "TTI"); fornitori.put(43, "VECTOR"); 
		
		try {
			
			int id=1;
			File file = new File("/../../Documents and Settings/utente/Desktop/OutputSM/"+id+"-Output.xls");
			//File file = new File("/../../10.0.0.188/picotronik su picostore/OutputSM/"+id+"-Output.xls");
			while(file.exists()){
				id++;
				file = new File("/../../Documents and Settings/utente/Desktop/OutputSM/"+id+"-Output.xls");	
				//file = new File("/../../10.0.0.188/picotronik su picostore/OutputSM/"+id+"-Output.xls");
			}
			
			workbook = Workbook.createWorkbook(file);
			
			//CICLO CHE PER OGNI FORNITORE CREA UN FOGLIO EXCEL:
			for(int s=0;s<NUMFORN;s++){
				if (dati[s].isEmpty()==false){
					WritableSheet sheet = workbook.createSheet(fornitori.get(s+1), 0); 
				
					WritableFont arial16font = new WritableFont(WritableFont.ARIAL, 16);
					WritableCellFormat arial16format = new WritableCellFormat (arial16font);
					if(ric){
						Label rich=new Label(0,0,"Richesta d'offerta",arial16format);
						sheet.addCell(rich);
					}else {
						Label off=new Label(0,0,"Ordine",arial16format);
						sheet.addCell(off);
					}
					//DATA IN ALTO A DESTRA:
					Date now = Calendar.getInstance().getTime();
					DateFormat customDateFormat = new DateFormat ("dd mm yyyy");
					WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat);
					DateTime dateCell = new DateTime(2, 0,now, dateFormat);
					sheet.addCell(dateCell); 
					Label mir=new Label(1,0,"Mirandola lì");
					sheet.addCell(mir);
				
					WritableFont arial12font = new WritableFont(WritableFont.ARIAL, 12);
					WritableCellFormat arial12format = new WritableCellFormat (arial12font);
					Label qt=new Label(0,1,"Qt necessaria",arial12format);
					Label descriz=new Label(1,1,"Qt da Ordinare",arial12format);
					Label note=new Label(2,1,"Descrizione",arial12format);
					Label conf=new Label(3,1,"Codice",arial12format);
					Label commessa=new Label(4,1,"Commessa",arial12format);
					Label prezzo=new Label(5,1,"Consegna",arial12format);
					Label valuta=new Label(6,1,"Prezzo",arial12format);
					Label consegna=new Label(7,1,"Valuta",arial12format);
					sheet.addCell(qt);
					sheet.addCell(descriz);
					sheet.addCell(commessa);
					sheet.addCell(note);
					sheet.addCell(conf);
					sheet.addCell(prezzo);
					sheet.addCell(valuta);
					sheet.addCell(consegna);
					
					//ESTRAZIONE E SCRITTURA DEI DATI:
					Iterator it=dati[s].iterator();
					int j=2;
					while(it.hasNext()){
						int i=(Integer)it.next();
						Label pezzi = new Label(0, j, GestioneOrdini.numpezzi[i].getText());
						Label desc = new Label(2,j, GestioneOrdini.labeldescr[i].getText());
						Label cod = new Label(3,j, GestioneOrdini.labelcod[i].getText());
						Label lot=new Label(1,j,GestioneOrdini.labelquant[i].getText());
						Label cons=new Label(5,j,GestioneOrdini.consegna[i].getText());
						sheet.addCell(pezzi);
						sheet.addCell(cod);
						sheet.addCell(desc);
						sheet.addCell(lot);
						sheet.addCell(cons);
						j++;
					}	
				}
			} //chiusura ciclo for fornitori
			
			workbook.write(); //scrive tutto sul file
			workbook.close(); //chiude il file excel
			GestioneOrdini.comunicazioni.setText("File creato con successo");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} 
	}
}
