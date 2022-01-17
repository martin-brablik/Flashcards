package cz.upol.jj.martinbrablik.flashcards.commands;

import cz.upol.jj.martinbrablik.flashcards.DataHandler;
import cz.upol.jj.martinbrablik.flashcards.Deck;

public class SetOrderCommand extends Command
{
	//Pøíkaz pro nastavení tpùsobu øazení karet v balíèku
	public SetOrderCommand(String name, String[] mandatory_args, String[] optional_args)
	{
		super(name, mandatory_args, optional_args);
	}
	@Override
	public boolean execute()
	{
		try
		{
			Deck d = Deck.findByName(this.getMandatoryArgs()[0]); //Získání balíèku ze seznamu balíèkù
			//Výbìr zpùsobu podle zadané hodnoty
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
				System.out.println("Chbya: Nesprávnì zadané poøadí " + this.getMandatoryArgs()[1]);
				return false;
			}
			d.orderMethod.order(d); //Seøazení karet podle nového zpùsobu
			DataHandler.saveDeck(d); //Uložení do souboru
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Chyba: Nesprávný poèet argumentù");
			return false;
		}
	}
}