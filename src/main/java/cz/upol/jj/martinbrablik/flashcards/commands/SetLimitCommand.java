package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class SetLimitCommand extends Command
{
	//Pøíkaz pro nastavení limit karet ke zkoušení
	public SetLimitCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		try
		{
			Deck d = Deck.findByName(this.getMandatoryArgs()[0]); //Získání balíèku ze seznamu balíèkù
			if(d == null)
			{
				System.out.println("Chyba: Balíèek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}
			if(this.getMandatoryArgs()[1].equalsIgnoreCase("new"))
			{
				d.setNewLimit(Integer.parseInt(this.getMandatoryArgs()[2])); //Nastavení limit nových karet
			}
			else if(this.getMandatoryArgs()[1].equalsIgnoreCase("repeated"))
			{
				d.setRepeatedLimit(Integer.parseInt(this.getMandatoryArgs()[2])); //Natavení limitu opakujících se karet
			}
			else
			{
				System.out.println("Chbya: Typ karty " + this.getMandatoryArgs()[1] + " neexistuje");
				return false;
			}
			System.out.println("Limit karet byl úspìšnì nastaven");
			DataHandler.saveDeck(d); //Uložení do souboru
			return true;
		}
		catch(ArrayIndexOutOfBoundsException|NumberFormatException e)
		{
			if(e instanceof NumberFormatException)
				System.out.println("Chbya: Nesprávnì zadaný argument " + this.getMandatoryArgs()[2]);
			return false;
		}
	}
}
