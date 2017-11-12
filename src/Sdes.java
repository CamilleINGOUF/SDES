import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;

public class Sdes 
{
	
	public static final int[][] S0 = {{1,0,3,2},{3,2,1,0},{0,2,1,3},{3,1,3,2}};
	public static final int[][] S1 = {{0,1,2,3},{2,0,1,3},{3,0,1,0},{2,1,0,3}};
	
	public static BitSet[] createKeys(BitSet k)
	{
		BitSet[] ks = new BitSet[2];
		
		ks[0] = new BitSet(8);
		ks[1] = new BitSet(8);
		
		BitSet kP10 = P10(k);
		
		//k1
		BitSet ls1 = LSOnEachHalf(kP10, 1);
		ks[0] = (BitSet) P8(ls1).clone();
		
		//k2
		BitSet ls2 = LSOnEachHalf(ls1, 2);
		ks[1] = (BitSet) P8(ls2).clone();
		
		return ks;
	}
	
	public static BitSet P10(BitSet b)
	{
		BitSet p10 = new BitSet(10);
		
		if(b.get(0))p10.set(6);
		if(b.get(1))p10.set(2);
		if(b.get(2))p10.set(0);
		if(b.get(3))p10.set(4);
		if(b.get(4))p10.set(1);
		if(b.get(5))p10.set(9);
		if(b.get(6))p10.set(3);
		if(b.get(7))p10.set(8);
		if(b.get(8))p10.set(7);
		if(b.get(9))p10.set(5);
		
		return p10;
	}
	
	public static BitSet P8(BitSet b)
	{
		BitSet p8 = new BitSet(8);
		
		if(b.get(2)) p8.set(1);
		if(b.get(3)) p8.set(3);
		if(b.get(4)) p8.set(5);
		if(b.get(5)) p8.set(0);
		if(b.get(6)) p8.set(2);
		if(b.get(7)) p8.set(4);
		if(b.get(8)) p8.set(7);
		if(b.get(9)) p8.set(6);
		
		return p8;
	}
	
	public static BitSet LSOnEachHalf(BitSet b, int times)
	{
		BitSet ls = (BitSet) b.clone();
		
		for(int i = 0; i < times; i++)
		{
			//first half
			if(b.get(0)) ls.set(4); else ls.clear(4);
			if(b.get(1)) ls.set(0); else ls.clear(0);
			if(b.get(2)) ls.set(1); else ls.clear(1);
			if(b.get(3)) ls.set(2); else ls.clear(2);
			if(b.get(4)) ls.set(3); else ls.clear(3);
			
			//second half
			if(b.get(5)) ls.set(9); else ls.clear(9);
			if(b.get(6)) ls.set(5); else ls.clear(5);
			if(b.get(7)) ls.set(6); else ls.clear(6);
			if(b.get(8)) ls.set(7); else ls.clear(7);
			if(b.get(9)) ls.set(8); else ls.clear(8);
			b = (BitSet) ls.clone();
		}
		
		return ls;
	}
	
	public static BitSet ip(BitSet b)
	{
		BitSet bt = new BitSet(8);
		if(b.get(0)) bt.set(3);
		if(b.get(1)) bt.set(0);
		if(b.get(2)) bt.set(2);
		if(b.get(3)) bt.set(4);
		if(b.get(4)) bt.set(6);
		if(b.get(5)) bt.set(1);
		if(b.get(6)) bt.set(7);
		if(b.get(7)) bt.set(5);
		return bt;
	}
	
	public static BitSet ep(BitSet b)
	{
		BitSet bt = new BitSet(8);
		
		if(b.get(0))
		{
			bt.set(1);
			bt.set(7);
		}
		
		if(b.get(1))
		{
			bt.set(2);
			bt.set(4);
		}
		
		if(b.get(2))
		{
			bt.set(3);
			bt.set(5);
		}
		
		if(b.get(3))
		{
			bt.set(0);
			bt.set(6);
		}
		
		return bt;
	}
	
