package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class SetLimitCommand extends Command
{
	//P��kaz pro nastaven� limit karet ke zkou�en�
	public SetLimitCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		try
		{
			Deck d = Deck.findByName(this.getMandatoryArgs()[0]); //Z�sk�n� bal��ku ze seznamu bal��k�
			if(d == null)
			{
				System.out.println("Chyba: Bal��ek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}
			if(this.getMandatoryArgs()[1].equalsIgnoreCase("new"))
			{
				d.setNewLimit(Integer.parseInt(this.getMandatoryArgs()[2])); //Nastaven� limit nov�ch karet
			}
			else if(this.getMandatoryArgs()[1].equalsIgnoreCase("repeated"))
			{
				d.setRepeatedLimit(Integer.parseInt(this.getMandatoryArgs()[2])); //Nataven� limitu opakuj�c�ch se karet
			}
			else
			{
				System.out.println("Chbya: Typ karty " + this.getMandatoryArgs()[1] + " neexistuje");
				return false;
			}
			System.out.println("Limit karet byl �sp�n� nastaven");
			DataHandler.saveDeck(d); //Ulo�en� do souboru
			return true;
		}
		catch(ArrayIndexOutOfBoundsException|NumberFormatException e)
		{
			if(e instanceof NumberFormatException)
				System.out.println("Chbya: Nespr�vn� zadan� argument " + this.getMandatoryArgs()[2]);
			return false;
		}
	}
}
