package supply_manager;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import java.net.*;


public class GestioneOrdini extends JFrame implements ActionListener, ItemListener {
	
	private static final long serialVersionUID = 1L;
	
	static final int NUMFORN=50;
	static JPanel records;
	static JLabel comunicazioni;
	static boolean richiesteoff=true;
	static final int MAXLETTURE = 50;
	private JRadioButton[] radiobutton;
	int letture=0;
	
	static JTextField[] numpezzi=new JTextField[MAXLETTURE]; //quantità necessaria
	static JLabel[] labelcod= new JLabel[MAXLETTURE];
	static JTextField[] labeldescr= new JTextField[MAXLETTURE];
	static JTextField[] labelquant= new JTextField[MAXLETTURE]; //quantità da ordinare
	static ArrayList<JTextField> forni1 = new ArrayList();
	static ArrayList<JTextField> forni2 = new ArrayList();
	static ArrayList<JTextField> forni3 = new ArrayList();
	static ArrayList<JTextField> forni4 = new ArrayList();
	static JTextField[] consegna= new JTextField[MAXLETTURE];
	
	static ArrayList<Integer>[] dati= new ArrayList[NUMFORN];
	
	
	
	public static void main(String[] args) {
		
		GestioneOrdini frame= new GestioneOrdini();
		frame.setVisible(true);	
	}
	
	public GestioneOrdini(){
		Container contentPane;
		setSize(900,600);
		setTitle("© Supply Manager 1.1");
		JPanel intestazione= new JPanel();
		JPanel letture= new JPanel();
		JPanel controlpanel=new JPanel();
		JPanel info=new JPanel();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//LAYOUT GENERALE DEL CONTENITORE DEL FRAME TOTALE
		contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout(5,5));
		contentPane.add(intestazione, BorderLayout.NORTH);
		contentPane.add(letture, BorderLayout.CENTER);
		contentPane.add(controlpanel, BorderLayout.EAST);
		contentPane.add(info, BorderLayout.SOUTH);
		
		//PANNELLO INTESTAZIONE IN ALTO CON INFORMAZIONI
		intestazione.setLayout(new BorderLayout());
		intestazione.setBorder(BorderFactory.createEtchedBorder());
			JLabel testa1=new JLabel();
			testa1.setText("Gestione approvvigionamenti.");
			intestazione.add(testa1,BorderLayout.NORTH);
			JLabel testa2=new JLabel();
			testa2.setText("Seleziona il campo 'Aggiungi Componente' e leggi il codice a barre tramite la penna ottica. In alternativa inserisci il codice manualmente e premi invio.");
			intestazione.add(testa2, BorderLayout.SOUTH);
		
		//PANNELLO LETTURE CENTRALE
		letture.setLayout(new BorderLayout(5,0));
		letture.setBorder(BorderFactory.createLoweredBevelBorder());
			//PANNELLO NOMI DELLE COLONNE
			JPanel topletture = new JPanel();
			topletture.setLayout(new FlowLayout(FlowLayout.LEFT));
			letture.add(topletture,BorderLayout.NORTH);
			topletture.setBorder(BorderFactory.createEtchedBorder());
			JLabel numpezzi=new JLabel("Qt necessaria          ");
			topletture.add(numpezzi);
			JLabel codice =new JLabel(" Codice         ");
			topletture.add(codice);
			JLabel descrizione =new JLabel("      Descrizione                                                                                             ");
			topletture.add(descrizione);
			JLabel quantita =new JLabel("                                                                                                                                                                                                      Qt da Ordinare");
			topletture.add(quantita);
			JLabel fornitori =new JLabel("                               Fornitori scelti              ");
			topletture.add(fornitori);
			JLabel consegna =new JLabel("       Consegna");
			topletture.add(consegna);
				
