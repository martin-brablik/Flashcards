package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class RenameDeckCommand extends Command
{
	//P��kaz pro zm�nu jm�na bal��ku
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
				System.out.println("Chyba: Bal��ek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}
			DataHandler.deleteDeck(Deck.findByName(this.getMandatoryArgs()[0])); //Odstran�n� souboru bal��ku a slo�ky s jeho kartami
			Deck.findByName(this.getMandatoryArgs()[0]).setName(this.getMandatoryArgs()[1]); //Zm�na jm�na bal��ku
			DataHandler.saveDeck(Deck.findByName(this.getMandatoryArgs()[1])); //Op�tovn� ulo�en� bal��ku i s jeho kartami
			System.out.println("Bal��ek byl p�ejmenov�n");
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chyba: Nespr�vn� po�et argument�");
			return false;
		}
	}
}
