package supply_manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Record implements ActionListener {

	private String descrizione;
	private double quantitaconf;
	private static JButton[] elimina=new JButton[40];
	private int rletture;
	
	//COSTRUTTORE NUOVA LETTURA 
	public Record(Lettura lettura,int letture,String codiceInserito){
		
		this.descrizione=lettura.getDescrizione();
		this.quantitaconf=lettura.getQuantitaconf();
		
		this.rletture=letture;
		
		//PANNELLO RECORD TOTALE
		JPanel record = new JPanel();
		record.setLayout(new BorderLayout(5,0));
		record.setBorder(BorderFactory.createRaisedBevelBorder());
		
			//IMPOSTAZIONE DELLE CARATERISTICHE DEI CAMPI
		
			//NUM PEZZI ED ELIMINA
			GestioneOrdini.numpezzi[letture-1]=new JTextField();
			GestioneOrdini.numpezzi[letture-1].setColumns(9);
			record.add(GestioneOrdini.numpezzi[letture-1],BorderLayout.WEST);
			
			elimina[letture-1]=new JButton("Elimina");
			record.add(elimina[letture-1],BorderLayout.EAST);
			elimina[letture-1].addActionListener(this);
			
			
			//CODICE E CONSEGNA
			JPanel central1=new JPanel();
			central1.setLayout(new BorderLayout(5,0));
			//central1.setBorder(BorderFactory.createRaisedBevelBorder());
			record.add(central1,BorderLayout.CENTER);
			
			
			GestioneOrdini.labelcod[letture-1]=new JLabel("     "+correctCod(codiceInserito));
			central1.add(GestioneOrdini.labelcod[letture-1],BorderLayout.WEST);
			GestioneOrdini.consegna[letture-1]=new JTextField();
			GestioneOrdini.consegna[letture-1].setColumns(10);
			central1.add(GestioneOrdini.consegna[letture-1],BorderLayout.EAST);
			
			//DESCRIZIONE, LOTTO E FORNITORI
			JPanel central2=new JPanel();
			central2.setLayout(new BorderLayout(5,0));
			central1.add(central2,BorderLayout.CENTER);
			
			GestioneOrdini.labeldescr[letture-1]=new JTextField(lettura.getDescrizione());
			central2.add(GestioneOrdini.labeldescr[letture-1],BorderLayout.CENTER);
			
			//PANNELLO LOTTO E FORNITORI
			JPanel lotforn =new JPanel();
			lotforn.setLayout(new BorderLayout(40,0)/*new GridLayout(1,2)*/);
			//GestioneOrdini.labelquant[letture-1]=new JLabel(Double.toString(lettura.getQuantitaconf()));
			GestioneOrdini.labelquant[letture-1]= new JTextField("");
			GestioneOrdini.labelquant[letture-1].setColumns(9);
			lotforn.add(GestioneOrdini.labelquant[letture-1],BorderLayout.WEST);
			
			JPanel fornit=new JPanel();
			fornit.setLayout(new GridLayout(1,5));
			
			GestioneOrdini.forni1.add(letture-1, new JTextField());
			GestioneOrdini.forni1.get(letture-1).setColumns(2);
			fornit.add(GestioneOrdini.forni1.get(letture-1));
			GestioneOrdini.forni2.add(letture-1, new JTextField());
			GestioneOrdini.forni2.get(letture-1).setColumns(2);
			fornit.add(GestioneOrdini.forni2.get(letture-1));
			GestioneOrdini.forni3.add(letture-1, new JTextField());
			GestioneOrdini.forni3.get(letture-1).setColumns(2);
			fornit.add(GestioneOrdini.forni3.get(letture-1));
			GestioneOrdini.forni4.add(letture-1, new JTextField());
			GestioneOrdini.forni4.get(letture-1).setColumns(2);
			fornit.add(GestioneOrdini.forni4.get(letture-1));
			
			JLabel spazio=new JLabel("       ");
			fornit.add(spazio);
			lotforn.add(fornit,BorderLayout.EAST);
			
			central2.add(lotforn,BorderLayout.EAST);
		
		GestioneOrdini.records.add(record);
	}
	
	
	//METODO PER LUNGHEZZA FISSA STRINGA CODICE
	private String correctCod(String codiceInserito){
		int num=codiceInserito.length();
		for(int i=0;i<(13-num);i++){
			codiceInserito+="  ";
		}
		return codiceInserito;	
	}
	
	
	//COSTRUTTORE MESSAGGIO ERRORE
	public Record(String errore){
		
	}
	
	
	public void actionPerformed(ActionEvent event) {
		JButton elimina=(JButton) event.getSource();
		GestioneOrdini.numpezzi[rletture-1].setText(null);
		GestioneOrdini.labeldescr[rletture-1].setText("COMPONENTE RIMOSSO");
		GestioneOrdini.labelquant[rletture-1].setText(null);
		GestioneOrdini.consegna[rletture-1].setText(null);
		GestioneOrdini.forni1.get(rletture-1).setText(null);
		GestioneOrdini.forni2.get(rletture-1).setText(null);
		GestioneOrdini.forni3.get(rletture-1).setText(null);
		GestioneOrdini.forni4.get(rletture-1).setText(null);
		elimina.setBackground(Color.BLUE);
	}
	
	
	
}//chiusura classe
