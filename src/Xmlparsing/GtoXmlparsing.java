package Xmlparsing;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import javax.swing.text.html.parser.TagElement;


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
			StringBuilder level3 = new StringBuilder();
			String attributlevel1 =null;
			String attributlevel2 =null;
			String attributlevel3 =null;
			String taglevel1=null;
			String taglevel2 = null;
			String taglevel3 = null;

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
						level1.append(level2+"</"+taglevel2.toLowerCase() +">\n</"+ taglevel1.toLowerCase()+">\n");
						level2.delete(0,level2.length());
						taglevel2=null;
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
					if (taglevel2!=null) {
						System.out.println("tag in this level is " + taglevel2);
						level2.append(level3+"</"+taglevel2.toLowerCase()+">\n");
						System.out.println("contenu de level 2 "+level2);
						level3.delete(0,level3.length());
					}
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
						level2.append(">");
					else 
						level2.append(">"+contents);
						
					System.out.println("balise level2" + level2);	
					
					break;
				case "2" : 
					i++;
					System.out.println("is in level 3" +lineWorlds[i] );					
					taglevel3 = lineWorlds[i].toLowerCase();
					String content = null;
					for (i++;i<lineWorlds.length;i++){
						if (lineWorlds[i].startsWith("@") ){
							attributlevel3 = " id=\""+lineWorlds[i].toLowerCase()+"\"";
						}else {
							if (content==null)
								content = lineWorlds[i].toLowerCase()  ;
							else 
								content = content + lineWorlds[i].toLowerCase()  ;
						}
						
					}
					level3.append("<" + taglevel3 );
					if (attributlevel3!=null)
						level3.append(attributlevel3);
					if (content==null)
						level3.append("></"+ taglevel3+">\n");
					else 
						level3.append(">"+content + "></"+taglevel3+">\n");
					System.out.println("balise level3 " + level3);	
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
