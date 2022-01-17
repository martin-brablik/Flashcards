package cz.upol.jj.martinbrablik.flashcards.commands;

import java.util.Arrays;

import cz.upol.jj.martinbrablik.flashcards.Card;
import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class AddCardCommand extends Command
{
	//P��kaz pro p�id�n� karty do bal��ku
	public AddCardCommand(String name, String[] mandatory_args, String[] optional_args)
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
				//Pokud se nepoda�� v seznamu bal��k� naj�t bal��ek se zadan�m jm�nem p��kaz skon�� cybou.
				System.out.println("Chyba: Kartu se nepoda�ilo p�idat, proto�e bal��ek " + this.getMandatoryArgs()[0] + " neexistuje");
				return false;
			}	
			Card c = new Card(Deck.findByName(this.getMandatoryArgs()[0]), Arrays.copyOfRange(this.getMandatoryArgs(), 1, this.getMandatoryArgs().length)); //Vytvo�en� objektu karty a p�id�n� do bal��ku.
			Deck.findByName(this.getMandatoryArgs()[0]).orderMethod.order(Deck.findByName(this.getMandatoryArgs()[0])); //Set��zen� karet v bal��ku
			System.out.println("Karta byla p�id�na do bal��ku " + this.getMandatoryArgs()[0]);
			DataHandler.saveCard(c); //Ulo�en� souboru karty
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chbya: Nespr�vn� po�et argument�");
			return false;
		}
	}
}