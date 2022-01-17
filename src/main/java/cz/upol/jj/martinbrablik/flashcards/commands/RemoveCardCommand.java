package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class RemoveCardCommand extends Command
{
	//Pøíkaz pro odstranìní karty z  balíèku
	public RemoveCardCommand(String name, String[] mandatory_args, String[] optional_args)
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
				System.out.println("Chbya: Kartu se nepodaøilo odstranit, protože balíèek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}
			DataHandler.deleteCard(Deck.findByName(this.getMandatoryArgs()[0]).findCard(Integer.parseInt(this.getMandatoryArgs()[1]))); //Odstranìní souboru karty
			Deck.findByName(this.getMandatoryArgs()[0]).removeCardById(Integer.parseInt(this.getMandatoryArgs()[1])); //Odstranìní karty ze seznamu karet
			System.out.println("Karta s ID " + this.getMandatoryArgs()[1] + " byla úspìšnì odstranìna z balíèku " + this.getMandatoryArgs()[0]);
			Deck.findByName(this.getMandatoryArgs()[0]).orderMethod.order(Deck.findByName(this.getMandatoryArgs()[0])); //Setøízení karet v balíèku
			return true;
		}
		catch(ArrayIndexOutOfBoundsException|NumberFormatException|NullPointerException e)
		{
			if(e instanceof ArrayIndexOutOfBoundsException)
				System.out.println("Chyba: Nesprávnì zadaný poèet argumentù");
			else
				System.out.println("Chyba: Nesprávnì zadaný argument " + this.getMandatoryArgs()[0]);
			return false;
		}
	}
}
