package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class RenameDeckCommand extends Command
{
	//Pøíkaz pro zmìnu jména balíèku
	public RenameDeckCommand(String name, String[] mandatory_args, String[] optional_args)
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
			Deck.findByName(this.getMandatoryArgs()[0]).setName(this.getMandatoryArgs()[1]); //Zmìna jména balíèku
			DataHandler.saveDeck(Deck.findByName(this.getMandatoryArgs()[1])); //Opìtovné uložení balíèku i s jeho kartami
			System.out.println("Balíèek byl pøejmenován");
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chyba: Nesprávný poèet argumentù");
			return false;
		}
	}
}