	public static BitSet s(BitSet b)
	{
		BitSet bt = new BitSet(4);
		
		BitSet g = new BitSet(4);
		BitSet d = new BitSet(4);
		
		if(b.get(0)) g.set(0);
		if(b.get(1)) g.set(1);
		if(b.get(2)) g.set(2);
		if(b.get(3)) g.set(3);
		if(b.get(4)) d.set(0);
		if(b.get(5)) d.set(1);
		if(b.get(6)) d.set(2);
		if(b.get(7)) d.set(3);
		
		int x= 0;
		int y = 0;
		
		int s0 = 0;
		int s1 = 0;
		
		if(g.get(0)) x+=2;
		if(g.get(3)) x++;
		if(g.get(1)) y+=2;
		if(g.get(2)) y++;
		
		s0 += S0[x][y];
		
		x= 0;
		y = 0;
		
		if(d.get(0)) x+=2;
		if(d.get(3)) x++;
		if(d.get(1)) y+=2;
		if(d.get(2)) y++;
		
		s1 += S1[x][y];
		
		if(s0 % 2 == 1) bt.set(1);
		if(s0 > 1) bt.set(0);
		if(s1 % 2 == 1) bt.set(3);
		if(s1 > 1) bt.set(2);
		
//		printBits("gs    = ", g, 4);
//		System.out.println("s0    = "+s0);
//		printBits("ds    = ", d, 4);
//		System.out.println("s1    = "+s1);
		
		return bt;
	}
	
	public static BitSet p4(BitSet n)
	{
		BitSet p4 = new BitSet(4);
		
		if(n.get(0)) p4.set(3);
		if(n.get(1)) p4.set(0);
		if(n.get(2)) p4.set(2);
		if(n.get(3)) p4.set(1);
		
		return p4;
	}
	
	public static BitSet f(BitSet b,BitSet k)
	{
		BitSet bt;
		
		BitSet ep = Sdes.ep(b);
//		printBits("EP(A) = ", ep, 8);
		ep.xor(k);
//		printBits("k     = ", k, 8);
//		printBits("ep    = ", ep, 8);
		
		BitSet s = s(ep);
//		printBits("s     = ", s, 4);
		
		bt = p4(s);
//		printBits("p4    = ", bt, 4);
		
		return bt;
	}
	
	public static BitSet fk(BitSet b,BitSet k)
	{
		BitSet bt = new BitSet(8);
		
		BitSet G = new BitSet(4);
		BitSet D = new BitSet(4);
		
		if(b.get(0)) G.set(0);
		if(b.get(1)) G.set(1);
		if(b.get(2)) G.set(2);
		if(b.get(3)) G.set(3);
		if(b.get(4)) D.set(0);
		if(b.get(5)) D.set(1);
		if(b.get(6)) D.set(2);
		if(b.get(7)) D.set(3);
		
		BitSet f = f(D,k);
//		printBits("f(A)  = ", f, 4);
		G.xor(f);
		
		if(G.get(0)) bt.set(0);
		if(G.get(1)) bt.set(1);
		if(G.get(2)) bt.set(2);
		if(G.get(3)) bt.set(3);
		if(D.get(0)) bt.set(4);
		if(D.get(1)) bt.set(5);
		if(D.get(2)) bt.set(6);
		if(D.get(3)) bt.set(7);
		
		return bt;
	}
	
	public static BitSet sw(BitSet b)
	{
		BitSet sw = new BitSet(8);
		
		BitSet G = new BitSet(4);
		BitSet D = new BitSet(4);
		
		if(b.get(0)) G.set(0);
		if(b.get(1)) G.set(1);
		if(b.get(2)) G.set(2);
		if(b.get(3)) G.set(3);
		if(b.get(4)) D.set(0);
		if(b.get(5)) D.set(1);
		if(b.get(6)) D.set(2);
		if(b.get(7)) D.set(3);
		
		if(G.get(0)) sw.set(4);
		if(G.get(1)) sw.set(5);
		if(G.get(2)) sw.set(6);
		if(G.get(3)) sw.set(7);
		if(D.get(0)) sw.set(0);
		if(D.get(1)) sw.set(1);
		if(D.get(2)) sw.set(2);
		if(D.get(3)) sw.set(3);
		
		return sw;
	}
	
	public static BitSet ip1(BitSet b)
	{
		BitSet bt = new BitSet(8);
		if(b.get(0)) bt.set(1);
		if(b.get(1)) bt.set(5);
		if(b.get(2)) bt.set(2);
		if(b.get(3)) bt.set(0);
		if(b.get(4)) bt.set(3);
		if(b.get(5)) bt.set(7);
		if(b.get(6)) bt.set(4);
		if(b.get(7)) bt.set(6);
		return bt;
	}
	
	public static BitSet cryptLetterSDES(BitSet binaryLetter, BitSet key)
	{
		BitSet[] keys = createKeys(key);
		
		BitSet cryptedLetter = Sdes.ip(binaryLetter);
		
		cryptedLetter = Sdes.fk(cryptedLetter, keys[0]);
		
		cryptedLetter = Sdes.sw(cryptedLetter);
		
		cryptedLetter = Sdes.fk(cryptedLetter, keys[1]);
		
		cryptedLetter = Sdes.ip1(cryptedLetter);
		
		return cryptedLetter;
	}
	
