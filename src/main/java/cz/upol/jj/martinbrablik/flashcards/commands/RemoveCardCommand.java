package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class RemoveCardCommand extends Command
{
	//P��kaz pro odstran�n� karty z  bal��ku
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
				System.out.println("Chbya: Kartu se nepoda�ilo odstranit, proto�e bal��ek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}
			DataHandler.deleteCard(Deck.findByName(this.getMandatoryArgs()[0]).findCard(Integer.parseInt(this.getMandatoryArgs()[1]))); //Odstran�n� souboru karty
			Deck.findByName(this.getMandatoryArgs()[0]).removeCardById(Integer.parseInt(this.getMandatoryArgs()[1])); //Odstran�n� karty ze seznamu karet
			System.out.println("Karta s ID " + this.getMandatoryArgs()[1] + " byla �sp�n� odstran�na z bal��ku " + this.getMandatoryArgs()[0]);
			Deck.findByName(this.getMandatoryArgs()[0]).orderMethod.order(Deck.findByName(this.getMandatoryArgs()[0])); //Set��zen� karet v bal��ku
			return true;
		}
		catch(ArrayIndexOutOfBoundsException|NumberFormatException|NullPointerException e)
		{
			if(e instanceof ArrayIndexOutOfBoundsException)
				System.out.println("Chyba: Nespr�vn� zadan� po�et argument�");
			else
				System.out.println("Chyba: Nespr�vn� zadan� argument " + this.getMandatoryArgs()[0]);
			return false;
		}
	}
}