			//PANNELLO CHE VISUALIZZERA' I COMPONENTI LETTI 
			records = new JPanel();
			records.setBackground(Color.LIGHT_GRAY);
			records.setLayout(new GridLayout(50,1));
			JScrollPane scroll=new JScrollPane(records);
			letture.add(scroll,BorderLayout.CENTER);

				
		//PANNELLO DI CONTROLLO SULLA DESTRA
		controlpanel.setLayout(new BorderLayout());
		controlpanel.setBorder(BorderFactory.createRaisedBevelBorder());
			
			//PANNELLO IN ALTO A DESTRA PER AGGIUNGERE COMPONENTE
			JPanel aggiungi = new JPanel();
			aggiungi.setLayout(new GridLayout(2,1));
			controlpanel.add(aggiungi, BorderLayout.NORTH);
				JLabel Laggiungi=new JLabel();
				Laggiungi.setText("Aggiungi Componente: ");
				aggiungi.add(Laggiungi);
				
				JTextField aggiungiOggetto=new JTextField();
				aggiungiOggetto.setColumns(5);
				aggiungi.add(aggiungiOggetto);
				aggiungiOggetto.addActionListener(this);
			//PANNELLO COMUNICAZIONI COMPONENTI
			JPanel comunicaz=new JPanel();
			comunicaz.setLayout(new FlowLayout());
			GestioneOrdini.comunicazioni=new JLabel("");
			comunicaz.add(GestioneOrdini.comunicazioni);
			controlpanel.add(comunicaz,BorderLayout.CENTER);
			
			//PANNELLO BOTTONI (RADIO+CREA) IN BASSO A DESTRA
			JPanel buttons=new JPanel();
			buttons.setLayout(new BorderLayout());
			controlpanel.add(buttons,BorderLayout.SOUTH);
				//RADIOBUTTON
				ButtonGroup radio =new ButtonGroup();
				JPanel radiopanel=new JPanel();
				radiopanel.setLayout(new FlowLayout());
				radiopanel.setBorder(BorderFactory.createTitledBorder("Tipo di documento"));
				radiobutton= new JRadioButton[2];
				radiobutton[0]= new JRadioButton("Richiesta d'offerta");
				radiobutton[0].addItemListener(this);
				radiobutton[1]= new JRadioButton("Ordine");
				radiobutton[1].addItemListener(this);
				radio.add(radiobutton[0]);
				radio.add(radiobutton[1]);
				radiopanel.add(radiobutton[0]);
				radiopanel.add(radiobutton[1]);
				buttons.add(radiopanel,BorderLayout.CENTER);
				radiobutton[0].setSelected(true);
	
				JButton Crea = new JButton("CREA");
				buttons.add(Crea, BorderLayout.SOUTH);
				Crea.addActionListener(this);
				
