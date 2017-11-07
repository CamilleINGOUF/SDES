import java.util.BitSet;

public class sdes 
{
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
	
	public static BitSet f(BitSet b,BitSet k)
	{
		BitSet bt = new BitSet(4);
		
		BitSet ep = sdes.ep(b);
		ep.xor(k);
		
		BitSet g = new BitSet(4);
		BitSet d = new BitSet(4);
		
		if(ep.get(0)) g.set(0);
		if(ep.get(1)) g.set(1);
		if(ep.get(2)) g.set(2);
		if(ep.get(3)) g.set(3);
		if(ep.get(4)) d.set(0);
		if(ep.get(5)) d.set(1);
		if(ep.get(6)) d.set(2);
		if(ep.get(7)) d.set(3);
		
		
		
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
		
		G.xor(f(D,k));
		
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
	
	public static void printBits(String prompt, BitSet b, int nb_bits) 
	{
      System.out.print(prompt + " ");
      for (int i = 0; i < nb_bits; i++) 
      {
         System.out.print(b.get(i) ? "1" : "0");
      }
      System.out.println();
	}
	
	public static void main(String[] args) 
	{
		BitSet k = new BitSet(10);
//		k.set(0);//1000000000
//		k.set(2);//1010000000
//		k.set(8);//1010000010
		k.set(0,10);
		k.clear(4);
		k.clear(7);
		k.clear(8);
		printBits("k = ", k, 10);
		
		BitSet[] keys = createKeys(k);
		
		printBits("k1 = ", keys[0], 8);
		printBits("k2 = ", keys[1], 8);
		
		BitSet letterA = new BitSet(8);
		letterA.set(1);
		letterA.set(7);
		printBits("A = ", letterA, 8);
		
		letterA = sdes.ip(letterA);
		printBits("IP(A) = ", letterA, 8);
		
		letterA = sdes.fk(letterA, keys[0]);
		printBits("fk(A) = ", letterA, 8);
	}
}
