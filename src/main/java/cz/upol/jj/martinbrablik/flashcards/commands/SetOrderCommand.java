package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class SetOrderCommand extends Command
{
	//P��kaz pro nastaven� tp�sobu �azen� karet v bal��ku
	public SetOrderCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		try
		{
			Deck d = Deck.findByName(this.getMandatoryArgs()[0]); //Z�sk�n� bal��ku ze seznamu bal��k�
			//V�b�r zp�sobu podle zadan� hodnoty
			if(this.getMandatoryArgs()[1].equalsIgnoreCase("time"))
			{
				d.orderMethod = Deck.availableOrderMethods[0];
			}
			else if(this.getMandatoryArgs()[1].equalsIgnoreCase("reverse_time"))
			{
				d.orderMethod = Deck.availableOrderMethods[0];
				d.orderMethod.setReverse(true);
			}
			else if(this.getMandatoryArgs()[1].equalsIgnoreCase("score"))
			{
				d.orderMethod = Deck.availableOrderMethods[1];
			}
			else if(this.getMandatoryArgs()[1].equalsIgnoreCase("reverse_score"))
			{
				d.orderMethod = Deck.availableOrderMethods[1];
				d.orderMethod.setReverse(true);
			}
			else
			{
				System.out.println("Chbya: Nespr�vn� zadan� po�ad� " + this.getMandatoryArgs()[1]);
				return false;
			}
			d.orderMethod.order(d); //Se�azen� karet podle nov�ho zp�sobu
			DataHandler.saveDeck(d); //Ulo�en� do souboru
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chyba: Nespr�vn� po�et argument�");
			return false;
		}
	}
}