		//PANNELLO INFO IN BASSO:
		info.setLayout(new FlowLayout(FlowLayout.CENTER));
		info.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel matteo=new JLabel(); 
		matteo.setText("©2013 Supply Manager 1.1     Designed by");
		info.add(matteo);
		JLabel link = new JLabel("Matteo Stefanini");
		 link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		 link.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent e) {
		      if (e.getClickCount() > 0) {
		          if (Desktop.isDesktopSupported()) {
		                Desktop desktop = Desktop.getDesktop();
		                try {
		                    URI uri = new URI("mailto:info@matteostefanini.it");
		                    desktop.browse(uri);
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                } catch (URISyntaxException ex) {
		                    ex.printStackTrace();
		                }
		        }
		      }
		   }
		 });
		 info.add(link);			
	}

	
	
	//METODO DI RISPOSTA AGLI EVENTI
	public void actionPerformed(ActionEvent event) {
		//EVENTO JTextField:
		if (event.getSource() instanceof JTextField) {
			JTextField cod = (JTextField) event.getSource();
			String codiceInserito = cod.getText();
			cod.setText(null);
			if(codiceInserito.isEmpty())
				GestioneOrdini.comunicazioni.setText("Codice non valido");
			else{
				letture++;
				if(letture<MAXLETTURE-1){
					Lettura lettura = new Lettura(codiceInserito, letture);
					Record record=new Record(lettura,letture,codiceInserito);
					GestioneOrdini.comunicazioni.setText("Ultimo codice inserito: "+codiceInserito);
				}else if(letture==MAXLETTURE-1){
					Lettura lettura = new Lettura(codiceInserito, letture);
					Record record=new Record(lettura,letture,codiceInserito);
					GestioneOrdini.comunicazioni.setText("Inserire ultimo componente");
				}else if(letture==MAXLETTURE){
					Lettura lettura = new Lettura(codiceInserito, letture);
					Record record=new Record(lettura,letture,codiceInserito);
					GestioneOrdini.comunicazioni.setText("Limite componenti raggiunto.");
					} else
						GestioneOrdini.comunicazioni.setText("Limite componenti raggiunto.");
			}
		}// fine evento JTextField
		
		
		//EVENTO JButton:
		else 
			if(event.getSource() instanceof JButton){
				
				if (radiobutton[0].isSelected()){GestioneOrdini.richiesteoff=true;}//RICHIESTE D'OFFERTA ON
				else {GestioneOrdini.richiesteoff=false;}  //ORDINI ON
				
				//Inizializzo vettore dati:
				for(int n=0;n<NUMFORN;n++){
					dati[n]=new ArrayList();
				}
				
				Iterator it;
				boolean stato=true;
				
				//CICLO ACQUISIZIONE DATI DA FORNI1:
				it=GestioneOrdini.forni1.iterator();
				while(it.hasNext()){
					JTextField O=(JTextField)it.next();
					if(O.getText().equalsIgnoreCase("")){}
					else{
						int numfornitore=Integer.parseInt(O.getText());
						int riga=GestioneOrdini.forni1.indexOf(O);
						if (numfornitore<=NUMFORN)
							GestioneOrdini.dati[numfornitore-1].add(riga);
						else{
							GestioneOrdini.comunicazioni.setText("Fornitore inesistente alla riga "+(riga+1));
							stato=false;
						}
					}
				} 
				//FORNI2:
				it=GestioneOrdini.forni2.iterator();
				while(it.hasNext()){
					JTextField O=(JTextField)it.next();
					if(O.getText().equalsIgnoreCase("")){}
					else{
						int numfornitore=Integer.parseInt(O.getText());
						int riga=GestioneOrdini.forni2.indexOf(O);
						if (numfornitore<=NUMFORN)
							GestioneOrdini.dati[numfornitore-1].add(riga);
						else{
							GestioneOrdini.comunicazioni.setText("Fornitore inesistente alla riga "+(riga+1));
							stato=false;
						}
					}
				} 
				//FORNI3:
				it=GestioneOrdini.forni3.iterator();
				while(it.hasNext()){
					JTextField O=(JTextField)it.next();
					if(O.getText().equalsIgnoreCase("")){}
					else{
						int numfornitore=Integer.parseInt(O.getText());
						int riga=GestioneOrdini.forni3.indexOf(O);
						if (numfornitore<=NUMFORN)
							GestioneOrdini.dati[numfornitore-1].add(riga);
						else{
							GestioneOrdini.comunicazioni.setText("Fornitore inesistente alla riga "+(riga+1));
							stato=false;
						}
					}
				} 
				//FORNI4:
				it=GestioneOrdini.forni4.iterator();
				while(it.hasNext()){
					JTextField O=(JTextField)it.next();
					if(O.getText().equalsIgnoreCase("")){}
					else{
						int numfornitore=Integer.parseInt(O.getText());
						int riga=GestioneOrdini.forni4.indexOf(O);
						if (numfornitore<=NUMFORN)
							GestioneOrdini.dati[numfornitore-1].add(riga);
						else{
							GestioneOrdini.comunicazioni.setText("Fornitore inesistente alla riga "+(riga+1));
							stato=false;
						}
					}
				} 
				//INVIO DATI PER OUTPUT:
				if (stato){
					Documento doc = new Documento(richiesteoff,dati);
				}
			}//fine evento JButton
	}// fine actionPerformed
	
	
	
	public void itemStateChanged(ItemEvent event){
		
	}
	
	
}
