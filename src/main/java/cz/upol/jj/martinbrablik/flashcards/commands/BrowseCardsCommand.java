package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.Deck;

public class BrowseCardsCommand extends Command
{
	//P��kaz vyp�e v�echny informace o v�ech kart�ch v bal��ku
	public BrowseCardsCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		try
		{
			if(Deck.findByName(this.getMandatoryArgs()[0]) == null)
			{
				//Pokud se nepoda�� v seznamu bal��k� naj�t bal��ek se zadan�m jm�nem p��kaz skon�� cybou.
				System.out.println("Chyba: Bal��ek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}	
			for(int i = 0; i < Deck.findByName(this.getMandatoryArgs()[0]).getCards().size(); i++)
			{
				System.out.println("\nID: " + Deck.findByName(this.getMandatoryArgs()[0]).getCards().get(i).getId());
				System.out.println("P�edn� strana: " + Deck.findByName(this.getMandatoryArgs()[0]).getCards().get(i).getSide(0));
				System.out.println("Zadn� strana: " + Deck.findByName(this.getMandatoryArgs()[0]).getCards().get(i).getSide(1));
				System.out.println("Sk�re: " + Deck.findByName(this.getMandatoryArgs()[0]).getCards().get(i).getScore());
			}
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.print("Chyba: Nespr�vn� po�et argument�");
			return false;
		}
	}
}
