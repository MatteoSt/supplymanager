package supply_manager;

import java.sql.*;


public class Lettura {

	static final String DATABASE_URL = "jdbc:odbc:namedatabase";
	
	private String descrizione;
	private double quantitaconf;
	private String user="";
	private String password="";
	
	
	public Lettura(String cod, int letture){
		int codice=Integer.parseInt(cod);
		Connection connection= null;
		Statement statement = null;
		
		try{
			connection = DriverManager.getConnection(DATABASE_URL,"user","password");
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT Description,ReorderingLotSize FROM MA_Items JOIN MA_ItemsGoodsData ON MA_Items.Item=MA_ItemsGoodsData.Item  WHERE MA_Items.Item='"+cod+"'");
			while(resultSet.next()){
			this.descrizione=resultSet.getString("Description");
			this.quantitaconf=resultSet.getFloat("ReorderingLotSize");
			}
		}
		catch (SQLException sqlException){
			sqlException.printStackTrace();
			System.exit(1);
		}
		finally{
			try{
				statement.close();
				connection.close();
			}
			catch (Exception exception){
				exception.printStackTrace();
				System.exit(1);
			}
		}
		
	}
	
	
	public String getDescrizione(){
		return this.descrizione;
	}
	
	public double getQuantitaconf(){
		return this.quantitaconf;
	}

}
