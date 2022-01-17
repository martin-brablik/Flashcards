package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class RemoveDeckCommand extends Command
{
	//P��kaz pro odstran�n� bal��ku
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
				System.out.println("Chyba: Bal��ek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}	
			DataHandler.deleteDeck(Deck.findByName(this.getMandatoryArgs()[0])); //Odstran�n� souboru bal��ku a slo�ky s jeho kartami
			Deck.findByName(this.getMandatoryArgs()[0]).unregister(); //Odstran�n� bal��ku ze seznamu bal��k�
			System.out.println("Bal��ek " + this.getMandatoryArgs()[0] + " byl �sp�n� odstran�n");
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chyba: Nespr�vn� po�et argument�");
			return false;
		}
	}
}
