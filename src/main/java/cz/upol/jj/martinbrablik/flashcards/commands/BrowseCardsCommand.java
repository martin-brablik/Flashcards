package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.Deck;

public class BrowseCardsCommand extends Command
{
	//Pøíkaz vypíše všechny informace o všech kartách v balíèku
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
				//Pokud se nepodaøí v seznamu balíèkù najít balíèek se zadaným jménem pøíkaz skonèí cybou.
				System.out.println("Chyba: Balíèek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}	
			for(int i = 0; i < Deck.findByName(this.getMandatoryArgs()[0]).getCards().size(); i++)
			{
				System.out.println("\nID: " + Deck.findByName(this.getMandatoryArgs()[0]).getCards().get(i).getId());
				System.out.println("Pøední strana: " + Deck.findByName(this.getMandatoryArgs()[0]).getCards().get(i).getSide(0));
				System.out.println("Zadní strana: " + Deck.findByName(this.getMandatoryArgs()[0]).getCards().get(i).getSide(1));
				System.out.println("Skóre: " + Deck.findByName(this.getMandatoryArgs()[0]).getCards().get(i).getScore());
			}
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.print("Chyba: Nesprávný poèet argumentù");
			return false;
		}
	}
}
