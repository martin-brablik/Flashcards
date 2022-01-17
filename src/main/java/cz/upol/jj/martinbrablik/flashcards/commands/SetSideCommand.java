package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class SetSideCommand extends Command
{
	//Pøíkaz pro zmìny hodnoty strany karty
	public SetSideCommand(String name, String[] mandatory_args, String[] optional_args)
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
			Deck.findByName(this.getMandatoryArgs()[0]).findCard(Integer.parseInt(this.getMandatoryArgs()[1])).setSide(Integer.parseInt(this.getMandatoryArgs()[2]), this.getMandatoryArgs()[3]); //Zmìna obsahu strany
			DataHandler.saveCard(Deck.findByName(this.getMandatoryArgs()[0]).findCard(Integer.parseInt(this.getMandatoryArgs()[1]))); //Uložení do souboru
			return true;
		}
		catch(ArrayIndexOutOfBoundsException|NumberFormatException e)
		{
			String msg;
			if(e instanceof NumberFormatException)
				msg = "Chyba: nesprávnì zadaný argument";
			else
				msg = "Chyba: Nesprávný poèet argumentù";
			System.out.println(msg);
			return false;
		}
	}
}
