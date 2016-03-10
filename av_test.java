package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Testjava {
	public static void main(String[] args){
		//generetor
		long g = 2;
		//plithos psifoforwn
		int n = 5;
		
		long[] xi = new long[n];
		
		Random r = new Random();
		long temp;
		
		boolean veto = false; // allakste to se true gia na exei veto
		
		if(!veto){//paradigma xwris veto
			
			//ekdidoume ta xi me g = 2 gia osous eipan nai
			//gia na einai eukolo na epalitheusoume ta xi
			//einai ola enas arithmos pou grafete os dunami tou 2
			for(int i=0; i < n; i++){
				temp = r.nextInt(30);
				if(temp == 0) temp = temp + 1;
				xi[i] = (long) Math.pow(g, temp);
				System.out.println("x" + (i+1) + "=" + xi[i]);
			}
		}
		else{//paradigma me veto
			
			
			for(int i=0; i < n-1; i++){
				temp = r.nextInt(30);
				if(temp == 0) temp = temp + 1;
				System.out.println("temp" + (i+1) + "=" + temp);
				xi[i] = (long) Math.pow(g, temp);
				System.out.println("x" + (i+1) + "=" + xi[i]);
			}
			//ekdidoume ri gia to paradigma oti kapoios eipe oxi
			//tuxeos arithmos pol/smenos me mono arithmo gia na
			//min mporei na graftei os 2 se kapoia dunami
			temp = r.nextInt(30);
			xi[4] = temp * 3;
		}
		long c;
		long rand;
		long t;
		long s;
		
		//dimiourgia twn yi san metablites kai oxi san arithmous gia
		//na min kanoume prakseis kathe grami tou array einai ena yi
		List<String> Σxiyi = new ArrayList<String>();
		String[][] yi = new String[n][n];
		for(int i=0; i < n; i++){
			int count = n-i;
			count = n - count;
			for(int j=0; j < n; j++){
				if(i == j){
					yi[i][j] = "";
				}
				else{
					if(count > 0){
						yi[i][j] = "+x"+(j+1);
						count--;
					}
					else yi[i][j] = "-x"+(j+1);
				}
			}
		}
		//epalitheusi twn xi me knowlegde proof algorithmo
		//xrisimopoiithike to schnorr protocol https://en.wikipedia.org/wiki/Proof_of_knowledge
		// g = 2 opote gia na einai dektos enas arithmos
		// prepei na mporei na graftei os dunami tou 2
		for(int i=0; i < n; i++){
			c = r.nextInt(10);
			if(c == 0) c = c + 1;
			rand = r.nextInt(10);
			if(rand == 0) rand = rand + 1;
			t = (long) Math.pow(g, rand);
			s =  (rand + c * binlog(xi[i]));
			
			// dimiourgia tou Σciyi gia to Πg^ciyi os mia lista opoy kathe antikimeno einai ena cixi
			//pou prokiptei apo tin epimeristiki tou ciyi
			//kai apikonizou metablites pali gia na apofigoume prakseis
			//logo oti oi arithmoi einai poli megali
			
			if(Math.pow(g, s) == t*(Math.pow(xi[i], c))){//ci = xi
				for(int j=0; j < n; j++){
					if(!"".equals(yi[i][j])){
						String txt = yi[i][j];
						String sign = txt.substring(0,1);
						String newtxt = txt.substring(2);
						int x = Integer.parseInt(newtxt);
						if((i+1) < x){
							txt = sign + "x"+(i+1) + "x" + x;
							System.out.println( txt + " list item" );
						}
						else{
							txt = yi[i][j] + "x"+(i+1);
							System.out.println(txt + " list item" );
						}
						Σxiyi.add(txt);
					}
				}
			}//ci = ri
			else{
				for(int j=0; j < n; j++){
					if(!"".equals(yi[i][j])){
						String txt = yi[i][j];
						String sign = txt.substring(0,1);
						String newtxt = txt.substring(2);
						int x = Integer.parseInt(newtxt);
						if((i+1) < x){
							txt = sign + "r"+(i+1) + "x" + x;
							System.out.println( txt + " list item" );
						}
						else{
							txt = yi[i][j] + "r"+(i+1);
							System.out.println( txt + " list item" );
						}
						Σxiyi.add(txt);
					}
				}
			}
		}
		
		//ipologismos tou Σciyi me aplopoiisi twn antithetwn
		//opou an ta stoixia sti lista einai kaina Σciyi = 0
		for(int i=0; i < Σxiyi.size(); i++){
			if(!"".equals(Σxiyi.get(i))){
				String txt = Σxiyi.get(i);
				if("-".equals(txt.substring(0,1)))txt = "+" + txt.substring(1);
				else txt = "-" + txt.substring(1,txt.length());
				for(int j=0; j < Σxiyi.size(); j++){
					if(txt.equals(Σxiyi.get(j))){
						Σxiyi.set(i, "");
						Σxiyi.set(j, "");
						break;
					}
				}
			}
		}
		//apotelesma an Σciyi = 0 tote Πg^ciyi = 1 ara vote passed 
		//alliws != 1 opote exoume veto
		int count = 0;
		for(int i=0; i < Σxiyi.size(); i++){
			if(!"".equals(Σxiyi.get(i))) count++;
		}
		
		if(count > 0) System.out.println("veto");
		else System.out.println("vote passed");
	}
	
	// funtion pou epistrefei to log me basi 2 se akaireo typou long
	private static long binlog( long bits ) 
	{
		long log = 0;
	    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
	    if( bits >= 256 ) { bits >>>= 8; log += 8; }
	    if( bits >= 16  ) { bits >>>= 4; log += 4; }
	    if( bits >= 4   ) { bits >>>= 2; log += 2; }
	    return log + ( bits >>> 1 );
	}
}