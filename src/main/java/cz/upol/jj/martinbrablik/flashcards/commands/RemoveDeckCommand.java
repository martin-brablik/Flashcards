package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class RemoveDeckCommand extends Command
{
	//Pøíkaz pro odstranìní balíèku
	public RemoveDeckCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		try
		{
			if(!Deck.registeredDecks.contains(Deck.findByName(this.getMandatoryArgs()[0])))
			{
				System.out.println("Chyba: Balíèek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}	
			DataHandler.deleteDeck(Deck.findByName(this.getMandatoryArgs()[0])); //Odstranìní souboru balíèku a složky s jeho kartami
			Deck.findByName(this.getMandatoryArgs()[0]).unregister(); //Odstranìní balíèku ze seznamu balíèkù
			System.out.println("Balíèek " + this.getMandatoryArgs()[0] + " byl úspìšnì odstranìn");
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chyba: Nesprávný poèet argumentù");
			return false;
		}
	}
}
