package Xmlparsing;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


public class GtoXmlparsing {

	/**
	 * 
	 * @param args
	 */

	public void parsingToXml(String file) {

		System.out.println("debut de parsing ....");
		StringBuilder root = new StringBuilder();
		root.append("<?xml version='1.0' encoding='ISO-8859-1' standalone='yes' ?>\n");
		root.append("<root>\n");

		try {

			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			
			FileOutputStream fstreamout = new FileOutputStream("resultat.xml");
			DataOutputStream out = new DataOutputStream(fstreamout);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			StringBuilder level1 = new StringBuilder();
			StringBuilder level2 = new StringBuilder();
			String taglevel1=null;
			String attributlevel1 =null;
			String attributlevel2 =null;
			String taglevel2 = null;

			while ((strLine = br.readLine()) != null) {

				int i = 0;
				String[] lineWorlds = strLine.split(" ");
				
				if (lineWorlds.length<=1 )
					continue;
				else if (lineWorlds[i].hashCode()==0)
					for(;lineWorlds[i].hashCode()==0;i++);
				
				switch (lineWorlds[i]){
				case "0" : 
					i++;
					if (taglevel1!=null) {
						System.out.println("tag in this level is " + taglevel1);
						level1.append(level2+"</"+taglevel1.toLowerCase()+">\n");
						level2.delete(0,level2.length());
					}
				
					if (lineWorlds[i].equals("HEAD") || lineWorlds[i].equals("TRLR") ) 
						continue;
					System.out.println("level 0");
					if (lineWorlds[i].startsWith("@") ){
						attributlevel1 = "id=\""+lineWorlds[i].toLowerCase()+"\"";i++;
					}
					taglevel1 = lineWorlds[i];
					level1.append("<"+lineWorlds[i].toLowerCase()+ " "+ attributlevel1 + ">\n");
					System.out.println("balise level1" + level1);	
					break;
				case "1" :
					
					i++;
					System.out.println("level 1");
					taglevel2 = lineWorlds[i].toLowerCase();
					String contents = null;
					for (i++;i<lineWorlds.length;i++){
						if (lineWorlds[i].startsWith("@") ){
							attributlevel2 = " id=\""+lineWorlds[i].toLowerCase()+"\"";
						}else {
							if (contents==null)
								contents = lineWorlds[i].toLowerCase()  ;
							else 
								contents = contents + lineWorlds[i].toLowerCase()  ;
						}
						
					}
					level2.append("<" + taglevel2 );
					if (attributlevel2!=null)
						level2.append(attributlevel2);
					if (contents==null)
						level2.append("></"+taglevel2+">\n");
					else 
						level2.append(">"+contents+"</"+taglevel2+">\n");
						
					System.out.println("balise level2" + level2);	
					
					break;
				}
	
			}

			in.close();
			root.append(level1+"\n</root>");
			out.writeBytes(root.toString());
			
			System.out.println("le fichier gedom parser vers XML\n" + root.toString());

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		GtoXmlparsing parseur = new GtoXmlparsing();

		parseur.parsingToXml(args[0]);

	}

}