	@SuppressWarnings("resource")
	public static String readFile(String filename) throws FileNotFoundException
	{
		String str = new Scanner(new File(filename)).useDelimiter("\\Z").next();
		return str;
	}
	
	public static BitSet charToBitSet8(char ch)
	{
		BitSet eightB = new BitSet(8);
		
		int valueLetter = (int) ch;
//		System.out.println("Value : "+valueLetter);
		
		if(valueLetter % 2 == 1) eightB.set(7);
		
		if(valueLetter % 128 != valueLetter)
		{
			valueLetter -= 128;
			eightB.set(0);
		}
		
		if(valueLetter % 64 != valueLetter)
		{
			valueLetter -= 64;
			eightB.set(1);
		}
		
		if(valueLetter % 32 != valueLetter)
		{
			valueLetter -= 32;
			eightB.set(2);
		}
		
		if(valueLetter % 16 != valueLetter)
		{
			valueLetter -= 16;
			eightB.set(3);
		}
		
		if(valueLetter % 8 != valueLetter)
		{
			valueLetter -= 8;
			eightB.set(4);
		}
		
		if(valueLetter % 4 != valueLetter)
		{
			valueLetter -= 4;
			eightB.set(5);
		}
		
		
		if(valueLetter % 2 != valueLetter)
		{
			valueLetter -= 2;
			eightB.set(6);
		}
		
		return eightB;
	}
	
	public static ArrayList<BitSet> StringToBitSet(String str)
	{
		ArrayList<BitSet> translatedText = new ArrayList<BitSet>();
		
		for(char c : str.toCharArray())
			translatedText.add(charToBitSet8(c));
		
		return translatedText;
	}
	
	public static void printBits(String prompt, BitSet b, int nb_bits) 
	{
      System.out.print(prompt);
      for (int i = 0; i < nb_bits; i++) 
      {
         System.out.print(b.get(i) ? "1" : "0");
      }
      System.out.println();
	}
	
	public static String toStringBitSet(BitSet b, int nb_bits) 
	{
		String str = new String();
		for (int i = 0; i < nb_bits; i++) 
  		{
	  		str += (b.get(i) ? "1" : "0");
  		}
		str+="\n";
  		return str;
	}
	
	public static void writeBitSetsToFile(ArrayList<BitSet> bs, String filename)
	{
		try
		{
		    PrintWriter writer = new PrintWriter(filename, "UTF-8");
		    for(BitSet b : bs)
		    	writer.print(toStringBitSet(b,8));
		    writer.close();
		} 
		catch (IOException e) 
		{
			
		}
	}
	
	public static void debug()
	{
		BitSet k = new BitSet(10);
		k.set(0,10);
		k.clear(4);
		k.clear(7);
		k.clear(8);
		printBits("k = ", k, 10);
		
		BitSet[] keys = createKeys(k);
		
		BitSet letterA = new BitSet(8);
		letterA.set(1);
		letterA.set(7);
		printBits("A = ", letterA, 8);
		
		BitSet ip = ip(letterA);
		printBits("IP(A) = ", ip, 8);
		
		BitSet fk = fk(ip,keys[0]);
		printBits("fk(A) = ",fk, 8);
		
		BitSet sw = sw(fk);
		printBits("sw(A) = ",sw, 8);
		
		BitSet fk2 = fk(sw,keys[1]);
		printBits("fk(A) = ",fk2, 8);
		
		BitSet cryptedLetter = cryptLetterSDES(letterA, k);
		printBits("Crypted A = ", cryptedLetter, 8);
	}
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		if(args.length != 3)
		{
			System.err.println("Sdes [key] [clearFile] [outputFile]");
			System.exit(0);
		}
		
		String key = new String(args[0]);
		
		if(key.length() != 10)
		{
			System.err.println("[!] Key must be 10 bits long.");
			System.exit(0);
		}
		
		String inputFile = new String(args[1]);
		String outputFile = new String(args[2]);
	
		BitSet keyBitSet = new BitSet(10);
		for(int  i = 0; i < key.length(); i++)
			if(key.charAt(i) == '1') keyBitSet.set(i);
		
//		debug();
		
		ArrayList<BitSet> translatedText = StringToBitSet(readFile(inputFile));
		writeBitSetsToFile(translatedText, "binaire"+inputFile);
		ArrayList<BitSet> sdesText = new ArrayList<BitSet>();
		for(BitSet b : translatedText)
			sdesText.add(cryptLetterSDES(b, keyBitSet));
		writeBitSetsToFile(sdesText, outputFile);
	}
}
